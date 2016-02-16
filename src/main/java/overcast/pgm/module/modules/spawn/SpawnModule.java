package overcast.pgm.module.modules.spawn;

import overcast.pgm.builder.Builder;
import overcast.pgm.module.Module;
import overcast.pgm.module.modules.kits.KitModule;
import overcast.pgm.module.modules.region.Region;
import overcast.pgm.module.modules.team.Team;

public class SpawnModule extends Module{

	private KitModule kit;
	private Region spawn;
	private Team team;
	
	public SpawnModule(Team team, KitModule kit, Region spawn){
		this.team = team;
		this.kit = kit;
		this.spawn = spawn;
	}
	
	@Override
	public Class<? extends Builder> builder() {
		return SpawnBuilder.class;
	}
	
	
	
	public KitModule getKit(){
		return this.kit;
	}
	
	
	public Region getSpawnRegion(){
		return this.spawn;
	}
	
	
	
	public Team getTeam(){
		return this.team;
	}

}
