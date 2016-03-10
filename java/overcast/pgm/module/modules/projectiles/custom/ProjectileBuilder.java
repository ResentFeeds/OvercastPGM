package overcast.pgm.module.modules.projectiles.custom;

import org.bukkit.entity.EntityType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.ModuleStage;
import overcast.pgm.util.BukkitUtils;
import overcast.pgm.util.NumberUtils;
import overcast.pgm.util.StringUtils;
import overcast.pgm.util.TimeUtil;
import overcast.pgm.util.XMLUtils;
import overcast.pgm.xml.XMLParseException; 
//TODO work on this
@BuilderInfo(stage = ModuleStage.START)
public class ProjectileBuilder extends Builder {

	@Override
	public ModuleCollection<Module> build(Document doc) throws XMLParseException {
		ModuleCollection<Module> modules = new ModuleCollection<>();
		Element root = doc.getDocumentElement();
		Node projectilesNode = root.getElementsByTagName("projectiles").item(0);
		if(projectilesNode != null){
			if(projectilesNode.getNodeType() == Node.ELEMENT_NODE){
				Element projectilesElement = (Element) projectilesNode;
				
				for(Element projectileEL : XMLUtils.getUniqueChildren(projectilesElement, "projectile")){
					 if(projectileEL != null){
						 String id = projectileEL.hasAttribute("id") ? projectileEL.getAttribute("id") : null;
						 String name = projectileEL.hasAttribute("name") ? BukkitUtils.colorize(projectileEL.getAttribute("name")) : null;
                         boolean throwable = XMLUtils.parseBoolean(projectileEL.getAttribute("throwable"));
                         EntityType type = projectileEL.hasAttribute("projectile") ? EntityType.valueOf(StringUtils.getTechnicalName(projectileEL.getAttribute("projectile"))) : EntityType.ARROW;
                         double velocity = projectileEL.hasAttribute("velocity") ? NumberUtils.parseDouble(projectileEL.getAttribute("velocity")) : 1.0;
                         double damage = projectileEL.hasAttribute("damage") ? NumberUtils.parseDouble(projectileEL.getAttribute("damage")) : 0.0;
                         ActionType both = projectileEL.hasAttribute("click") ? XMLUtils.parseActionType(projectileEL.getAttribute("click")) : XMLUtils.parseActionType("both");
                         int cooldown = projectileEL.hasAttribute("cooldown") ? TimeUtil.parseTime(projectileEL.getAttribute("cooldown")) : 0; 
                         modules.add(new ProjectileModule(id, name, throwable, type, velocity, damage, both, null, cooldown)); 
					 }
				}
			}
		}
		return modules;
	}

}
