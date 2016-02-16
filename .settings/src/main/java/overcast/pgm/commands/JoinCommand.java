package overcast.pgm.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;

import overcast.pgm.module.modules.team.Team;
import overcast.pgm.module.modules.team.TeamManager;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.TeamUtil;

public class JoinCommand {
	
	@Command(aliases = { "join" }, desc = "Join a team ", usage = "<team>")
	public static void join(final CommandContext args, CommandSender sender) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			OvercastPlayer p = OvercastPlayer.getPlayers(player);
			if (args.argsLength() == 0) {
				player.sendMessage(ChatColor.RED + "/join <team>");
				return;
			}

			if (args.argsLength() == 1) {
				if (args.getSlice(1)[0].equalsIgnoreCase("obs") || args.getSlice(1)[0].equalsIgnoreCase("observers")) {
					Team team = TeamUtil.getTeamModule().getObservers();

					TeamManager.addPlayer(team, p);
					/**
					 * return here so it wont give them the team not found in
					 * query" message
					 */
					return;
				}
			}
			Team team = TeamUtil.getTeam(args.getJoinedStrings(0));

			if (team == null) {
				player.sendMessage(ChatColor.RED + "team not found in query");
			} else {
				TeamManager.addPlayer(team, p);
			}
		}
	}
}