package overcast.pgm.channels;

import static org.bukkit.ChatColor.*;

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
		 
		if (player.hasNickname()) {
			return team.getColor() + "" + prefix + WHITE + " <" + team.getColor() + player.getNickname() + WHITE + ">: "
					+ message;
		} else {
			return team.getColor() + "" + prefix + WHITE + " <" + team.getColor() + player.getName() + WHITE + ">: "
					+ message;
		}

	}
}
