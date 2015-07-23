package overcast.pgm.module;

import org.bukkit.event.Listener;

public abstract class Module implements Listener {
	
	public abstract void unload();
	
	
	public ModuleAbout getAbout(){
		return new ModuleAbout(getClass());
	}
}
