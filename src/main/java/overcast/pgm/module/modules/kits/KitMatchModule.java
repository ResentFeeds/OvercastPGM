package overcast.pgm.module.modules.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.modules.projectiles.custom.ActionType;
import overcast.pgm.util.Log;

public class KitMatchModule extends MatchModule implements Listener {

	List<ArmorKit> armor;
	List<ItemKit> items;

	List<ItemKit> projectiles;

	public KitMatchModule(Match match, List<ArmorKit> armor, List<ItemKit> items) {
		super(match);
		this.projectiles = new ArrayList<>();
		this.armor = armor;
		this.items = items;

		int i = 0;
		for (ItemKit item : this.items) {
			if (item != null) {
				if (item.isProjectile()) {
					this.projectiles.add(item);
					i++;
				} else {
					Log.info("No projectiles");
				}
			}
		}

		if (this.projectiles.size() != 0) {
			Log.info("there are " + i + " projectiles");
		}
	}

	@Override
	public void load() {
		this.match.registerEvents(this);
	}

	@Override
	public void unload() {
		this.projectiles.clear();
		HandlerList.unregisterAll(this);
	}

	@Override
	public void enable() {
		this.match.registerEvents(this);
	}

	@Override
	public void disable() {
		this.projectiles.clear();
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
						if (slot == 101 || slot == 102 || slot == 103 || slot == 104) {
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
			if (inv instanceof PlayerInventory) {	
				PlayerInventory pi = (PlayerInventory) event.getInventory();
				for (ArmorKit ar : this.armor) {
					 if(ar != null){
						 for(ItemStack armor : pi.getArmorContents()){
							 if(armor != null){
								 if(armor.equals(ar) && ar.isLocked() && event.getCurrentItem().equals(ar.getStack())){
									 if(event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT){
										 event.setCancelled(true);
									 } 
								 }
							 }
						 }
					 }
				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		ItemStack item = player.getItemInHand();

		if (item != null) {
			if (item.getType() != Material.AIR) {
				for (ItemKit itemK : this.projectiles) {
					if (item.getType() == itemK.getItemStack().getType()) {
						ActionType type = itemK.getProjectile().getBoth();
						for (int i = 0; i < type.getActions().length; i++) {
							Action action = type.getActions()[i];
							if (event.getAction() == action) {
								// SEND A MESSAGE
								player.sendMessage("passed all");
							}
						}
					}
				}
			}
		}
	}
}
