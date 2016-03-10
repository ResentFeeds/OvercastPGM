package overcast.pgm.module;

import com.google.common.base.Preconditions;

public class ModuleAbout {

	private ModuleInfo info;

	public ModuleAbout(Class<? extends Module> module){
		Preconditions.checkArgument(module.isAnnotationPresent(ModuleInfo.class), "no @ModuleInfo annotation present!");
		this.info = module.getAnnotation(ModuleInfo.class);
	}
	
	
	public ModuleInfo getInfo(){
		return this.info;
	}
}
