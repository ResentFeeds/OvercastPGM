package overcast.pgm.util;

import org.bukkit.Location;
import org.bukkit.util.Vector;
 
import overcast.pgm.OvercastPGM;

public class LocationUtils {

	public static Location newLocation(int x, int y, int z) {
		return new Location(OvercastPGM.getInstance().getMatch().getWorld()	, x, y, z);
	}
	
	
	public static Location convertVectorToLocation(Vector vec){
		return vec.toLocation(OvercastPGM.getInstance().getMatch().getWorld());
	}
	
}
