package overcast.pgm.module.modules.toolrepair;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;

public class ToolRepairMatchModule extends MatchModule implements Listener {

	private List<Material> tools;

	public ToolRepairMatchModule(Match match, List<Material> tools) {
		super(match);
		this.tools = tools;
	}

	@Override
	public void load() {
		match.registerEvents(this);
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
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		if (!event.isCancelled()) {
			ItemStack item1 = event.getItem().getItemStack();
			if (tools.contains(item1.getType()) && event.getPlayer().getInventory().contains(item1.getType())) {
				for (ItemStack item2 : event.getPlayer().getInventory().getContents()) {
					if (item2 != null && toMaxDurability(item1).equals(toMaxDurability(item2))) {
						event.setCancelled(true);
						event.getItem().remove();
						event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ITEM_PICKUP, 0.1F, 1);
						int result = item2.getDurability()
								- (item1.getType().getMaxDurability() - item1.getDurability());
						item2.setDurability((short) (result < 0 ? 0 : result));
						break;
					}
				}
			}
		}
	}

	public static ItemStack toMaxDurability(ItemStack item) {
		ItemStack item2 = new ItemStack(item);
		item2.setDurability(item.getType().getMaxDurability());
		return item2;
	}

	public int doAgebra(int t, int f, int n, float d) {
		return t = (int) (f + ((n - 1) * d));
	}
}
