package overcast.pgm.module.modules.region;

import java.util.Random;

import org.bukkit.util.Vector;

public interface RegionContainer extends Region {

	public Vector getRandom(Random random);
}
