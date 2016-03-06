package overcast.pgm.module.modules.objective.wool;

import org.bukkit.DyeColor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.ModuleFactory;
import overcast.pgm.module.ModuleStage;
import overcast.pgm.module.modules.region.RegionContext;
import overcast.pgm.module.modules.region.types.BlockRegion;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.util.TeamUtil;
import overcast.pgm.util.XMLUtils;
import overcast.pgm.xml.XMLParseException;

@BuilderInfo(stage = ModuleStage.NORMAL)
public class WoolBuilder extends Builder {

	// TODO work on this alot more :)
	@Override
	public ModuleCollection<Module> build(Document doc, ModuleFactory factory) throws XMLParseException {
		ModuleCollection<Module> modules = new ModuleCollection<Module>();
        RegionContext context = factory.getRegionContext(); 
		Element root = doc.getDocumentElement();
 
		Node woolNode = root.getElementsByTagName("wools").item(0);
		Team team = null;
		if (woolNode != null) {
			if (woolNode.getNodeType() == Node.ELEMENT_NODE) {
				Element woolElement = (Element) woolNode;
				
			    for(Element wool : XMLUtils.getUniqueChildren(woolElement, "wool")){
					if (wool != null) {
						if (wool.hasAttribute("team")) {
							team = TeamUtil.getTeamByID(wool.getAttribute("team"));
						}
						
						
						// dye color is required */
						
						DyeColor color = XMLUtils.parseDyeColor(wool.getAttribute("color"));
						
						BlockRegion region = (BlockRegion) context.get(wool.hasAttribute("monument") ? wool.getAttribute("monument") : null);
						boolean required = wool.hasAttribute("required") ? XMLUtils.parseBoolean(wool.getAttribute("required")) : true;
						String id = wool.hasAttribute("id") ? wool.getAttribute("id") : null;
						WoolObjective woolOBJ = new WoolObjective(id, required, team, color, region);
					    modules.add(woolOBJ);
					}
				}
			} 
		}
			
		return modules;
	}

	@Override
	public ModuleCollection<Module> build(Document doc) throws XMLParseException {
		return null;
	}

}
