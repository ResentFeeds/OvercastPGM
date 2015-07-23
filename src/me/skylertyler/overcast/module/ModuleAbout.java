package me.skylertyler.overcast.module;

import com.google.common.base.Preconditions;

public class ModuleAbout {

	private final ModuleInfo info;

	public ModuleAbout(Class<? extends Module> module) {
		Preconditions.checkArgument(
				module.isAnnotationPresent(ModuleInfo.class),
				"the class has no @ModuleInfo class is called " + module);
		this.info = module.getAnnotation(ModuleInfo.class);
	}

	public ModuleInfo getInfo() {
		return this.info;
	}

}
