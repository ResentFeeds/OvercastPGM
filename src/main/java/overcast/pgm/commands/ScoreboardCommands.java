package overcast.pgm.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;

import overcast.pgm.match.Match;
import overcast.pgm.match.MatchHandler;
import overcast.pgm.scoreboard.ScoreboardHandler;

public class ScoreboardCommands {

	@Command(aliases = {"update"}, desc = "update the board", min = 0, max = 0)
	public static void update(final CommandContext args, CommandSender sender){
		if(sender instanceof Player){ 
			Match match = MatchHandler.getMatchHandler().getMatch();
			ScoreboardHandler handler = match.getScoreboardHandler();
			handler.updateBoard();
		}else{
			sender.sendMessage(ChatColor.RED + "A player may only do this.");
		}
	}
}
