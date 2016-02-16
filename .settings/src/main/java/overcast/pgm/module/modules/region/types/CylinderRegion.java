package overcast.pgm.module.modules.region.types;

import java.util.Random;

import org.bukkit.util.Vector;

import overcast.pgm.module.modules.region.AbstractRegion;
import overcast.pgm.module.modules.region.RegionContainer;

public class CylinderRegion extends AbstractRegion implements RegionContainer {

	private final Vector base;
	private final double radius;
	private final double radiusSq;
	private final double height;

	public CylinderRegion(Vector base, double radius, double height) {
		this.base = base;
		this.radius = radius;
		this.radiusSq = radius * radius;
		this.height = height;
	}

	@Override
	public boolean contains(Vector point) {
		return point.getY() >= this.base.getY()
				&& point.getY() <= (this.base.getY() + this.height)
				&& Math.pow(point.getX() - this.base.getX(), 2)
						+ Math.pow(point.getZ() - this.base.getZ(), 2) < this.radiusSq;

	}

	@Override
	public Vector getRandom(Random random) {
		double angle = random.nextDouble() * Math.PI * 2;
		double hyp = random.nextDouble() + random.nextDouble();
		hyp = (hyp < 1D ? hyp : 2 - hyp) * this.radius;
		double x = Math.cos(angle) * hyp + this.base.getX();
		double z = Math.sin(angle) * hyp + this.base.getZ();
		double y = this.height * random.nextDouble() + this.base.getY();
		return new Vector(x, y, z);
	}

	public Vector getBase() {
		return base;
	}

	public double getRadius() {
		return radius;
	}

	public double getRadiusSq() {
		return radiusSq;
	}

	public double getHeight() {
		return height;
	}
	
	
	
	

}
