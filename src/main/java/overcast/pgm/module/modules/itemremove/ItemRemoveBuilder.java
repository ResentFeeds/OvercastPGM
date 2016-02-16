package overcast.pgm.module.modules.itemremove;

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
public class ItemRemoveBuilder extends Builder {

	@Override
	public ModuleCollection<Module> build(Document doc) throws XMLParseException {
		ModuleCollection<Module> modules = new ModuleCollection<>();
		Element root = doc.getDocumentElement();

		Node node = root.getElementsByTagName("itemremove").item(0);
		List<Material> removed = new ArrayList<>();
		if (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elementNode = (Element) node;

				List<Element> children = XMLUtils.getChildElements(elementNode);

				for (Element child : children) {
					if (child != null) {
						switch (child.getTagName()) {
						case "item":
							removed.add(XMLUtils.parseMaterial(child.getTextContent()));
							break;
						}
					}
				}
			}

			ItemRemoveModule removeModule = new ItemRemoveModule(removed);

			modules.add(removeModule);

		}
		return modules;

	}

}
