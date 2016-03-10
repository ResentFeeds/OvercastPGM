package overcast.pgm.module.modules.region;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import overcast.pgm.module.modules.filter.Filter;
 

public interface Region extends Filter{
	

    /**
     * Test if the region contains the given point
     */
    boolean contains(Vector point);


    /**
     * Test if the region contains the given point
     */
    boolean contains(Location point);

    /**
     * Test if the region contains the center of the given block
     */
    boolean contains(BlockVector pos);

    /**
     * Test if the region contains the center of the given block
     */
    boolean contains(Block block);

    /**
     * Test if the region contains the center of the given block
     */
    boolean contains(BlockState block);

    /**
     * Test if the region contains the given entity
     */
    boolean contains(Entity entity);
 
} 
