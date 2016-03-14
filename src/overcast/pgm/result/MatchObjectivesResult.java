package overcast.pgm.result;

import java.util.Set;

import overcast.pgm.match.Match;
import overcast.pgm.module.modules.objective.ObjectiveModule;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.util.TeamUtil;

public class MatchObjectivesResult implements MatchResult {

	@Override
	public void execute(Match match) {
		Team highest = null;
		int mostObj = 0;

		Set<Team> teams = TeamUtil.getTeamModule().getTeams();
		for (Team team : teams) {
			int completedOjectives = 0;
			for (ObjectiveModule obj : match.getWinManager().getObjectives(team)) {
				if (obj.isCompleted()) {
					completedOjectives++;
				}
			}

			if (completedOjectives == mostObj) {
				highest = null;
			} else if (completedOjectives > completedOjectives) {
				highest = team;
				mostObj = completedOjectives;
			}
		}

		match.end(highest);
	}
}
