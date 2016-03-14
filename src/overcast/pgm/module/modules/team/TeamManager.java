package overcast.pgm.module.modules.team;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import overcast.pgm.module.modules.spawn.Spawn;
import overcast.pgm.player.OvercastPlayer; 
import overcast.pgm.util.KitUtils;
import overcast.pgm.util.LocationUtils;
import overcast.pgm.util.MessageUtils;
import overcast.pgm.util.TeamUtil;

public class TeamManager {

	public static void addPlayer(Team newTeam, OvercastPlayer p) {
		Player player = p.getPlayer();
		if (p.getTeam() != newTeam) {
			TeamModule teamModule = TeamUtil.getTeamModule();
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
							KitUtils.giveObsKit(p);
						}
					}
				}
				continue;
			}

		//	BukkitUtils.playerVisibility(p);

			newTeam.addMember(player.getUniqueId());

			Spawn spawn = LocationUtils.getSpawn(newTeam);

			if (spawn.getKit() == null && !p.isObserver()) {
				player.setGameMode(GameMode.SURVIVAL);
			}
			spawn.teleport(p);
			player.setPlayerListName(newTeam.getColor() + p.getPlayerName());
			player.sendMessage("You joined " + newTeam.getColor() + newTeam.getName());
		} else {
			MessageUtils.warningMessage(player, ChatColor.RED + "You are already joined that team!");
		}
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
