package overcast.pgm.module.modules.filter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.w3c.dom.Element;

import overcast.pgm.module.ModuleFactory;
import overcast.pgm.module.modules.filter.types.HoldingFilter;
import overcast.pgm.module.modules.filter.types.KillStreakFilter;
import overcast.pgm.module.modules.filter.types.TeamFilter;
import overcast.pgm.module.modules.filter.types.logic.NotFilter;
import overcast.pgm.module.modules.region.RegionContext;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.util.Log;
import overcast.pgm.util.MethodParser;
import overcast.pgm.util.MethodParsers;
import overcast.pgm.util.NumberUtils;
import overcast.pgm.util.TeamUtil;
import overcast.pgm.util.XMLUtils;
import overcast.pgm.xml.InvalidXMLException;

public class FilterParser {

	public Map<String, Method> methodParsers = MethodParsers.getMethodParsersForClass(FilterParser.class);

	private final ModuleFactory modules;
	private final FilterContext filterContext;
	private final RegionContext regionContext;

	public FilterParser(ModuleFactory modules, FilterContext filterContext2, RegionContext regionContext2) {
		this.modules = modules;
		this.filterContext = filterContext2;
		this.regionContext = regionContext2;
	}

	public ModuleFactory getModules() {
		return modules;
	}

	public FilterContext getFilterContext() {
		return filterContext;
	}

	public RegionContext getRegionContext() {
		return regionContext;
	}

	public Map<String, Method> getMethodParsers() {
		return methodParsers;
	}

	@MethodParser("team")
	public TeamFilter parseTeamFilter(Element element) {
		Team team = TeamUtil.getTeamByID(element.getTextContent());
		String id = element.hasAttribute("id") ? element.getAttribute("id") : null;
		return new TeamFilter(id, team);
	}

	public List<Filter> parseChildFilters(Element parent) throws InvalidXMLException {
		List<Filter> filters = new ArrayList<>();
		for (Element child : getFilterChildren(parent)) {
			if (isFilterElement(child)) {
				filters.add(parse(child));
			}
		}
		return filters;
	}

	@MethodParser("not")
	public NotFilter parseNotFilter(Element element) {
		try {
			List<Filter> children = parseChildFilters(element);
			int size = children.size();
			if (size != 0) {
				NotFilter notFilter = new NotFilter(children);

				return notFilter;
			}
		} catch (InvalidXMLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@MethodParser("kill-streak")
	public KillStreakFilter parseKillStreakFilter(Element element) {
		int amount = element.hasAttribute("amount") ? NumberUtils.parseInteger(element.getAttribute("amount")) : 1;
		int max = element.hasAttribute("max") ? NumberUtils.parseInteger(element.getAttribute("max")) : 2;
		int min = element.hasAttribute("min") ? NumberUtils.parseInteger(element.getAttribute("min")) : 1;

		KillStreakFilter ks = new KillStreakFilter(amount, max, min);

		return ks;
	}

	@MethodParser("holding")
	public HoldingFilter parseHoldingFilter(Element element) {
		Material mat = XMLUtils.parseMaterial(element.getTextContent());
		return new HoldingFilter(mat);
	}

	public List<Element> getFilterChildren(Element parent) {
		List<Element> elements = new ArrayList<Element>();
		for (Element el : XMLUtils.getChildElements(parent)) {
			if (this.isFilterElement(el)) {
				elements.add(el);
			}
		}
		return elements;
	}

	public boolean isFilterElement(Element el) {
		return this.filterContext.get(el.getNodeName()) != null;
	}

	protected Method getMethodParser(String regionName) {
		return methodParsers.get(regionName);
	}

	public Filter parse(Element element) throws InvalidXMLException {
		Filter filter = parseFilter(element);
		String id = element.hasAttribute("id") ? element.getAttribute("id") : null;
		if (id == null || element.getTagName().equals("filter")) {
			this.filterContext.add(filter);
		} else {
			if (this.filterContext.get(id) == null) {
				this.filterContext.add(id, filter);
			} else {
				throw new InvalidXMLException("There is already a filter with that id ", element);
			}
		}
		Log.info("Parsed " + element.getTagName() + " with an id of " + id);
		return filter;
	}

	public Filter parseFilter(Element el) throws InvalidXMLException {
		Method parser = this.getMethodParser(el.getTagName());
		try {
			return (Filter) parser.invoke(this, el);
		} catch (Exception e) {
			if (e.getCause() instanceof InvalidXMLException) {
				throw (InvalidXMLException) e.getCause();
			} else {
				throw new InvalidXMLException("Unknown error parsing filter: " + e.getMessage(), el, e);
			}
		}
	}
}
