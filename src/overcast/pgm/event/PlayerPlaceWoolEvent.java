package overcast.pgm.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import overcast.pgm.module.modules.objective.wool.WoolObjective;
import overcast.pgm.player.OvercastPlayer;

public class PlayerPlaceWoolEvent extends Event {

	protected static final HandlerList handlers = new HandlerList();

	private OvercastPlayer player;

	private WoolObjective wool;

	public PlayerPlaceWoolEvent(OvercastPlayer player, WoolObjective wool) {
		this.player = player;
		this.wool = wool;
	}

	public OvercastPlayer getWhoPlaced() {
		return this.player;
	}

	public WoolObjective getWoolObjective() {
		return this.wool;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
