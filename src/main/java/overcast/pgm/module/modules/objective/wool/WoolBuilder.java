package overcast.pgm.module.modules.objective.wool;

import java.util.List;

import org.bukkit.util.Vector;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.ModuleFactory;
import overcast.pgm.module.ModuleStage;
import overcast.pgm.module.modules.region.types.BlockRegion;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.util.BukkitUtils;
import overcast.pgm.util.Log;
import overcast.pgm.util.TeamUtil;
import overcast.pgm.util.XMLUtils;
import overcast.pgm.xml.InvalidXMLException;
import overcast.pgm.xml.XMLParseException;

@BuilderInfo(stage = ModuleStage.LOAD)
public class WoolBuilder extends Builder {

	// TODO work on this alot more :)
	@Override
	public ModuleCollection<Module> build(Document doc, ModuleFactory factory) throws XMLParseException {
		ModuleCollection<Module> modules = new ModuleCollection<Module>();

		Element root = doc.getDocumentElement();

		Node node = root.getElementsByTagName("wools").item(0);

		if (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element woolsElement = (Element) node;
				Team team = null;
				String id = null;
				String monument = null;
				Vector block = null;
				if (woolsElement.hasAttribute("id")) {
					id = woolsElement.getAttribute("id");
				}

				if (woolsElement.hasAttribute("team")) {
					team = TeamUtil.getTeamByID(woolsElement.getAttribute("team"));
				} else {
					List<Element> children = XMLUtils.getChildElements(woolsElement);

					for (Element child : children) {
						if (child.getTagName().equals("wool")) {
							if (child.hasAttribute("id")) {
								id = child.getAttribute("id");
							}
							if (child.hasAttribute("team")) {
								team = TeamUtil.getTeamByID(child.getAttribute("team"));
							}

							if (child.hasAttribute("monument")) {
								monument = BukkitUtils.colorize(child.getAttribute("monument"));
							}


							Node monumentNode = child.getElementsByTagName("monument").item(0);

							if (monumentNode != null) {
								if (monumentNode.getNodeType() == Node.ELEMENT_NODE) {
									Element element = (Element) node;

									Node blockNode = element.getElementsByTagName("block").item(0);
								    if(blockNode.getNodeType() == Node.ELEMENT_NODE){
								    	Element blockElement = (Element) node;
								    	try {
											block = XMLUtils.parseVector(null, blockElement.getTextContent());
										} catch (DOMException | InvalidXMLException e) {
											e.printStackTrace();
										}
								    }
								}
							} else {
								Log.warning("the <monument> tag is needed!");
							}
						}
					}
				}

				modules.add(new WoolObjective(id, team, null, new BlockRegion(block), monument));
			}
		}
		return modules;
	}

	@Override
	public ModuleCollection<Module> build(Document doc) throws XMLParseException {
		return null;
	}

}
