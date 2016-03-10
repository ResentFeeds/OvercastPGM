package overcast.pgm.module.modules.itemkeep;
 

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
public class ItemKeepBuilder extends Builder{

	@Override
	public ModuleCollection<Module> build(Document doc) throws XMLParseException {
		ModuleCollection<Module> modules = new ModuleCollection<>();
		Element root = doc.getDocumentElement();
		List<Material> kept = new ArrayList<>();
		Node node = root.getElementsByTagName("itemkeep").item(0);
		
		if(node != null){
			if(node.getNodeType() == Node.ELEMENT_NODE){
				Element elNode = (Element) node;
				
				List<Element> children = XMLUtils.getChildElements(elNode);
				
				for(Element child : children){
					if(child != null){
						switch(child.getTagName()){
						case "item":
							kept.add(XMLUtils.parseMaterial(child.getTextContent()));
							break;
						}
					}
				}
			}
			
			ItemKeepModule keepModule = new ItemKeepModule(kept);
			modules.add(keepModule);  
		}
		
		return modules;
	} 
}
