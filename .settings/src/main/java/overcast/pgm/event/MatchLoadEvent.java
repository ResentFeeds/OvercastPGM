package overcast.pgm.event;

import overcast.pgm.map.Map;
import overcast.pgm.match.Match;

public class MatchLoadEvent extends PGMEvent {

	private Match match;
	private Map map;

	public MatchLoadEvent(Match match, Map map) {
		this.match = match;
		this.map = map;
	}

	
	public Match getMatch(){
		return this.match;
	}
	
	
	public String getDescription(){
		return map.getShortDescription();
	}
}
