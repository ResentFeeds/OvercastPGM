package overcast.pgm.module.modules.filter.types;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import overcast.pgm.module.modules.filter.FilterState;
import overcast.pgm.module.modules.kits.ItemKit;

public class CarryingFilter extends AbstractSingleFilter {

	private ItemKit item;

	public CarryingFilter(ItemKit item) {
		super();
		this.item = item;
	}

	@Override
	public FilterState query(Object obj) {
		if (obj instanceof Player) {
			Player player = (Player) obj;

			ItemStack[] inventory = player.getInventory().getContents();
			ItemStack item = this.item.getItemStack();
			for (ItemStack stack : inventory) {
				if (stack.getType().equals(item.getType()) || item.hasItemMeta() ? stack.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName()) : false) {
					return FilterState.ALLOW;
				} else {
					return FilterState.ABSTAIN;
				}
			}
		}
		return FilterState.ABSTAIN;
	}
}
