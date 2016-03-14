package overcast.pgm.util;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import overcast.pgm.match.MatchHandler;
import overcast.pgm.module.modules.kits.KitModule;
import overcast.pgm.module.modules.tutorial.TutorialModule;
import overcast.pgm.player.OvercastPlayer;

public class KitUtils {

	public static List<KitModule> getKits() {
		List<KitModule> kitModules = MatchHandler.getMatchHandler().getMatch().getModules().getModules(KitModule.class);
		return kitModules;
	}

	public static KitModule getKit(String id) {
		for (KitModule kitModule : getKits()) {
			if (kitModule != null) {
				if (kitModule.getID().equals(id)) {
					return kitModule;
				}
			}
		}
		return null;
	}

	public static void giveObsKit(OvercastPlayer player) {
		player.getInventory().clear();
		ChatColor bold = ChatColor.BOLD;
		ItemStack compass = new ItemStack(Material.COMPASS);
		ItemMeta compassMeta = compass.getItemMeta();

		compassMeta.setDisplayName(ChatColor.BLUE + "" + bold + "Teleporter Tool");
		compass.setItemMeta(compassMeta);
		player.getInventory().setItem(0, compass);

		ItemStack picker = new ItemStack(Material.LEATHER_HELMET);
		ItemMeta pickerMeta = picker.getItemMeta();
		pickerMeta.setDisplayName(ChatColor.GREEN.toString() + bold + "Team Selection");
		pickerMeta.setLore(Arrays.asList(ChatColor.DARK_PURPLE + "Join the game!"));
		picker.setItemMeta(pickerMeta);
		player.getInventory().setItem(1, picker);

		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 0, (byte) 3);
		SkullMeta headMeta = (SkullMeta) head.getItemMeta();
		headMeta.setOwner(player.getName());
		headMeta.setDisplayName(ChatColor.RED + "Teleporter");
		head.setItemMeta(headMeta);

		player.getInventory().setItem(2, head);

		boolean loaded = MatchHandler.getMatchHandler().getMatch().getModules().isModuleLoaded(TutorialModule.class);
		boolean hastntDefuser = player.getPlayer().hasPermission("overcast.tntdefuse") || player.isOperator();

		if (loaded) {
			ItemStack stack = player.getItem();
			player.getInventory().setItem(3, stack);
		}

		// TODO make shear listener
		if (hastntDefuser) {
			ItemStack shears = new ItemStack(Material.SHEARS);
			ItemMeta shearMeta = shears.getItemMeta();

			shearMeta.setDisplayName(ChatColor.AQUA + "TNT Defuser");

			shears.setItemMeta(shearMeta);

			player.getInventory().setItem(4, shears);
		}
	}
}
