package overcast.pgm.util;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardUtils {

	/**
	 * Return the given text modified for use as a player name on the
	 * scoreboard, which means truncating it and avoiding collisions with real
	 * player names or nicknames;
	 */
	public static String toPlayerName(String text) {
		if (text.matches("^[A-Za-z0-9_]$")) {
			return StringUtils.substring(text, 0, 16);
		} else {
			return StringUtils.substring(text, 0, 15) + ' ';
		}
	}

	public static Team registerTeam(Scoreboard scoreboard, overcast.pgm.module.modules.team.Team team) {
		Team bukkitTeam = registerTeam(scoreboard, team.getName(), team.getColor());
		bukkitTeam.setDisplayName(team.getName()); 
		return bukkitTeam;
	}

	public static Team registerTeam(Scoreboard scoreboard, String name, ChatColor prefix) {
		return registerTeam(scoreboard, name, prefix.toString());
	}

	public static Team registerTeam(Scoreboard scoreboard, String name, String prefix) {
		Team team = scoreboard.registerNewTeam(StringUtils.substring(name, 0, 16));

		team.setPrefix(prefix.toString());
		team.setSuffix(ChatColor.WHITE.toString());

		return team;
	}

	public static void removeTeams(Scoreboard scoreboard) {
		for (Team team : scoreboard.getTeams()) {
			team.unregister();
		}
	}

	public static void removeObjectives(Scoreboard scoreboard) {
		for (Objective objective : scoreboard.getObjectives()) {
			objective.unregister();
		}
	}  
	
	 
}
