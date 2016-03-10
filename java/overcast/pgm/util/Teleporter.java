package overcast.pgm.util;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import overcast.pgm.player.OvercastPlayer;

public class Teleporter {

	private List<OvercastPlayer> players;

	public Teleporter(List<OvercastPlayer> players) {
		this.players = players;
	}

	public void viewInventory(OvercastPlayer viewer) {
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.RED + "Teleporter");

		int i = 0;
		for (OvercastPlayer player : this.players) {
			if (player != null && (!player.getName().equals(viewer.getName()))) {
				ItemStack skullitem = new ItemStack(Material.SKULL_ITEM, 1, (short) 0, (byte) 3);
				SkullMeta skullMeta = (SkullMeta) skullitem.getItemMeta();
				skullMeta.setOwner(player.getName());
				skullMeta.setDisplayName(player.getTeam().getColor() + player.getName());
				skullitem.setItemMeta(skullMeta);
				inv.setItem(i, skullitem);
				i++;
			}
		}
		viewer.openInventory(inv);
	}

	public List<OvercastPlayer> getPlayers() {
		return this.players;
	}

}
