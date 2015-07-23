package overcast.pgm.module;

import overcast.pgm.builder.Builder;

public @interface ModuleInfo {

	public String name();
	
	public String desc();
	
	public Class<? extends Module>[] require() default {};
	
	public Class<? extends Builder> builder();
}
