package overcast.pgm.module.modules.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block; 
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.modules.projectiles.custom.ActionType;

public class KitMatchModule extends MatchModule implements Listener {

	List<ArmorKit> armor;
	List<ItemKit> items;

	List<ItemKit> projectiles;

	public KitMatchModule(Match match, List<ArmorKit> armor, List<ItemKit> items) {
		super(match);
		this.projectiles = new ArrayList<>();
		this.armor = armor;
		this.items = items;
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
		// clear
		for (ItemKit item : this.items) {
			if (item != null && !item.canDestroy.isEmpty()) {
				item.canDestroy.clear();
			}
		}
		HandlerList.unregisterAll(this);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();

			PlayerInventory playerInv = player.getInventory();
			for (ItemStack armor : playerInv.getArmorContents()) {
				for (ArmorKit armorK : this.armor) {
					if (armorK.isLocked() && armorK.getStack().isSimilar(armor)) {
						event.setCancelled(true);
					}
				}
			}
		}
	}

	
	//FIXME projectiles dont work at all ;/
	
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

	@EventHandler(ignoreCancelled = false, priority = EventPriority.NORMAL)
	public void canDestroy(BlockBreakEvent event) {
		for (ItemKit item : this.items) {
			if (item.canDestroy.isEmpty()) {
				continue;
			}

			for (Material mat : item.canDestroy()) {
				event.setCancelled(!event.getBlock().getType().equals(mat));
			}
		}
	}

	@EventHandler(ignoreCancelled = false, priority = EventPriority.NORMAL)
	public void canPlaceOn(BlockPlaceEvent event) {
		Block clicked = event.getBlockAgainst();

		for (ItemKit item : this.items) {
			if (item.canPlaceOn.isEmpty()) {
				continue;
			}

		 
			if (item.getItemStack().isSimilar(event.getItemInHand())) {
				for (Material mat : item.canPlaceOn()) {
					event.setCancelled(!clicked.getType().equals(mat));
				}
			}
		}
	}
}
