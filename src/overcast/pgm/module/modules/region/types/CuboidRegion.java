package overcast.pgm.module.modules.region.types;

import org.bukkit.util.Vector;

import overcast.pgm.module.modules.region.AbstractRegion;

public class CuboidRegion extends AbstractRegion {

	private final Vector min;
	private final Vector max; 
 
	
	public CuboidRegion(Vector min, Vector max) {
		this.min = min;
		this.max = max; 
	}

	@Override
	public boolean contains(Vector point) {
		return ((point.getX() >= min.getX()) && (point.getX() <= max.getX())
				&& (point.getY() >= min.getY()) && (point.getY() <= max.getY())
				&& (point.getZ() >= min.getZ()) && (point.getZ() <= max.getZ()));
	}
	 
}
