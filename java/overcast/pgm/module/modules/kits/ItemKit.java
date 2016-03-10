package overcast.pgm.module.modules.kits;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import overcast.pgm.module.modules.kits.parsers.ItemKitParser;
import overcast.pgm.module.modules.projectiles.custom.ProjectileModule;
import overcast.pgm.player.OvercastPlayer;

public class ItemKit {

	ItemStack stack;
	int slot;

	ItemKitParser parser;
	ProjectileModule projectile;
	Set<Material> canDestroy;
    Set<Material> canPlaceOn; // need to implement

	public ItemKit(int slot, ItemStack stack, ProjectileModule projectile, Set<Material> canDestroy, Set<Material> canPlaceOn) {
		this.slot = slot;
		this.stack = stack;
		this.projectile = projectile;
		this.canDestroy = canDestroy;
		this.canPlaceOn = canPlaceOn;
	}

	public ItemKit(ItemKitParser parser) {
		this(parser.getSlot(), parser.getItemStack(), parser.getProjectile(), parser.canDestroy(), parser.canPlaceOn());
		this.parser = parser;
	}

	public ItemStack getItemStack() {
		return this.stack;
	}

	public int getSlot() {
		return this.slot;
	}

	public ProjectileModule getProjectile() {
		return this.projectile;
	}

	public boolean isProjectile() {
		return this.projectile != null;
	} 


	public Set<Material> canDestroy() {
		return this.canDestroy;
	} 
	

	public Set<Material> canPlaceOn() {
		return this.canPlaceOn;
	}


	public void apply(OvercastPlayer p, boolean force) {
		PlayerInventory inv = p.getPlayer().getInventory();
		
		if (inv.getItem(slot) == null || force) {
			inv.setItem(slot, stack);
		} else {
			inv.addItem(stack);
		}
	}

}
