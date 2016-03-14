package overcast.pgm.module.modules.timelimit;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import overcast.pgm.OvercastPGM;
import overcast.pgm.builder.Builder;
import overcast.pgm.match.MatchHandler;
import overcast.pgm.module.TaskedModule;
import overcast.pgm.result.MatchResult;
import overcast.pgm.result.MatchResults;
import overcast.pgm.util.TimeUtil;

public class TimeModule extends TaskedModule {

	private int time;
	private int count;
	private boolean show;
	private MatchResult result;

	public TimeModule(int time, boolean show, MatchResult result) {
		this.time = time;
		this.show = show;
		this.result = result;
	} 
	@Override
	public void run() {
		if (this.time != 0) {
			this.time--;
			broadcastTimeLeft(this.time, this.show);
		} else {
			result.execute(MatchHandler.getMatchHandler().getMatch());
			Bukkit.broadcastMessage(MatchResults.describe(this.result, ChatColor.WHITE));
			Bukkit.getScheduler().cancelTask(this.count);
		}
	}
 
	public void broadcastTimeLeft(int time, boolean show) {
		if (show) {
			if ((time % 300 == 0 && time != 0) || time == 60 | time == 120 || time == 180 || time == 240 || time == 10
					|| time == 20 || time == 30 || (time != 0 && time <= 5)) {
				Bukkit.broadcastMessage(status(time));
			}
		}
	}

	public ChatColor getStatusColor(int sec) {
		if (sec != 0 && sec <= 5) {
			return ChatColor.DARK_RED;
		} else if (sec < 300 && sec % 60 == 0) {
			if (sec == 60) {
				return ChatColor.YELLOW;
			}
		} else {
			if (sec == 30 || sec == 20 || sec == 10) {
				return ChatColor.GOLD;
			}
		}
		return ChatColor.GREEN;
	}

	@Override
	public String status(int amount) {
		return ChatColor.AQUA + "Time Remaining: " + getFormattedTime(amount);
	}

	private String getFormattedTime(int amount) {
		return getStatusColor(amount) + TimeUtil.formatIntoHHMMSS(amount);
	}
	@Override
	public Class<? extends Builder> builder() {
		return TimeBuilder.class;
	}

	public int getCount() {
		return this.count;
	}

	public int getTime() {
		return this.time;
	}

	@Override
	public void create() {
		this.count = Bukkit.getScheduler().scheduleAsyncRepeatingTask(OvercastPGM.getInstance(), this, 0, 20);
	}
}
