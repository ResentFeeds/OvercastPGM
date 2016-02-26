package overcast.pgm.module.modules.kits;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import overcast.pgm.module.modules.kits.parsers.ItemKitParser;
import overcast.pgm.player.OvercastPlayer;

public class ItemKit {

	ItemStack stack;
	int slot;

	ItemKitParser parser;

	public ItemKit(int slot, ItemStack stack) {
		this.slot = slot;
		this.stack = stack;
	}

	public ItemKit(ItemKitParser parser) {
		this(parser.getSlot(), parser.getItemStack());
		this.parser = parser;
	}

	public ItemStack getItemStack() {
		return this.stack;
	}

	public int getSlot() {
		return this.slot;
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
