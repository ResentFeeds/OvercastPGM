package overcast.pgm.module.modules.filter.types;

import org.bukkit.inventory.ItemStack;

import overcast.pgm.module.modules.filter.FilterState;
import overcast.pgm.module.modules.kits.ItemKit;

public class WearingFilter extends AbstractSingleFilter {
 
	private ItemKit item;

	public WearingFilter(ItemKit item) {
		this.item = item;
	}

	//Test these out :)
	@Override
	public FilterState query(Object obj) {
		if (obj instanceof ItemStack[]) {
			ItemStack[] stacks = (ItemStack[]) obj;
			for (ItemStack stack : stacks) {
				if (stack != null) {
					if (stack.getType().equals(this.item.getItemStack().getType())) {
						return FilterState.ALLOW;
					} else {
						return FilterState.DENY;
					}
				}
			}
		}
		return FilterState.ABSTAIN;
	}

}
