package overcast.pgm.util;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import overcast.pgm.OvercastPGM;

public class BlockUtils {

	public static Location center(Block block) {
		Location blockLocation = block.getLocation();

		double x = blockLocation.getX() - 0.5D;
		double y = blockLocation.getY() - 0.5D;
		double z = blockLocation.getZ() - 0.5D;
		
		return new Location(OvercastPGM.getInstance().getMatch().getWorld(), x, y, z);
	}

	public static Location center(BlockState block) {
	    Block b = block.getBlock();
	    Location result = center(b);
	    
	    return result;
	}

	public static Vector center(BlockVector blockPos) { 
		return new Vector(blockPos.getX(), blockPos.getY(), blockPos.getZ());
	} 
}
