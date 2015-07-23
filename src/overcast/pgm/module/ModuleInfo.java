package overcast.pgm.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import overcast.pgm.builder.Builder;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {

	public String name();
	
	public String desc();
	
	public boolean listener() default true;
	
	public Class<? extends Module>[] requires() default {};
	
	public Class<? extends Builder> builder();
}
