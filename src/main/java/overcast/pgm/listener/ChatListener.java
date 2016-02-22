package overcast.pgm.listener;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import overcast.pgm.channels.AdminChannel;
import overcast.pgm.channels.GlobalChannel;
import overcast.pgm.channels.TeamChannel;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.player.OvercastPlayer;

public class ChatListener implements Listener {

	// TODO clean this up!
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		OvercastPlayer player = OvercastPlayer.getPlayers(event.getPlayer());
		String message = event.getMessage(); 
		if (player.getChannel() instanceof GlobalChannel) {
			event.setCancelled(true);
			GlobalChannel global = (GlobalChannel) player.getChannel();
			Bukkit.broadcastMessage(global.format(player, message));
		} else if (player.getChannel() instanceof TeamChannel) {
			event.setCancelled(true);
			TeamChannel teamC = (TeamChannel) player.getChannel();
			Team team = player.getTeam();

			List<UUID> members = team.getMembers();

			event.setFormat(teamC.format(player, message));

			for (UUID uuid : members) {
				Player member = Bukkit.getPlayer(uuid);

				if (member != null) {
					member.sendMessage(event.getFormat());
				}
			}
		} else if (player.getChannel() instanceof AdminChannel) {
			AdminChannel admin = (AdminChannel) player.getChannel();

			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p != null) {
					OvercastPlayer overcast = OvercastPlayer.getPlayers(p);
					if (overcast.isOperator() || overcast.hasPermssion(admin.getPermssion())) {
						event.setCancelled(true);
						overcast.sendMessage(admin.format(player, message));
					}
				}
			}
		}

	}
}
