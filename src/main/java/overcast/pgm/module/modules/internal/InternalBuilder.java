package overcast.pgm.module.modules.internal;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.util.XMLUtils;

@BuilderInfo()
public class InternalBuilder extends Builder {

	@Override
	public ModuleCollection<Module> build(Document doc) {
		ModuleCollection<Module> modules = new ModuleCollection<>(); 
		Element root = doc.getDocumentElement();
		
		boolean internal = true;

		if (root.hasAttribute("internal")) {
			internal = XMLUtils.parseBoolean(root.getAttribute("internal"));
		}
		InternalModule internalModule =  new InternalModule(internal);
		modules.add(internalModule);
		
		return modules;
	}
}
