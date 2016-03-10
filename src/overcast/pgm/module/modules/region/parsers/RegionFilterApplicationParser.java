package overcast.pgm.module.modules.region.parsers;

import java.util.List;

import org.w3c.dom.Element;

import overcast.pgm.module.modules.kits.KitModule;
import overcast.pgm.module.modules.region.RFAScope;
import overcast.pgm.module.modules.region.Region;
import overcast.pgm.module.modules.region.RegionParser;
import overcast.pgm.parser.ElementParser;
import overcast.pgm.util.KitUtils;
import overcast.pgm.util.XMLUtils;
import overcast.pgm.xml.InvalidXMLException;

public class RegionFilterApplicationParser extends ElementParser {

	private KitModule kit;
	private RFAScope scope;
	private Region region;
	private String message;
	
	private RegionParser parser;

	public RegionFilterApplicationParser(Element applyElement, RegionParser parser) {
		super(applyElement);
		this.parser = parser;
	}

	public void parseRegionFilterApplication(Element element) {
		if (element != null) {
			this.kit = element.hasAttribute("kit") ? KitUtils.getKit(element.getAttribute("kit")) : null;
            this.message = element.hasAttribute("message") ? element.getAttribute("message") : null;
			for (RFAScope rfas : RFAScope.values()) {
				if (rfas != null) {
					String name = rfas.getNewName();

					this.scope = element.hasAttribute(name) ? rfas : null;
				}
			}

			List<Element> children = XMLUtils.getChildElements(element);

			for (Element child : children) {
				if (child != null) {
					if (RegionParser.getMethodParsers().get(child.getTagName()) != null
							|| child.getTagName().equals("region")) {
						try {
							this.region = parser.parse(child);
						} catch (InvalidXMLException e) { 
							e.printStackTrace();
						}
					}
				}
			}
		}
	} 
	
	public String getMessage(){
		return this.message;
	}
	
	
	public boolean hasMessage(){
		return this.message != null;
	}

	public boolean hasKit() {
		return this.kit != null;
	}

	public KitModule getKit() {
		return this.kit;
	}

	public RFAScope getRFAScope() {
		return this.scope;
	}
	
	public Region getRegion(){
		return this.region;
	}
	
}
