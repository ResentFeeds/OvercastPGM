package overcast.pgm.result;

import overcast.pgm.match.Match;

public class MatchTieResult implements MatchResult {
	
	@Override
	public void execute(Match match) { 
		match.end(null);
	}

}
