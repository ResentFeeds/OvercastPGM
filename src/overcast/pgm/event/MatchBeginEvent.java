package overcast.pgm.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import overcast.pgm.match.Match;

public class MatchBeginEvent extends Event {
	

	
	
	private static final HandlerList handlers = new HandlerList();
	private Match match;

	
	public MatchBeginEvent(Match match){
		this.match = match;
	}
	
	
	public Match getMatch(){
		return this.match;
	}
	
	

	
	public static HandlerList getHandlerList() {
		return handlers;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
}
