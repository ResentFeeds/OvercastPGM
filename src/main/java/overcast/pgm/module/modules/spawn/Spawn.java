package overcast.pgm.module.modules.spawn;

import overcast.pgm.module.modules.kits.KitModule;
import overcast.pgm.module.modules.region.Region;
import overcast.pgm.module.modules.team.Team;

public class Spawn {
      
	private Team team;
	private KitModule kit;
	private Region region;

	public Spawn(Team team, KitModule kit, Region region){
		this.team = team;
		this.kit = kit;
		this.region = region;
	}
	
	
	public Team getTeam(){
		return this.team;
	}
	
	
	public KitModule getKit(){
		return this.kit;
	}
	
	
	public boolean hasKit(){
		return this.getKit() != null;
	}
	
	
	public Region getRegion(){
		return this.region;
	}
}
