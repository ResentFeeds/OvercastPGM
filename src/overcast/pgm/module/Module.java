package overcast.pgm.module;

import org.bukkit.event.Listener;

import overcast.pgm.builder.Builder;

public abstract class Module implements Listener {
	
	public abstract void unload();
	
	
	public ModuleAbout getAbout(){
		return new ModuleAbout(getClass());
	}
	
	public abstract Class<? extends Builder> builder();
	
	public boolean hasListener(){
		return this.getAbout().getInfo().listener();
	}

	public boolean hasBuilder() {
		return this.builder() != null;
	}
}
