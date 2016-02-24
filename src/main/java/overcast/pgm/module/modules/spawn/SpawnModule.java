package overcast.pgm.module.modules.spawn;

import java.util.List;

import overcast.pgm.builder.Builder;
import overcast.pgm.module.Module;

public class SpawnModule extends Module {

	private List<Spawn> spawns;
	private Spawn def;

	public SpawnModule(List<Spawn> spawns, Spawn def) {
		this.spawns = spawns;
		this.def = def;
	}

	@Override
	public Class<? extends Builder> builder() {
		return SpawnBuilder.class;
	}

	public List<Spawn> getSpawns() {
		return this.spawns;
	} 
	
	
	public Spawn getDefaultSpawn(){
		return this.def;
	}
}
