package overcast.pgm.module.modules.killreward;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.ModuleFactory;
import overcast.pgm.module.ModuleStage;
import overcast.pgm.module.modules.filter.Filter;
import overcast.pgm.module.modules.filter.FilterParser;
import overcast.pgm.module.modules.kits.KitModule;
import overcast.pgm.xml.InvalidXMLException;
import overcast.pgm.xml.XMLParseException;

@BuilderInfo(stage = ModuleStage.LOAD)
public class KillRewardBuilder extends Builder {

	//FIXME this ;)
	@Override
	public ModuleCollection<Module> build(Document doc) throws XMLParseException {
		return null;
	}

	public static KillReward parseKillReward(Element kw, ModuleFactory fac) {
		FilterParser fp = fac.getFilterContext().getParser();
		KitModule km = null;
		if (kw != null) {
			
			Node filter = kw.getElementsByTagName("filter").item(0);
			if (filter != null) {
				if (filter.getNodeType() == Node.ELEMENT_NODE) {
					Element filterElement = (Element) filter;
					try {
						List<Filter> filters = fp.parseChildFilters(filterElement);
						KillReward killreward = new KillReward(filters, km);
						return killreward;
					} catch (InvalidXMLException e) {
						e.printStackTrace();
					}

				}
			}
		}
		return null;
	}

}
