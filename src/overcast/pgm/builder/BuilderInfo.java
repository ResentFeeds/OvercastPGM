package overcast.pgm.builder;

import overcast.pgm.module.ModuleStage;

public @interface BuilderInfo {

	public boolean documentable() default true;
	
	ModuleStage stage() default ModuleStage.START;
}
