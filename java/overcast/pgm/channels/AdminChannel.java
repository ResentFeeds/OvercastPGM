package overcast.pgm.channels;

import org.bukkit.ChatColor;
import org.bukkit.permissions.Permission;

import overcast.pgm.module.modules.team.Team;
import overcast.pgm.player.OvercastPlayer;
import static org.bukkit.ChatColor.*;

public class AdminChannel extends PrivateChannel {

	public AdminChannel() {
		super("Admin", new Permission("channel.admin.receive"));
	}

	@Override
	public String format(OvercastPlayer player, String message) {
		String name = player.getName();
		if (player.hasNickname()) {
			name = player.getNickname();
		} 
		String prefix = "[Admin]";

		Team team = player.getTeam();

		return prefix + WHITE + " <" + team.getColor() + name + ChatColor.WHITE + ">: " + message;
	}
}
