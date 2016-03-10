package overcast.pgm.module.modules.crafting;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import overcast.pgm.builder.Builder;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.util.XMLUtils;
import overcast.pgm.xml.XMLParseException;

public class CraftingBuilder extends Builder {

	String[] ingredientTags = new String[] { "ingredient", "i" };

	@Override
	public ModuleCollection<Module> build(Document doc) throws XMLParseException {
		ModuleCollection<Module> modules = new ModuleCollection<>();
		Element root = doc.getDocumentElement();

		Node craftingNode = root.getElementsByTagName("crafting").item(0);

		if (craftingNode != null) {
			if (craftingNode.getNodeType() == Node.ELEMENT_NODE) {
				Element craftingEl = (Element) craftingNode;

				List<Element> children = XMLUtils.getChildElements(craftingEl);
				for (Element child : children) {
					if (child != null) {
						switch(child.getTagName()){
						     
						}
					}
				}
			}
		}
		return modules;
	}
}
