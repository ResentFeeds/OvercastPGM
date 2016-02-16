package overcast.pgm.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;

import overcast.pgm.module.modules.team.Team;
import overcast.pgm.player.OvercastPlayer;

public class MyTeamCommand {

	@Command(aliases = { "mt", "myteam" }, desc = "check what team you're currently on")
	public static void myteam(final CommandContext args, CommandSender sender) {
		if(sender instanceof Player){
			Player p = (Player) sender;
			OvercastPlayer player = OvercastPlayer.getPlayers(p);
			
			if(player.isObserver()){
				player.sendMessage(ChatColor.RED + "Your not on a team");
			}else{
				Team team = player.getTeam(); 
				player.sendMessage("Your on the " + team.getColor() + team.getName());
			}
		}
	}
}
