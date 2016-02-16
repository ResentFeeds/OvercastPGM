package overcast.pgm.module.modules.itemremove;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;

public class ItemRemoveMatchModule extends MatchModule implements Listener {

	private List<Material> removed;

	public ItemRemoveMatchModule(Match match, List<Material> removed) {
		super(match);
		this.removed = removed;
	}

	@Override
	public void load() {
	}

	@Override
	public void unload() {
		HandlerList.unregisterAll(this);
	}

	@Override
	public void enable() {
		this.match.registerEvents(this);
	}

	@Override
	public void disable() {
		HandlerList.unregisterAll(this);
	}

	@EventHandler
	public void onItemSpawn(ItemSpawnEvent event) {
		ItemStack dropped = event.getEntity().getItemStack();

		for (Material d : this.removed) {
			if (dropped.getType().equals(d)) {
				event.setCancelled(true);
			}
		}
	}

}
