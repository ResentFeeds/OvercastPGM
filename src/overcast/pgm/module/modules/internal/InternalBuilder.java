package overcast.pgm.module.modules.internal;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.util.XMLUtils;

@BuilderInfo()
public class InternalBuilder extends Builder {

	@Override
	public Module build(Document doc) {
		Element root = doc.getDocumentElement();
		boolean internal = true;

		if (root.hasAttribute("internal")) {
			internal = XMLUtils.parseBoolean(root.getAttribute("internal"));
		}
		return new InternalModule(internal);
	}
}
