package overcast.pgm.module.modules.team;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.ModuleStage;
import overcast.pgm.util.NumberUtils;
import overcast.pgm.util.XMLUtils;
import overcast.pgm.xml.XMLParseException;

@BuilderInfo(stage = ModuleStage.START)
public class TeamBuilder extends Builder {

	@Override
	public ModuleCollection<Module> build(Document doc) throws XMLParseException {
		ModuleCollection<Module> modules = new ModuleCollection<>();
		Set<Team> teams = new HashSet<>();
		Element root = doc.getDocumentElement();
		Node teamsNode = root.getElementsByTagName("teams").item(0);

		if (teamsNode == null) {
			throw new XMLParseException("there needs to be teams");
		}

		Team newTeam = null;
		
		if (teamsNode.getNodeType() == Node.ELEMENT_NODE) {
			Element teamsElement = (Element) teamsNode;
			NodeList teamList = teamsElement.getChildNodes();

			for (int i = 0; i < teamList.getLength(); i++) {
				Node node = teamList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE
						&& node.getNodeName().equals("team")) {
					Element teamElement = (Element) node;

					if (!teamElement.hasAttribute("id")) {
						throw new XMLParseException(
								"there is a team with no ID");
					}

					if (!teamElement.hasAttribute("max")) {
						throw new XMLParseException(
								"there is no max attribute!");
					}

					int max = NumberUtils.parseInteger(teamElement
							.getAttribute("max"));

					int max_overfill = 0;
					
					ChatColor color = ChatColor.WHITE;
					
					if(teamElement.hasAttribute("color")){
						color = XMLUtils.parseChatColor(teamElement.getAttribute("color"));
					}
					
					
					boolean plural = false;
					
					if(teamElement.hasAttribute("plural")){
						plural = XMLUtils.parseBoolean(teamElement.getAttribute("plural"));
					}

					String id = teamElement.getAttribute("id");

					String name = teamElement.getTextContent();
					
					
					if(teamElement.hasAttribute("max-overfill")){
						max_overfill = NumberUtils.parseInteger(teamElement
								.getAttribute("max-overfill"));
						newTeam = new Team(id, name, color, max, max_overfill, plural);
					}else{
						newTeam = new Team(id, name, color, max, plural);
					}
					
					if (max >= max_overfill) {
						throw new XMLParseException(
								"the max attribute can't equal or greater than the max-overfill");
					}
					
					if (max_overfill <= max) {
						throw new XMLParseException(
								"the max-overfill attribute cant be lower than the or equal to the max attribute");
					}
					
					
					teams.add(newTeam);
					
					
					TeamModule teamModule = new TeamModule(teams);
					
					modules.add(teamModule);
				}
			}
		}
		return modules;
	}

}
