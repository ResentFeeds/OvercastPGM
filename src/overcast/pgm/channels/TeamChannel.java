package overcast.pgm.channels;

import static org.bukkit.ChatColor.*;

import org.bukkit.ChatColor;

import overcast.pgm.module.modules.team.Team;
import overcast.pgm.player.OvercastPlayer;

public class TeamChannel extends Channel {

	public TeamChannel() {
		super("Team");
	}

	@Override
	public String format(OvercastPlayer player, String message) {
		String prefix = "[Team]";

		Team team = player.getTeam();

		String name = player.getPlayerName();

		return team.getColor() + "" + prefix + WHITE + " <" + (player.isAuthor() ? ChatColor.DARK_AQUA + "*" : "")
				+ team.getColor() + name + WHITE + ">: " + message;
	}
}
