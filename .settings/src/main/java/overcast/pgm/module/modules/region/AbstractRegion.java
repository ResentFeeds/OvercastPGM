package overcast.pgm.module.modules.region;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import overcast.pgm.module.modules.filter.FilterState;
import overcast.pgm.module.modules.filter.types.AbstractSingleFilter;
import overcast.pgm.util.BlockUtils;

public abstract  class AbstractRegion extends AbstractSingleFilter implements Region {
 
    @Override
    public boolean contains(Location point) {
        return this.contains(point.toVector());
    }

    @Override
    public boolean contains(BlockVector blockPos) {
        return this.contains((Vector) BlockUtils.center(blockPos));
    }

    @Override
    public boolean contains(Block block) {
        return this.contains(BlockUtils.center(block));
    }

    @Override
    public boolean contains(BlockState block) {
        return this.contains(BlockUtils.center(block));
    }

    @Override
    public boolean contains(Entity entity) {
        return this.contains(entity.getLocation().toVector());
    }

    
    @Override
    public FilterState query(Object obj) {
        if(obj instanceof Block) {
            return FilterState.fromBoolean(this.contains((Block) obj));
        } else if(obj instanceof BlockState) {
            return FilterState.fromBoolean(this.contains((BlockState) obj));
        } else if(obj instanceof Entity) {
            return FilterState.fromBoolean(this.contains((Entity) obj));
        } else if(obj instanceof Location) {
            return FilterState.fromBoolean(this.contains((Location) obj));
        } else if(obj instanceof BlockVector) {
            return FilterState.fromBoolean(this.contains((BlockVector) obj));
        } else if(obj instanceof Vector) {
            return FilterState.fromBoolean(this.contains((Vector) obj));
        } else {
            return FilterState.ABSTAIN;
        }
    }
}
