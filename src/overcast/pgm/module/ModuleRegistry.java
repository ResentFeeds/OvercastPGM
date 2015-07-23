package overcast.pgm.module;

import java.util.ArrayList;
import java.util.List;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.util.Log;

public class ModuleRegistry {

	static List<Class<? extends Module>> modules;
	static List<Class<? extends Builder>> builders;

	// testing this 
	// may change this!
	public ModuleRegistry() {
		modules = new ArrayList<>();
		builders = new ArrayList<>();

		
		loadBuilders();
		loadModules();

		for (Class<? extends Module> module : getModules()) {
			if (hasModules() && hasBuilders()) {
				try {
					ModuleInfo info = module.newInstance().getAbout().getInfo();
					Log.info(info.name() + info.desc());
					for (Class<? extends Builder> builders : getBuilders()) {
						Builder builder = builders.newInstance();
						BuilderInfo binfo = builder.getAbout().getInfo();

						Log.info("documentable = " + binfo.documentable()
								+ "ModuleStage = " + binfo.stage().name());
					}

				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void loadBuilders(){
		registerBuilder(TestBuilder.class);
	}

	public static void registerBuilder(Class<? extends Builder> builder) {
		 builders.add(builder);
	}

	private boolean hasBuilders() {
		return getBuilders().size() > 0;
	}

	private List<Class<? extends Builder>> getBuilders() {
		return builders;
	}

	private void loadModules() {
		registerModule(TestModule.class);
	}

	public boolean hasModules() {
		return getModules().size() > 0 || getModules() != null;
	}

	public static List<Class<? extends Module>> getModules() {
		return modules;
	}

	public static void registerModule(Class<? extends Module> registering) {
		modules.add(registering);
	}
}
