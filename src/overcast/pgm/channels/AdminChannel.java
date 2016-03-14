package overcast.pgm.channels;

import org.bukkit.ChatColor;
import org.bukkit.permissions.Permission;

import overcast.pgm.module.modules.team.Team;
import overcast.pgm.player.OvercastPlayer;
import static org.bukkit.ChatColor.*;

public class AdminChannel extends PrivateChannel {

	public AdminChannel() {
		super("Admin", new Permission("overcast.admin.receive"));
	}

	@Override
	public String format(OvercastPlayer player, String message) {
		String name = player.getPlayerName();
		String prefix = "[Admin]";  
		Team team = player.getTeam();
		return prefix + WHITE + " <" + (player.isAuthor() ? ChatColor.DARK_AQUA + "*" : "") + team.getColor() + name
				+ ChatColor.WHITE + ">: " + message;
	}
}
