package overcast.pgm.module;

import overcast.pgm.builder.Builder;
import overcast.pgm.module.modules.info.InfoBuilder;
import overcast.pgm.module.modules.info.InfoModule;

public class ModuleRegistry {

	protected ModuleFactory factory;

	public ModuleRegistry(ModuleFactory factory) {
		this.factory = factory;

		loadModules();
		loadBuilders();
	}

	public void loadBuilders() {
		addBuilder(InfoBuilder.class);
	}

	public ModuleFactory getFactory() {
		return this.factory;
	}

	public void addBuilder(Class<? extends Builder> builder) {
		getFactory().getBuilders().add(builder);
	}

	public void addModule(Class<? extends Module> module) {
		this.getFactory().getModules().add(module);
	}

	public void loadModules() {
		addModule(InfoModule.class);
	}
}
