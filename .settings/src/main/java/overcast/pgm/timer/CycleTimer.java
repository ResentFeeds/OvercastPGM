package overcast.pgm.timer;

import org.bukkit.Bukkit;

import static org.bukkit.ChatColor.*;
import overcast.pgm.map.Map;
import overcast.pgm.match.Match;

public class CycleTimer extends OvercastTimer {

	public CycleTimer(int sec, Match match) {
		super(sec, match);
	}

	@Override
	public void run() {
		if (this.sec != 0) {
			this.sec--;

			if (this.sec % 5 == 0 && this.sec > 5 || this.sec <= 5
					&& this.sec != 0) {
				Bukkit.broadcastMessage(status(this.sec));
			}
		} else {

			Bukkit.getScheduler().cancelTask(this.timer);
		}
	}

	public String status(int sec) {
		Map next = this.match.getNext();
		String result = null;
		if (sec > 1) {
			result = " seconds ";
		} else {
			result = " second ";
		}

		String seconds = result;

		String name = next.getInfo().getName();

		return DARK_AQUA + "Cycling to " + AQUA + name + DARK_AQUA + " in "
				+ DARK_RED + sec + DARK_AQUA + seconds;
	}

}
