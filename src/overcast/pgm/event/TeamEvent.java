package overcast.pgm.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TeamEvent extends Event {

	protected static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
