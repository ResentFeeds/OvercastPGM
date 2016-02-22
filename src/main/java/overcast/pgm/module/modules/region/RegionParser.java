package overcast.pgm.module.modules.region;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.util.Vector;
import org.w3c.dom.Element;

import overcast.pgm.module.modules.filter.types.CarryingFilter;
import overcast.pgm.module.modules.region.parsers.RegionFilterApplicationParser;
import overcast.pgm.module.modules.region.types.BlockRegion;
import overcast.pgm.module.modules.region.types.CircleRegion;
import overcast.pgm.module.modules.region.types.CuboidRegion;
import overcast.pgm.module.modules.region.types.CylinderRegion;
import overcast.pgm.util.Log;
import overcast.pgm.util.MethodParser;
import overcast.pgm.util.MethodParsers;
import overcast.pgm.util.NumberUtils;
import overcast.pgm.util.XMLUtils;
import overcast.pgm.xml.InvalidXMLException;
import overcast.pgm.xml.XMLParseException;

/**
 * FIX CIRCLE REGION *
 *
 * @author tylern
 *
 */
public class RegionParser {

	protected static final Map<String, Method> methodParsers = MethodParsers
			.getMethodParsersForClass(RegionParser.class);

	private RegionContext context;

	public RegionParser(RegionContext context) {
		this.context = context;
	}

	public Region parse(Element element) throws InvalidXMLException {
		Region region = parseRegion(element);
		String id = element.hasAttribute("id") ? element.getAttribute("id")
				: null;
		if (id == null || element.getTagName().equals("region")) {
			this.context.add(region);
		} else {
			if (this.context.get(id) == null) {
				this.context.add(id, region);
			} else {
				throw new InvalidXMLException(
						"There is already a region with that id ", element);
			}
		}
		Log.info("Parsed " + element.getTagName() + " with an id of " + id);
		return region;
	}

	private Region parseRegion(Element el) throws InvalidXMLException {
		Method parser = this.getMethodParser(el.getTagName());
		try {
			return (Region) parser.invoke(this, el);
		} catch (Exception e) {
			if (e.getCause() instanceof InvalidXMLException) {
				throw (InvalidXMLException) e.getCause();
			} else {
				throw new InvalidXMLException("Unknown error parsing region: "
						+ e.getMessage(), el, e);
			}
		}
	}

	public boolean isRegionElement(Element el) {
		return this.getMethodParser(el.getTagName()) != null;
	}

	protected Method getMethodParser(String regionName) {
		return methodParsers.get(regionName);
	}

	public static Map<String, Method> getMethodParsers() {
		return methodParsers;
	}

	public RegionContext getRegionContext() {
		return this.context;
	}

	@MethodParser("block")
	public BlockRegion parseBlock(Element element) {
		Vector vector = XMLUtils.parseVector(element);
		return new BlockRegion(vector);
	}

	@MethodParser("cuboid")
	public CuboidRegion parseCuboid(Element element) {
		try {
			Vector min = XMLUtils.parseVector(element.getAttributeNode("min"));
			Vector max = XMLUtils.parseVector(element.getAttributeNode("max")); 
			CarryingFilter filter = new CarryingFilter(element.getAttribute("material"));
			return new CuboidRegion(min, max, filter);
		} catch (InvalidXMLException e) {
			e.printStackTrace();
		}
		return null;
	}

	 
	public RegionFilterApplication parserRFA(Element element, RegionParser parser){
	    return new RegionFilterApplication(new RegionFilterApplicationParser(element, parser));	
	}
	
	/**
	 * set the default radius to 3
	 * 
	 * @throws XMLParseException
	 */

	@MethodParser("circle")
	public CircleRegion parseCircle(Element element) throws XMLParseException {
		List<Double> numbers = parse2DVector(element, "center");

		if ((numbers) == null) {
			throw new XMLParseException("invalid 2DVector while parsing "
					+ methodParsers.get(element.getTagName()).getName());
		}

		double radius = this.readDouble(element.getAttribute("radius"), 3);


		return new CircleRegion(numbers.get(0), numbers.get(1), radius);
	}

	private double readDouble(String attribute, double def) { 
		return attribute == null ? def : NumberUtils.parseDouble(attribute);
	}

	@MethodParser("cylinder")
	public CylinderRegion parseCylinder(Element element) throws InvalidXMLException{
		 Vector base = XMLUtils.parseVector(null, element.getAttribute("base"));
		 double radius = NumberUtils.parseDouble(element.getAttribute("radius"));
		 double height = NumberUtils.parseDouble(element.getAttribute("height"));
		return new CylinderRegion(base, radius, height);
	}

	public Region[] parseSubRegionsArray(Element parent)
			throws InvalidXMLException {
		return this.parseSubRegions(parent).toArray(new Region[0]);
	}

	public List<Region> parseSubRegions(Element parent)
			throws InvalidXMLException {
		List<Region> regions = new ArrayList<Region>();
		for (Element el : this.getRegionChildren(parent)) {
			regions.add(this.parse(el));
		}
		return regions;
	}

	@MethodParser("region")
	public Region parseNamedRegion(Element el) throws InvalidXMLException,
			InvalidRegionException {
		String id = el.getAttribute("id");
		if (id == null) {
			throw new InvalidRegionException("Region must specify id", el);
		} else {
			Region region = this.context.get(id);
			if (region == null) {
				throw new InvalidRegionException("Unknown region '" + id + "'",
						el);
			} else {
				return region;
			}
		}
	}

	public List<Element> getRegionChildren(Element parent) {
		List<Element> elements = new ArrayList<Element>();
		for (Element el : XMLUtils.getChildElements(parent)) {
			if (this.isRegionElement(el)) {
				elements.add(el);
			}
		}
		return elements;
	}

	public List<Double> parse2DVector(Element el, String attribute) {
		String text = el.getAttribute(attribute);
		if (text == null) {
			return null;
		} else {
			List<Double> nums = NumberUtils.parseDoubleList(text);
			if (nums.size() == 2) {
				return nums;
			} else {
				return null;
			}
		}
	}
	
	
}
