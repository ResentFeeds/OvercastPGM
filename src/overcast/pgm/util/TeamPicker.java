package overcast.pgm.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import overcast.pgm.match.Match;
import overcast.pgm.module.modules.team.Team;

public class TeamPicker {

	Set<Team> teams;

	String title = ChatColor.DARK_RED + "Pick your team";

	public TeamPicker(Match match) {
		this.teams = TeamUtil.getTeamModule().getTeams();

	}

	public Inventory teamPickerView() {
		Inventory inv = Bukkit.createInventory(null, 9, title);

		int members = 0;
		int max = 0;

		int amount = 0;
		for (Team teams : this.teams) {
			amount++;

			for (int i = 0; i < teams.getMembers().size(); i++) {
				members = i;
			}
			
			max = teams.getMax() * amount;
		}

		ItemStack stack = new ItemStack(Material.CHAINMAIL_HELMET);
		ItemMeta meta = stack.getItemMeta();
		ChatColor gray = ChatColor.GRAY;
		ChatColor bold = ChatColor.BOLD;
		ChatColor green = ChatColor.GREEN;
		ChatColor gold = ChatColor.GOLD;
		ChatColor red = ChatColor.RED;
		String title = gray.toString() + bold + "Auto Join";

		meta.setDisplayName(title);
		meta.setLore(Arrays.asList(green + "" + members + gold + " / " + red + max,
				ChatColor.AQUA + "Puts you on the team with the fewest players"));
		stack.setItemMeta(meta);

		int i = 0;
		ItemStack is = null;
		Map<Integer, ItemStack> helmets = new HashMap<>();
		for (Team team : this.teams) {
			is = new ItemStack(Material.LEATHER_HELMET);
			LeatherArmorMeta isMeta = (LeatherArmorMeta) is.getItemMeta();
			isMeta.setColor(team.chatColorToDyeColor());
			isMeta.setDisplayName(team.getColor() + ChatColor.BOLD.toString() + team.getName());
			isMeta.setLore(Arrays.asList(green + "" + team.getMembers().size() + gold +  " / " + red + team.getMax()));
			is.setItemMeta(isMeta);
			helmets.put(++i, is);
		}

		inv.setItem(0, stack);

		for (Entry<Integer, ItemStack> h : helmets.entrySet()) {
			if (h != null) {
				inv.setItem(h.getKey(), h.getValue());
			}
		}

		ItemStack closer = new ItemStack(Material.EYE_OF_ENDER);
		ItemMeta closerMeta = closer.getItemMeta();

		closerMeta.setDisplayName(ChatColor.DARK_RED + "Close");

		closer.setItemMeta(closerMeta);

		inv.setItem(8, closer);
		return inv;

	}
 
}
