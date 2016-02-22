package overcast.pgm.util;

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
}