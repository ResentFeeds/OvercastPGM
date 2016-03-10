package overcast.pgm.util;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import overcast.pgm.match.MatchHandler;
import overcast.pgm.module.modules.spawn.Spawn;
import overcast.pgm.module.modules.spawn.SpawnModule;
import overcast.pgm.module.modules.team.Team;

public class LocationUtils {

	public static Location newLocation(int x, int y, int z) {
		return new Location(MatchHandler.getMatchHandler().getMatch().getWorld(), x, y, z);
	}

	public static Location convertVectorToLocation(Vector vec) {
		return vec.toLocation(MatchHandler.getMatchHandler().getMatch().getWorld());
	}

	public static SpawnModule getSpawnModule() {
		SpawnModule spawn = MatchHandler.getMatchHandler().getMatch().getModules().getModule(SpawnModule.class);
		return spawn;
	}

	public static Spawn getSpawn(Team team) {
		List<Spawn> spawns = getSpawnModule().getSpawns();

		for (int i = 0; i < spawns.size(); i++) {
			Spawn spawn = spawns.get(i);

			if (spawn != null) {
				if (spawn.getTeam().getName().equals(team.getName())) {
					return spawn;
				}
			}
		}

		if (team.equals(TeamUtil.getTeamModule().getObservers())) {
			return getSpawnModule().getDefaultSpawn();
		}

		return getSpawnModule().getDefaultSpawn();
	}
}
