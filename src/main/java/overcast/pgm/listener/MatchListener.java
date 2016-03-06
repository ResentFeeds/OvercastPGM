package overcast.pgm.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import overcast.pgm.event.MatchBeginEvent;
import overcast.pgm.event.MatchLoadEvent;
import overcast.pgm.match.Match;

public class MatchListener implements Listener {

	@EventHandler
	public void onMatchLoad(MatchLoadEvent event) {
		Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Currently Playing " + event.getDescription());
	} 
	
	
	@EventHandler
	public void onMatchBegin(MatchBeginEvent event){
		Match match = event.getMatch();
		
		match.broadcast(ChatColor.GREEN + "The match has started!");
	}
}
