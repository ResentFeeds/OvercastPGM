package overcast.pgm.util;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Color;

import overcast.pgm.match.MatchHandler;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.module.modules.team.TeamModule;

public class TeamUtil {

	public static TeamModule getTeamModule() {
		TeamModule teamModule = (TeamModule) MatchHandler.getMatchHandler().getMatch().getModules()
				.getModule(TeamModule.class);
		return teamModule;
	}

	public static Team getTeam(String name) {
		for (Team all : getTeamModule().getTeams()) {
			if (all.getName().equalsIgnoreCase(name)) {
				return all;
			}
		}
		return null;
	}

	public static Team getTeamByID(String id) {
		for (Team all : getTeamModule().getTeams()) {
			if (all.getID().equalsIgnoreCase(id)) {
				return all;
			}
		}

		return null;
	}

	public static Team getTeam(Color color) {
		for (Team team : getTeamModule().getTeams()) {
			if (team != null) {
				if (team.getColor().name().startsWith(color.toString().replace("_", ""))) {
					return team;
				}
			}
		}
		return null;
	}

	public static Team getTeam(int id) {
		HashMap<Integer, Team> teams = new HashMap<>();

		int i = 0;
		for (Team team : getTeamModule().getTeams()) {
			teams.put(++i, team);
		}

		for (Entry<Integer, Team> entry : teams.entrySet()) {
			if (id == entry.getKey()) {
				return entry.getValue();
			}
		}
		return null;
	}

	public static int getMaxPlayers() {
		int amount = 0;
		int max = 0;
		for (Team team : getTeamModule().getTeams()) {
			amount++;
			max = team.getMax();
		}

		return max * amount;
	}
}