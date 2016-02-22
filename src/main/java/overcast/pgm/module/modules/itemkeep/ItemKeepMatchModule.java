package overcast.pgm.module.modules.itemkeep;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;

public class ItemKeepMatchModule extends MatchModule implements Listener {

	private List<Material> kept;

	public HashMap<Integer, ItemStack> items;

	public ItemKeepMatchModule(Match match, List<Material> kept) {
		super(match);
		this.items = new HashMap<>();
		this.kept = kept;
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
		match.registerEvents(this);
	}

	@Override
	public void disable() {
		HandlerList.unregisterAll(this);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();

		PlayerInventory inv = player.getInventory();
		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack stack = inv.getItem(i);

			if (stack != null) {
				for (Material mat : this.kept) {
					if (stack.getType().equals(mat)) {
						items.put(i, stack);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerSpawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();

		PlayerInventory pi = player.getInventory();

		for (Entry<Integer, ItemStack> entry : this.items.entrySet()) {
		     Integer slot = entry.getKey();
		     ItemStack stack = entry.getValue();
		     if(pi.getItem(slot) != null){
		    	 pi.addItem(stack);
		     }else{
				pi.setItem(slot, stack);
			}
		}
	}
}
