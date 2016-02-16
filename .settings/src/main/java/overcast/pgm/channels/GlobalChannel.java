package overcast.pgm.channels;

import org.bukkit.ChatColor;

import overcast.pgm.module.modules.team.Team;
import overcast.pgm.player.OvercastPlayer;

public class GlobalChannel extends Channel {

	public GlobalChannel() {
		super("Global");
	}

	@Override
	public String format(OvercastPlayer player, String message) {

		String name = player.getName();

		if (player.hasNickname()) {
			name = player.getNickname();
		}

		Team team = player.getTeam();

		return ChatColor.WHITE + "<" + team.getColor() + name + ChatColor.WHITE
				+ ">: " + message;
	}

}
