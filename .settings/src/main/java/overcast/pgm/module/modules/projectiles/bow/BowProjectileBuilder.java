package overcast.pgm.module.modules.projectiles.bow;

import java.util.List;

import org.bukkit.entity.EntityType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.modules.kits.PotionKit;
import overcast.pgm.module.modules.kits.parsers.PotionKitParser;
import overcast.pgm.util.NumberUtils;
import overcast.pgm.util.StringUtils;
import overcast.pgm.util.XMLUtils;
import overcast.pgm.xml.XMLParseException;

@BuilderInfo()

public class BowProjectileBuilder extends Builder {
	@Override
	public ModuleCollection<Module> build(Document doc) throws XMLParseException { 
		 Element root = doc.getDocumentElement();
		 
		 Node node = root.getElementsByTagName("modifybowprojectile").item(0);
		 
		 return node == null ? null : parse(node);
	}

	private ModuleCollection<Module> parse(Node node) {
		ModuleCollection<Module> modules = new ModuleCollection<>(); 
		if(node.getNodeType() == Node.ELEMENT_NODE){
			Element element = (Element) node;
			if(element != null){
		        modules.add(parseBowProjectile(element));
			}
		} 
		
		return modules;
	}

	private BowProjectileModule parseBowProjectile(Element element) {
		 List<Element> children = XMLUtils.getChildElements(element);
		 EntityType arrow = EntityType.ARROW; 
		 double velocity = 1.0; 
		PotionKit kit = null;
		for(Element child : children){
			  if(child != null){ 
				  switch(child.getTagName()){
				  case "projectile":
					  arrow = EntityType.valueOf(StringUtils.getTechnicalName(child.getTextContent()));
					  break;
				  case "veloctityMod":
					  velocity = NumberUtils.parseDouble(child.getTextContent());
					  break;
				  case "potion":
					  kit = new PotionKit(new PotionKitParser(child));
					  break;
				  }
			  }
		 }  
		
		return new BowProjectileModule(arrow, velocity, kit);
	} 
}
