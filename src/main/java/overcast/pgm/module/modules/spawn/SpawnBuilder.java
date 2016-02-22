package overcast.pgm.module.modules.spawn;

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
		Node node = root.getElementsByTagName("spawns").item(0);
		Team team = null;
		KitModule kit = null;
		Region region = null;
		if (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element SpawnsElement = (Element) node;
				Element spawnElement = XMLUtils.getChildElement(SpawnsElement, "spawn");

				if (spawnElement.hasAttribute("team")) {
					team = TeamUtil.getTeamByID(spawnElement.getAttribute("team"));
				}

				if (spawnElement.hasAttribute("kit")) {
					kit = KitUtils.getKit(spawnElement.getAttribute("kit"));
				}
 
		}
	}

		Spawn spawn = new Spawn(team, kit, region);
		SpawnModule module = new SpawnModule(spawn);
		modules.add(module);
		return modules;
	}

	@Override
	public ModuleCollection<Module> build(Document doc) throws XMLParseException {
		return null;
	}

}
