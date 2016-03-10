package overcast.pgm.commands;

import java.util.Collection;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;

import overcast.pgm.event.ScoreboardUpdateEvent;
import overcast.pgm.match.Match;
import overcast.pgm.match.MatchHandler;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.modules.objective.wool.WoolObjective;
import overcast.pgm.module.modules.projectiles.custom.ProjectileModule;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.module.modules.team.TeamModule;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.BukkitUtils;
import overcast.pgm.util.TeamUtil;

public class TeamCommands {

	static HashMap<String, String> names = new HashMap<>();

	@Command(aliases = { "mt", "myteam" }, desc = "check what team you're currently on")
	public static void myteam(final CommandContext args, CommandSender sender) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			OvercastPlayer player = OvercastPlayer.getPlayers(p);

			if (player.isObserver()) {
				player.sendMessage(ChatColor.RED + "Your not on a team");
			} else {
				Team team = player.getTeam();
				player.sendMessage("Your on the " + team.getColor() + team.getName());
			}
		}
	}

	@Command(aliases = { "obj", "objective" }, desc = "check objectives for a team", usage = "<team>", min = 1)
	public static void obj(final CommandContext args, CommandSender sender) {
		Match match = MatchHandler.getMatchHandler().getMatch();

		if (match.isRunning() || match.isStarting()) {
			boolean teamsLoaded = match.getModules().isModuleLoaded(TeamModule.class);
			boolean woolsLoaded = match.getModules().isModuleLoaded(WoolObjective.class);
			if (teamsLoaded) {
				Team team = TeamUtil.getTeam(args.getString(0));
				if (team != null) {
					if (woolsLoaded) {
						Collection<WoolObjective> wools = WoolObjective.getObjectives(team);
						for (WoolObjective wool : wools) {
							if (wool != null) {
								String completed = wool.isCompleted()
										? " " + ChatColor.GREEN + wool.getCharacter().getUTF()
										: " " + ChatColor.RED + wool.getCharacter().getUTF();
								sender.sendMessage(BukkitUtils.convertDyeColorToChatColor(wool.getColor())
										+ wool.getWoolName() + ChatColor.WHITE + " required: "
										+ (wool.isRequired() ? ChatColor.GREEN + "Yes" : ChatColor.RED + "No")
										+ completed);
							} else {
								// send them a message if there are no wools :)

							}
						}
					}
				}
			}
		}
	}

	// need to make this with joined strings
	@Command(aliases = { "alias" }, desc = "rename a particular team", usage = "<team> <name>", min = 2)
	public static void aliases(final CommandContext args, CommandSender sender) {
		String user = "";
		if (sender instanceof Player) {
			OvercastPlayer player = OvercastPlayer.getPlayers((Player) sender);
			user = player.hasNickname() ? player.getNickname() : player.getName();
		} else {
			user = ChatColor.GRAY + "[" + ChatColor.BLUE + "" + ChatColor.BOLD + "OvercastPGM" + ChatColor.GRAY + "]";
		}

		String name = args.getString(0);
		String newName = args.getJoinedStrings(1);
		Team team = TeamUtil.getTeam(name);

		if (team != null) {
			if (!team.getName().equalsIgnoreCase(newName)) {
				ChatColor teamColor = team.getColor();
				String teamName = teamColor + team.getName();
				Bukkit.broadcastMessage(user + ChatColor.WHITE + " has renamed " + teamName + ChatColor.WHITE + " to "
						+ teamColor + newName);
				ScoreboardUpdateEvent event = new ScoreboardUpdateEvent(team, teamName, MatchHandler.getMatchHandler().getMatch());
				Bukkit.getPluginManager().callEvent(event);
				team.setName(newName);
			}else{
			   sender.sendMessage(ChatColor.RED + "That team is already named that!");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "No team found by " + ChatColor.DARK_RED + team);
		}
	}

	@Command(aliases = { "proj" }, desc = "check projectiles")
	public static void proj(final CommandContext args, CommandSender sender) {
		Match match = MatchHandler.getMatchHandler().getMatch();
		ModuleCollection<Module> modules = match.getModules();
		ModuleCollection<ProjectileModule> projectiles = modules.getModules(ProjectileModule.class);

		for (ProjectileModule projectile : projectiles) {
			if (projectile != null) {
				Bukkit.broadcastMessage(projectile.toString());
			}
		}
	}
}
