package overcast.pgm.module.modules.armorkeep;

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
import overcast.pgm.module.modules.kits.ArmorType;
import overcast.pgm.util.BukkitUtils;

public class ArmorKeepMatchModule extends MatchModule implements Listener {

	private List<Material> kept;

	public HashMap<ArmorType, ItemStack> armor;

	public ArmorKeepMatchModule(Match match, List<Material> kept) {
		super(match);
		this.armor = new HashMap<>();
		this.kept = kept;
	}

	Material[] allowed = new Material[] { Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE,
			Material.LEATHER_LEGGINGS, Material.LEATHER_HELMET, Material.CHAINMAIL_BOOTS, Material.CHAINMAIL_CHESTPLATE,
			Material.CHAINMAIL_HELMET, Material.CHAINMAIL_LEGGINGS, Material.IRON_HELMET, Material.IRON_CHESTPLATE,
			Material.IRON_LEGGINGS, Material.IRON_BOOTS, Material.GOLD_HELMET, Material.GOLD_CHESTPLATE,
			Material.GOLD_LEGGINGS, Material.GOLD_BOOTS, Material.DIAMOND_HELMET, Material.DIAMOND_LEGGINGS,
			Material.DIAMOND_CHESTPLATE, Material.DIAMOND_BOOTS };

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
		for (ItemStack stack : inv.getArmorContents()) {
			if (stack != null) {
				if (BukkitUtils.isAllowed(stack.getType(), this.allowed)) {
					if (stack.equals(inv.getArmorContents()[3])) {
						this.armor.put(ArmorType.HELMET, stack);
					}

					if (stack.equals(inv.getArmorContents()[2])) {
						this.armor.put(ArmorType.CHESTPLATE, stack);
					}
					if (stack.equals(inv.getArmorContents()[1])) {
						this.armor.put(ArmorType.LEGGINGS, stack);
					}
					if (stack.equals(inv.getArmorContents()[0])) {
						this.armor.put(ArmorType.BOOTS, stack);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerSpawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();

		PlayerInventory pi = player.getInventory();

		for (Entry<ArmorType, ItemStack> entry : this.armor.entrySet()) {
			ArmorType type = entry.getKey();
			ItemStack stack = entry.getValue();
			switch (type) {
			case BOOTS:
				pi.setBoots(stack);
				break;
			case CHESTPLATE:
				pi.setChestplate(stack);
				break;
			case HELMET:
				pi.setHelmet(stack);
				break;
			case LEGGINGS:
				pi.setLeggings(stack);
				break;
			}
		}
	}

}
