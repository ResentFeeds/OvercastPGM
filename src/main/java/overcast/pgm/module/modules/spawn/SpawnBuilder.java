package overcast.pgm.module.modules.spawn;

import java.util.ArrayList;
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
import overcast.pgm.module.modules.kits.KitModule;
import overcast.pgm.module.modules.region.Region;
import overcast.pgm.module.modules.region.RegionContext;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.util.KitUtils;
import overcast.pgm.util.TeamUtil;
import overcast.pgm.util.XMLUtils;
import overcast.pgm.xml.XMLParseException;

@BuilderInfo(stage = ModuleStage.NORMAL)
public class SpawnBuilder extends Builder {

	@Override
	public ModuleCollection<Module> build(Document doc, ModuleFactory fac) throws XMLParseException {
		ModuleCollection<Module> modules = new ModuleCollection<>();
		Element root = doc.getDocumentElement();
		RegionContext rc = fac.getRegionContext();
		Team obs = TeamUtil.getTeamModule().getObservers();
		Node spawnsNode = root.getElementsByTagName("spawns").item(0);
		if (spawnsNode != null) {
			List<Spawn> spawns = new ArrayList<>();
			if (spawnsNode.getNodeType() == Node.ELEMENT_NODE) {
				Element spawnsElement = (Element) spawnsNode;

				List<Element> children = XMLUtils.getChildElements(spawnsElement);
				for (Element child : children) {
					if (child != null) {
						switch (child.getTagName()) {
						case "spawn":
							Team team = child.hasAttribute("team") ? TeamUtil.getTeamByID(child.getAttribute("team"))
									: null;
							KitModule kit = child.hasAttribute("kit") ? KitUtils.getKit(child.getAttribute("kit"))
									: null;

							Region region = child.hasAttribute("region") ? rc.get(child.getAttribute("region")) : null;
							// String outcome = region != null ? "not null" :
							// "null";
							// Log.info(outcome);
							spawns.add(new Spawn(team, region, kit));
							break;
						}
					}
				}

				// System.out.println("there are " + i + "spawn" + (i != 0 && i
				// > 1 ? "s" : ""));

				Element defaultE = XMLUtils.getUniqueChild(spawnsElement, "default");
				if (defaultE != null) {
					Region region = defaultE.hasAttribute("region") ? rc.get(defaultE.getAttribute("region")) : null;
					Spawn def = new Spawn(obs, region, null);
					SpawnModule spawnModule = new SpawnModule(spawns, def);
					modules.add(spawnModule);
				} else {
					try {
						throw new SpawnException("no default spawn found!");
					} catch (SpawnException e) { 
						e.printStackTrace();
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
