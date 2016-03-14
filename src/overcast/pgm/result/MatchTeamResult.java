package overcast.pgm.result;

import overcast.pgm.match.Match;
import overcast.pgm.module.modules.team.Team;

public class MatchTeamResult implements MatchResult {
	
	private final Team winner;

	public MatchTeamResult(Team winner){
		this.winner = winner;
	}
	
	
	public Team getWinner(){
		return winner;
	}

	@Override
	public void execute(Match match) {
		 match.end(winner);
	}  
}
