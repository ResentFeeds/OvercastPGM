package overcast.pgm.event;

import org.bukkit.event.Cancellable;

public class CancellableEvent extends PGMEvent implements Cancellable {

	public boolean cancelled;
	
	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		 this.cancelled = cancelled;
	}

}
