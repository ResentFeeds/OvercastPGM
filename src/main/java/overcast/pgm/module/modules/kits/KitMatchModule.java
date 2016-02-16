package overcast.pgm.module.modules.kits;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;

public class KitMatchModule extends MatchModule implements Listener {

	List<ArmorKit> armor;

	public KitMatchModule(Match match, List<ArmorKit> armor) {
		super(match);
		this.armor = armor;
	}

	@Override
	public void load() {
		this.match.registerEvents(this);
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

	/** come back to this later */
	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event) {
		Player player = (Player) event.getWhoClicked();

		if (player != null) {
			Inventory inv = event.getInventory();

			if (inv instanceof PlayerInventory) {
				for (ArmorKit ar : this.armor) {
					for (Integer slot : event.getInventorySlots()) {
						if (slot == 101 || slot == 102 || slot == 103
								|| slot == 104) {
							ItemStack stack = inv.getItem(slot);

							if (ar.isLocked() && ar.getStack().equals(stack)) {
								event.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked(); 
		if (player != null) {
			Inventory inv = event.getInventory();
			if (inv instanceof PlayerInventory ) {
				for (ArmorKit ar : this.armor) {
					if (ar.getStack().equals(event.getCurrentItem())
							&& ar.isLocked()) {
						event.setCancelled(true);
					}
				}
			}
		}
	} 
}
