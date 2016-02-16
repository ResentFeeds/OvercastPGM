package overcast.pgm.module.modules.filter.types;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import overcast.pgm.module.modules.filter.FilterState;
import overcast.pgm.util.XMLUtils;

public class CarryingFilter extends AbstractSingleFilter {

	private Material material;

	public CarryingFilter(String attr) {
		super();
		this.material = XMLUtils.parseMaterial(attr);
	}

	@Override
	public FilterState query(Object obj) {
		if (obj instanceof Player) {
			Player player = (Player) obj;

			ItemStack[] inventory = player.getInventory().getContents();

			for (ItemStack stack : inventory) {
				if (stack.getType().equals(this.material)) {
					return FilterState.ALLOW;
				} else {
					return FilterState.DENY;
				}
			}
		}
		return FilterState.ABSTAIN;
	}
}
