package overcast.pgm;

import java.util.List;

import overcast.pgm.match.Match;
import overcast.pgm.module.modules.objective.ObjectiveModule;
import overcast.pgm.module.modules.team.Team;

public class WinManager {

	private Match match;

	public WinManager(Match match) {
		this.match = match;
	}

	// fix 
	public List<ObjectiveModule> getObjectives(Team team) {
		return team.getObjectives();
	}  
}
