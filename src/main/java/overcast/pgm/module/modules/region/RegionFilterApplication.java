package overcast.pgm.module.modules.region;

import org.bukkit.util.Vector;

import overcast.pgm.module.modules.filter.Filter;
import overcast.pgm.module.modules.kits.KitModule;
import overcast.pgm.module.modules.region.parsers.RegionFilterApplicationParser;

public class RegionFilterApplication  {

	private final RFAScope scope;
	private final Region region;
	private final KitModule kit;
	private final Vector velocity;
	private final boolean relativeVelocity;
	private final String message;
	private final boolean earlyWarning;

	public RegionFilterApplication(RFAScope scope, Region region, Filter filter, KitModule kit, Vector velocity,
			boolean relativeVelocity, String message, boolean earlyWarning) {
		this.scope = scope;
		this.region = region;
		this.kit = kit;
		this.velocity = velocity;
		this.relativeVelocity = relativeVelocity;
		this.message = message;
		this.earlyWarning = earlyWarning;

	}

	public RegionFilterApplication(RegionFilterApplicationParser parser) {
		this(parser.getRFAScope(), parser.getRegion(), null, parser.getKit(), null, false, parser.getMessage(), false);
	}

	public RFAScope getScope() {
		return this.scope;
	}

	public Region getRegion() {
		return region;
	}

	public KitModule getKit() {
		return kit;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public boolean isRelativeVelocity() {
		return relativeVelocity;
	}

	public String getMessage() {
		return message;
	}

	public boolean isEarlyWarning() {
		return earlyWarning;
	}

	public boolean hasMessage() {
		return this.message != null;
	}

	public boolean hasKit() {
		return this.kit != null;
	}
}
