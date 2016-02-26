package overcast.pgm.module.modules.team;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import overcast.pgm.module.modules.spawn.Spawn;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.LocationUtils;
import overcast.pgm.util.TeamUtil;

public class TeamManager {

	public static void addPlayer(Team newTeam, OvercastPlayer p) {
		TeamModule teamModule = TeamUtil.getTeamModule();
		Player player = p.getPlayer();
		player.getInventory().setArmorContents(null);
		player.getInventory().clear();
		p.setWalkSpeed(0.2f);
		for (Team team : teamModule.getTeams()) {
			if (team.isMember(player.getUniqueId())) {
				team.removePlayer(player.getUniqueId());
			} else {
				Team obs = teamModule.getObservers();

				if (obs.isMember(player.getUniqueId())) {
					obs.removePlayer(player.getUniqueId());
				} else {
					if (newTeam.equals(teamModule.getObservers())) {
						player.setGameMode(GameMode.CREATIVE);
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
					}
				}
			}
			continue;
		}

		newTeam.addMember(player.getUniqueId());

		String name = p.hasNickname() ? p.getNickname() : p.getName();

		Spawn spawn = LocationUtils.getSpawn(newTeam);
		spawn.teleport(p);
		player.setPlayerListName(newTeam.getColor() + name);
		player.sendMessage("You joined " + newTeam.getColor() + newTeam.getName());
	}

	public static Team getTeam(OvercastPlayer player) {
		TeamModule teamModule = TeamUtil.getTeamModule();
		Player p = player.getPlayer();
		UUID uuid = p.getUniqueId();

		Team observers = teamModule.getObservers();

		if (observers.isMember(uuid)) {
			return observers;
		} else {
			for (Team team : teamModule.getTeams()) {
				if (team.isMember(uuid)) {
					return team;
				}
			}
		}

		return null;
	}
}
