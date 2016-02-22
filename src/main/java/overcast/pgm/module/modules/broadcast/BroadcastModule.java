package overcast.pgm.module.modules.broadcast;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import overcast.pgm.builder.Builder;
import overcast.pgm.match.MatchHandler;
import overcast.pgm.module.TaskedModule;
import overcast.pgm.timer.MatchTimer;

public class BroadcastModule extends TaskedModule {

	private int after;
	private int every;
	private int count;
	private String message;
	private BroadcastType type;

	private int timesBroadcasted;

	public BroadcastModule(BroadcastType type, int after, int every, int count, String message) {
		this.type = type;
		this.after = after;
		this.every = every;
		this.count = count;
		this.message = message;
		
		this.timesBroadcasted = 0; 
	}  
	 
	
	public BroadcastType getType(){
		return this.type;
	}

	public int getAfter() {
		return this.after;
	}

	public int getEvery() {
		return this.every;
	}

	public int getCount() {
		return this.count;
	}

	public String getMessage() {
		return this.message;
	}

	@Override
	public Class<? extends Builder> builder() {
		return BroadcastBuilder.class;
	}



	@Override
	public void run() {
		 if (MatchHandler.getMatchHandler().getMatch().isRunning()) {
	            if (timesBroadcasted < count) {
	                if (MatchTimer.getTimer().getSeconds() >= (after + (every * timesBroadcasted))) {
	                    if (type.equals(BroadcastType.TIP))
	                        Bukkit.broadcastMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "[" + ChatColor.BLUE + "" + ChatColor.BOLD + "Tip" + ChatColor.GRAY + "" + ChatColor.BOLD + "] " + ChatColor.AQUA + "" + ChatColor.ITALIC + message);
	                    else if (type.equals(BroadcastType.ALERT))
	                    	 Bukkit.broadcastMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "[" + ChatColor.YELLOW + "" + ChatColor.BOLD + "Alert" + ChatColor.GRAY + "" + ChatColor.BOLD + "] " + ChatColor.GREEN + "" + ChatColor.ITALIC + message);
	                    timesBroadcasted++;
	                }
	            }
	        }
	}


	@Override
	public void create() {
	}


	@Override
	public String status(int amount) {
		return "";
	} 
}
