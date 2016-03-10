package overcast.pgm.module.modules.toolrepair;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.util.XMLUtils;
import overcast.pgm.xml.XMLParseException;
@BuilderInfo()
public class ToolRepairBuilder extends Builder {

	@Override
	public ModuleCollection<Module> build(Document doc) throws XMLParseException {
		ModuleCollection<Module> modules = new ModuleCollection<>();

		Element root = doc.getDocumentElement();

		Node node = root.getElementsByTagName("toolrepair").item(0);

		if (node != null) {
			List<Material> tools = new ArrayList<>();
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element nodeElement = (Element) node;

				List<Element> children = XMLUtils.getChildElements(nodeElement);
				for (Element child : children) {
					if (child != null) {
						switch (child.getTagName()) {
						case "tool":
							tools.add(XMLUtils.parseMaterial(child.getTextContent()));
							break;
						}
					}
				}
			}

			ToolRepairModule toolRepairModule = new ToolRepairModule(tools);
			modules.add(toolRepairModule);
		}
		return modules;
	}
}
