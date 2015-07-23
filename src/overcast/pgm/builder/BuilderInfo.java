package overcast.pgm.builder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import overcast.pgm.module.ModuleStage;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BuilderInfo {

	public boolean documentable() default true;
	
	ModuleStage stage() default ModuleStage.START;
}
