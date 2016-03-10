package overcast.pgm.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;

import overcast.pgm.module.modules.team.Team;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.MessageUtils;
import overcast.pgm.util.StringUtils;

public class NickCommand {
 

	@Command(aliases = { "nick" }, usage = "<nickname> |clear| [player]", desc = "Set or clear a nickname for yourself or another player. ")
	public static void nick(final CommandContext args,
			final CommandSender sender) throws CommandException {
	//	String targetName = args.getString(1, null);
		if (sender instanceof Player) {
			OvercastPlayer player = OvercastPlayer.getPlayers((Player) sender);
			String name = player.getName();
			Team team = player.getTeam();

			if (args.argsLength() == 0) {
				player.sendMessage(ChatColor.RED + "/nick <name>");
				return;
			}

			if (args.argsLength() >= 1) {
				String build = StringUtils.build(args.getSlice(1), 0);

				if (build == null || build == ""
						|| build.equals(player.getName())) {
					if (build.equals(player.getName())) {
						MessageUtils
								.warningMessage(
										player.getPlayer(),
										ChatColor.RED
												+ "Your nickname cant be the same as your username");
					}
					return;
				}

				if (build.equalsIgnoreCase("clear")) {
					if (player.hasNickname()) {
						player.setNickname(null, team.getColor() + name);
						player.sendMessage(ChatColor.YELLOW
								+ "You no longer have a nickname");
					} else {
						MessageUtils.warningMessage(player.getPlayer(),
								ChatColor.RED + "/nick <name>");
					}
				} else {
					player.setNickname(build, team.getColor() + build);
					player.sendMessage(ChatColor.YELLOW
							+ "Your nickname is now " + ChatColor.RED + build);
				}
			}
			return;
		} else {
			sender.sendMessage(ChatColor.RED
					+ "You need to be a player to do this command");
		}
		return;
	}
}
