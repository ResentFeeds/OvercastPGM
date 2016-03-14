package overcast.pgm.timer;

import org.bukkit.Bukkit;
import static org.bukkit.ChatColor.*;

import overcast.pgm.match.Match;
import overcast.pgm.match.MatchState;

public class StartTimer extends OvercastTimer {

	/**
	 * 
	 * @param sec
	 *            time in seconds
	 * @param match
	 *            the match that this will run in
	 */
	
	 boolean finished = false;
	
	
	public StartTimer(int sec, Match match) {
		super(sec, match);
	}

	@Override
	public void run() {
		/** check if the seconds not equal to zero */
		if (this.sec != 0) {
			this.sec--;
			this.match.setState(MatchState.START);
			if (this.sec % 5 == 0 && this.sec > 5 || this.sec <= 5 && this.sec != 0) {
				Bukkit.getServer().broadcastMessage(status(this.sec));
			}
		} else {
			this.match.start();
			Bukkit.getScheduler().cancelTask(this.timer);
			setFinished(true);
		}
	}

	private String status(int sec) {
		String seconds = null;
		if (sec != 1) {
			seconds = GREEN + " seconds ";
		} else {
			seconds = GREEN + " second ";
		} 

		return GREEN + "Match starting in " + DARK_RED + "" + this.sec
				+ seconds + "";
	}
	
	
	public  void setFinished(boolean value){
		finished = value;
	} 
	
	
	public boolean isFinished(){
		return finished;
	}
}
