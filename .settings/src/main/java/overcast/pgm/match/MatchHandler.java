package overcast.pgm.match;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import overcast.pgm.generator.NullChunkGenerator;
import overcast.pgm.map.Map;
import overcast.pgm.util.FileUtils;
import overcast.pgm.util.Log;

public class MatchHandler {

	private Match match;
	/** the match that it occurs in */

    File source;
	/** the source being copied */
    File dest;

	/** the destination where the source should be copied to */

	public MatchHandler(Match match) {
		this.match = match;
		
		loadMap(match.getMap(), +1);
	}

	public Match getMatch() {
		return this.match;
	}
	
	/** 
	 * @param map
	 *            gets the map you want
	 * @param id
	 *            add +1 every tim you load a map!
	 */

	public void loadMap(Map map, int id) {
		try {
			
			File src = map.getSource();
			File dest = new File(src.getParentFile().getParent(), "match-"
					+ src.getName());
			FileUtils.copyFolder(src, dest);
			String matchName = "match-" + src.getName();
			new File(matchName + File.separator + "level.dat").delete();
			Match match = this.getMatch();
			WorldCreator creator = WorldCreator.name(dest.getName());
			creator.generator(new NullChunkGenerator());
			World world = creator.createWorld();
			world.setSpawnFlags(false, false);
			world.setAutoSave(false); 
			match.setWorld(world);
			
			
			for(Player players : Bukkit.getOnlinePlayers()){
				players.teleport(getMatch().getWorld().getSpawnLocation());
			}
		} catch (IOException e) {
			Log.info("[OvercastPGM] Error while loading map: "
							+ map.getInfo().getName());
			e.printStackTrace();
		}

	}



	public void deleteMatchFolder() {
		FileUtils.deleteDirectory(Bukkit.getWorldContainer());
	}
}
