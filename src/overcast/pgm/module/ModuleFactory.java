package overcast.pgm.module;

import java.util.ArrayList;
import java.util.List;
 


import org.w3c.dom.Document;

import overcast.pgm.builder.Builder;

public class ModuleFactory {

	public List<Class<? extends Module>> modules;
	public List<Class<? extends Builder>> builders;
	public ModuleRegistry registry;
	public Document doc;

	public ModuleFactory(Document doc) {
		this.modules = new ArrayList<>();
		this.builders = new ArrayList<>();
		this.doc = doc;
		this.registry = new ModuleRegistry(this);
	}

	public ModuleRegistry getRegistry() {
		return this.registry;
	}

	public boolean hasModules() {
		return this.getModules().size() > 0;
	}

	public List<Class<? extends Module>> getModules() {
		return this.modules;
	}

	public List<Class<? extends Builder>> getBuilders() {
		return this.builders;
	}

	public boolean hasBuilders() {
		return this.getBuilders().size() > 0 || this.getBuilders() != null;
	}

	public Document getDocument() {
		return this.doc;
	}

	public Module getModule(String name) {
		for (Class<? extends Module> modules : this.getModules()) {
			try {
				Module module = modules.newInstance();
				if (module.getAbout().getInfo().name().equals(name)) {
					return module;
				}
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
}
