package overcast.pgm.module.modules.spawn;

import overcast.pgm.module.modules.kits.KitModule;
import overcast.pgm.module.modules.region.Region;
import overcast.pgm.module.modules.region.types.BlockRegion;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.LocationUtils;

public class Spawn {

	private Team team;
	private Region region;
	private KitModule kit;

	// WORK ON LATER
	public Spawn(Team team, Region region, KitModule kit) {
		this.team = team;
		this.region = region;
		this.kit = kit;
	}

	public Team getTeam() {
		return this.team;
	}

	public Region getSpawnRegion() {
		return this.region;
	}

	public KitModule getKit() {
		return this.kit;
	}

	
	//FIX SPAWNS
	public void teleport(OvercastPlayer p) {
		if (this.region == null)
			return;

		Region region = this.getSpawnRegion();

		if (region instanceof BlockRegion) {
			BlockRegion block = (BlockRegion) region;
			p.teleport(LocationUtils.convertVectorToLocation(block.getVector()));
		}
		
		if (this.kit != null) {
			KitModule kit = this.getKit();
			kit.applyKit(p);
		}
	}
}
