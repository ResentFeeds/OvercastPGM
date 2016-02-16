package overcast.pgm.module.modules.friendlyfire;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.ModuleFactory;
import overcast.pgm.util.XMLUtils;
import overcast.pgm.xml.XMLParseException;

/***
 * 
 * @author tylern
 *
 * NOTES: Need to work on this module.
 */
@BuilderInfo()
public class FriendlyFireBuilder extends Builder {

	@Override
	public ModuleCollection<Module> build(Document doc) throws XMLParseException {
		ModuleCollection<Module> modules = new ModuleCollection<>();
		Element root = doc.getDocumentElement();
		FriendlyFireModule module = parseFriendlyFire(root, "friendlyfire", "friendlyfirerefund");
		modules.add(module);
		return modules;
	}

	private FriendlyFireModule parseFriendlyFire(Element root, String friendlyfireTag, String refundTag) {
		Element element = (Element) root.getElementsByTagName(friendlyfireTag).item(0);
		boolean friendlyfire = XMLUtils.parseBoolean(element.getTextContent(),
				false);
		Element refundElement = (Element) root.getElementsByTagName(refundTag).item(0);
		boolean friendlyfirerefund = XMLUtils.parseBoolean(refundElement.getTextContent(), false);
		return element == null ? null : (new FriendlyFireModule(friendlyfire, friendlyfirerefund));
	}

	@Override
	public ModuleCollection<Module> build(Document doc, ModuleFactory fac) {
		
		return null;
	}

}
