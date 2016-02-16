package overcast.pgm.module.modules.region.types;

import org.bukkit.util.Vector;

import overcast.pgm.module.modules.region.AbstractRegion;

public class CircleRegion extends AbstractRegion {

	protected final double x; 
	protected final double z;
	protected final double radius;
	protected final double radiusSq; 

	public CircleRegion(double x, double z, double radius) {
		this.x = x;
		this.z = z; 
		this.radius = radius;
		this.radiusSq = (radius * radius);
	}

	/** todo make this dont check where the y is at */
	@Override
	public boolean contains(Vector point) {
		double dx = point.getX() - this.x;
		double dz = point.getZ() - this.z; 
		return Math.pow(dx, 2) + Math.pow(dz, 2) <= this.radiusSq;
	} 
}
