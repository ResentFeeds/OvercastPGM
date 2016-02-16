package overcast.pgm.module.modules.region.types;

import org.bukkit.util.Vector;

import overcast.pgm.module.modules.region.AbstractRegion;

public class BlockRegion extends AbstractRegion {

	protected Vector vector;

	public BlockRegion(Vector vector) {
		this.vector = vector;
	}

	@Override
	public boolean contains(Vector point) {
		boolean same = point.getX() == this.vector.getX()
				&& point.getY() == this.vector.getY()
				&& point.getZ() == this.vector.getZ();
		return same;
	}

}
