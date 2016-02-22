package overcast.pgm.module.modules.spawn;

import overcast.pgm.builder.Builder;
import overcast.pgm.module.Module;
import overcast.pgm.module.modules.kits.KitModule;
import overcast.pgm.module.modules.region.Region;
import overcast.pgm.module.modules.team.Team;

public class SpawnModule extends Module{
 
	private Spawn spawn; 
	
	public SpawnModule(Spawn spawn){
		this.spawn = spawn;
	}
	
	@Override
	public Class<? extends Builder> builder() {
		return SpawnBuilder.class;
	}
	 

}
