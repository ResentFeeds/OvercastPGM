package overcast.pgm.timer;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;

import overcast.pgm.OvercastPGM;
import overcast.pgm.match.Match;

public abstract class OvercastTimer implements Runnable, Cancellable {

	int sec;

	boolean cancelled = false;

	int timer;

	Match match;
	static OvercastTimer instance;

	@SuppressWarnings("deprecation")
	public OvercastTimer(int sec, Match match) {
		instance = this;
		this.sec = sec + 1;
		this.match = match;
		this.timer = Bukkit.getScheduler().scheduleAsyncRepeatingTask(OvercastPGM.getInstance(), this, 0, 20);
	}

	public static OvercastTimer getTimer() {
		return instance;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public abstract void run();

	public int getSeconds() {
		return this.sec;
	}

	public Match getMatch() {
		return this.match;
	}
}
