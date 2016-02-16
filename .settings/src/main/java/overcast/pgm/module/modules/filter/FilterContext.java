package overcast.pgm.module.modules.filter;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.material.MaterialData;

import overcast.pgm.module.ModuleFactory;
import overcast.pgm.module.modules.filter.types.ClassFilter;
import overcast.pgm.module.modules.filter.types.StaticFilter;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.ContextStore;

public class FilterContext extends ContextStore<Filter> {

	private FilterParser parser;

	public FilterContext(ModuleFactory factory) {
		this.add("allow-all", new StaticFilter(FilterState.ALLOW));
		this.add("deny-all", new StaticFilter(FilterState.DENY));
		this.addDefaultFilter("players", Player.class, OvercastPlayer.class);
		this.addDefaultFilter("blocks", Block.class, BlockState.class, MaterialData.class);
		this.addDefaultFilter("world", World.class);
		this.addDefaultFilter("spawns", SpawnReason.class);
		this.addDefaultFilter("entities", Entity.class);
		this.addDefaultFilter("mobs", LivingEntity.class);

		this.parser = createParser(factory);
	}

	public FilterParser createParser(ModuleFactory modules) {
		return new FilterParser(modules, this, modules.getRegionContext());
	}

	private void addDefaultFilter(String name, Class<?>... classes) {
		this.add("allow-" + name, new ClassFilter(FilterState.ALLOW, classes));
		this.add("deny-" + name, new ClassFilter(FilterState.DENY, classes));
	}
	
	
	
	public FilterParser getParser(){
		return this.parser;
	}
}