package overcast.pgm.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
 
import overcast.pgm.OvercastPGM;
import overcast.pgm.match.MatchState;

public class ConnectionListener implements Listener {

	@EventHandler
	public void onServerPing(ServerListPingEvent event) {
		event.setMotd(MatchState.toString(OvercastPGM.getInstance().getMatch()));
	}
}
