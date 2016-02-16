package overcast.pgm.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.module.modules.team.TeamManager;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.TeamUtil;

public class PlayerListener implements Listener {
	
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		OvercastPlayer player = new OvercastPlayer(event.getPlayer());
		player.add();
		TeamManager.addPlayer(TeamUtil.getTeamModule().getObservers(), player);
		
		
	}
	
	
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		OvercastPlayer player = OvercastPlayer.getPlayers(event.getPlayer());
		Team team = player.getTeam(); 
		team.removePlayer(player.getPlayer().getUniqueId());
		player.remove();
	}
}
