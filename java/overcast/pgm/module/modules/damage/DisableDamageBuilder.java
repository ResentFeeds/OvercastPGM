package overcast.pgm.module.modules.damage;

import java.util.Set;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.collect.Sets;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.ModuleFactory;
import overcast.pgm.util.XMLUtils;
import overcast.pgm.xml.XMLParseException;

@BuilderInfo()
public class DisableDamageBuilder extends Builder {

	boolean self;
	boolean ally;
	boolean enemy;
	boolean other;

	@Override
	public ModuleCollection<Module> build(Document doc) throws XMLParseException {
		ModuleCollection<Module> modules = new ModuleCollection<>();
		Element root = doc.getDocumentElement();

		Node node = root.getElementsByTagName("disabledamage").item(0);
		DisableDamageModule disable = parseDisableDamageModule(node, "damage");

		modules.add(disable);
		return modules;

	}

	public DisableDamageModule parseDisableDamageModule(Node node, String damageTag) throws XMLParseException {
		if (node != null) {
			Set<DamageCause> causes = Sets.newHashSet();
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element disabledElement = (Element) node;
				NodeList children = disabledElement.getChildNodes();

				for (int i = 0; i < children.getLength(); i++) {
					Node damage = children.item(i);

					if (damage.getNodeType() == Node.ELEMENT_NODE && damage.getNodeName().equals(damageTag)) {
						Element damageElement = (Element) damage;
						if (damageElement != null) {
							DamageCause cause = XMLUtils.getCause(damageElement.getTextContent());
							if (cause == DamageCause.BLOCK_EXPLOSION) {
								this.self = XMLUtils.parseBoolean(damageElement.getAttributeNode("self").getNodeValue(),
										true);
								this.ally = XMLUtils.parseBoolean(damageElement.getAttributeNode("ally").getNodeValue(),
										true);
								this.enemy = XMLUtils
										.parseBoolean(damageElement.getAttributeNode("enemy").getNodeValue(), true);
								this.other = XMLUtils
										.parseBoolean(damageElement.getAttributeNode("other").getNodeValue(), true);
							}
							causes.add(cause);
						}
					}
				}
			}
			DisableDamageModule damage = new DisableDamageModule(causes);
			damage.setSelf(this.self);
			damage.setAlly(this.ally);
			damage.setEnemy(this.enemy);
			damage.setOther(this.other);
			// Log.info("[self == " + this.self);
			return damage;
		} else {
			return null;
		}
	}

	@Override
	public ModuleCollection<Module> build(Document doc, ModuleFactory fac) {
		return null;
	}
}