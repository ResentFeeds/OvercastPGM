package overcast.pgm.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import overcast.pgm.event.MatchLoadEvent;

public class MatchListener implements Listener {

	@EventHandler
	public void onMatchLoad(MatchLoadEvent event) {
		Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Currently Playing " + event.getDescription());
	} 
}
