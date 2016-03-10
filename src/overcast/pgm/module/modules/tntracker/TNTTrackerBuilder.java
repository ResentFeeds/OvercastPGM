package overcast.pgm.module.modules.tntracker;

import org.w3c.dom.Document;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.xml.XMLParseException;

@BuilderInfo()
public class TNTTrackerBuilder extends Builder {

	@Override
	public ModuleCollection<Module> build(Document doc) throws XMLParseException {
		ModuleCollection<Module> modules = new ModuleCollection<>();
		TNTTrackerModule tnt = new TNTTrackerModule();
		modules.add(tnt);
		return modules;
	}
}
