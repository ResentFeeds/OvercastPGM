package overcast.pgm.generator;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class NullChunkGenerator extends ChunkGenerator {

	public NullChunkGenerator() {
		
	}
	
	
	@Override
	public byte[] generate(World world, Random random, int x, int z) {
		byte[] chunks = new byte[16 * 16 * 256];
		return chunks;
	}
}
