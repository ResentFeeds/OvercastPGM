package overcast.pgm.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import overcast.pgm.match.Match;
import overcast.pgm.module.modules.team.Team;

public class ScoreboardUpdateEvent extends Event {

	private String newName;
	private Match match;
	private Team team;

	protected static final HandlerList handlers = new HandlerList();

	public ScoreboardUpdateEvent(Team team, String newName, Match match) {
		this.team = team;
		this.newName = newName;
		this.match = match;
	}

	public String getNewName() {
		return this.newName;
	}

	public Team getTeam() {
		return this.team;
	}

	public Match getMatch() {
		return this.match;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
