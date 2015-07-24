package overcast.pgm.module.modules.internal;

import org.bukkit.event.HandlerList;

import overcast.pgm.builder.Builder;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleInfo;

@ModuleInfo(name = "internal", desc = "Prevent compass teleports above Y 255")
public class InternalModule extends Module{

	// Not done with this yet 
	
	private boolean internal;

	public InternalModule(){ 
	}
	
	public InternalModule(boolean internal){
		this.internal = internal;
	}
	
	@Override
	public void unload() {
		HandlerList.unregisterAll(this);
	}

	@Override
	public Class<? extends Builder> builder() {
		return InternalBuilder.class;
	}

	public boolean isInternal(){
		return this.internal;
	}
}
