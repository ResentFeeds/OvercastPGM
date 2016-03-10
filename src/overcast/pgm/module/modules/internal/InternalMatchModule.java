package overcast.pgm.module.modules.internal;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;

public class InternalMatchModule extends MatchModule implements Listener {

	private boolean internal;

	public InternalMatchModule(Match match, boolean internal) {
		super(match);
		this.internal = internal;
	}

	@Override
	public void load() {
		this.match.registerEvents(this);
	}

	@Override
	public void unload() {
		HandlerList.unregisterAll(this);
	}

	@Override
	public void enable() {
		this.match.registerEvents(this);
	}

	@Override
	public void disable() {
		HandlerList.unregisterAll(this);
	}

	/**
	 * Prevent teleporting outside the map
	 */
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerTeleport(final PlayerTeleportEvent event) {
		if (this.internal) {
			if (event.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN) {
				double fromY = event.getFrom().getY();
				double toY = event.getTo().getY();

				/**
				 * check if its about 255
				 */

				if ((fromY >= 0.0D && fromY < 255.0D)
						&& (toY < 0.0D || toY >= 255.0D)) {
					event.setCancelled(true);
				}
			}
		}
	}
}
