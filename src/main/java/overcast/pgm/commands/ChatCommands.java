package overcast.pgm.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;

import overcast.pgm.channels.AdminChannel;
import overcast.pgm.channels.Channel;
import overcast.pgm.channels.GlobalChannel;
import overcast.pgm.channels.TeamChannel;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.ChannelUtil;
import overcast.pgm.util.MessageUtils;
import overcast.pgm.util.StringUtils;

public class ChatCommands {

	@Command(aliases = { "g", "global" }, desc = "toggle global channel")
	public static void g(final CommandContext args, CommandSender sender) {
		if (sender instanceof Player) {
			OvercastPlayer player = OvercastPlayer.getPlayers((Player) sender);
			GlobalChannel global = ChannelUtil.getGlobalChannel();
			if (args.argsLength() == 0) {
				if (player.hasChannel()) {
					Channel channel = player.getChannel();
					channel.removeMember(player);
					global.addMember(player);
				} else {
					global.addMember(player);
				}

				player.setChannel(global);
			}

			if (args.argsLength() >= 1) {
				String message = StringUtils.build(args.getSlice(1), 0);

				if (message == null || message == "")
					return;

				Bukkit.broadcastMessage(global.format(player, message));
			}
		}
	}

	@Command(aliases = { "a", "admin" }, desc = "toggle admin channel")
	public static void a(final CommandContext args, CommandSender sender) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			OvercastPlayer player = OvercastPlayer.getPlayers(p);
			AdminChannel channel = ChannelUtil.getAdminChannel();
			if (player.hasPermssion(channel.getPermssion())
					|| player.isOperator()) {
				if (args.argsLength() == 0) {
					if (player.hasChannel()) {
						Channel current = player.getChannel();
						current.removeMember(player);
						channel.addMember(player);
					} else {
						channel.addMember(player);
					}
					player.setChannel(channel);
					return;
				}

				if (args.argsLength() >= 1) {
					String message = StringUtils.build(args.getSlice(1), 0);

					if (message == null || message == "") {
						return;
					}

					for (OvercastPlayer members : channel.getMembers()) {
						if (members != null) {
							members.sendMessage(channel.format(player, message));
						}
					}
					return;
				}
			} else {
				MessageUtils.warningMessage(p.getPlayer(), ChatColor.RED
						+ "You need to be op or you need the permssion "
						+ channel.getPermssion().getName());
			}
		}
	}

	@Command(aliases = { "t", "team" }, desc = "toggle admin channel")
	public static void t(final CommandContext args, CommandSender sender) {
		if (sender instanceof Player) {
			OvercastPlayer player = OvercastPlayer.getPlayers((Player) sender);
			TeamChannel teamChannel = (TeamChannel) ChannelUtil
					.getTeamChannel();
			if (args.argsLength() == 0) {
				if (player.hasChannel()) {
					Channel channel = player.getChannel();
					channel.removeMember(player);
					teamChannel.addMember(player);
				} else {
					teamChannel.addMember(player);
				}
				player.setChannel(teamChannel);
			}

			if (args.argsLength() >= 1) {
				String message = StringUtils.build(args.getSlice(1), 0);

				if (message == null || message == "")
					return;

				Team team = player.getTeam();

				if (team != null) {
					for (UUID uuid : team.getMembers()) {
						Player member = Bukkit.getPlayer(uuid);
						if (member != null) {
							member.sendMessage(teamChannel.format(player,
									message));
						}
					}
				}
			}
		}
	}
}