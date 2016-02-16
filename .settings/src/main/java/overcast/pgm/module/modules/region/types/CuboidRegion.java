package overcast.pgm.module.modules.region.types;

import javax.annotation.Nullable;

import org.bukkit.util.Vector;

import com.google.common.base.Optional;

import overcast.pgm.module.modules.filter.types.CarryingFilter;
import overcast.pgm.module.modules.region.AbstractRegion;

public class CuboidRegion extends AbstractRegion {

	private final Vector min;
	private final Vector max;
	private Optional<CarryingFilter> carry;

	//TODO add filters */
	
	public CuboidRegion(Vector min, Vector max, @Nullable CarryingFilter carry) {
		this.min = min;
		this.max = max;
		this.carry = Optional.fromNullable(carry);
	}

	@Override
	public boolean contains(Vector point) {
		return ((point.getX() >= min.getX()) && (point.getX() <= max.getX())
				&& (point.getY() >= min.getY()) && (point.getY() <= max.getY())
				&& (point.getZ() >= min.getZ()) && (point.getZ() <= max.getZ()));
	}
	
	
	public boolean hasCarryFilter(){
		return this.getCarryFilter().isPresent();
	}

    public Optional<CarryingFilter> getCarryFilter() {
		return this.carry;
	}
}
