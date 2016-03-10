package overcast.pgm.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import overcast.pgm.OvercastPGM;
import overcast.pgm.map.Map;
import overcast.pgm.match.Match;
import overcast.pgm.module.modules.info.InfoModule;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;

public class MapCommands {

	@Command(aliases = { "map", "mapinfo" }, desc = "Shows information about the currently playing map.", usage = "")
	public static void map(final CommandContext args, CommandSender sender)
			throws CommandException { 
		 if(sender instanceof Player){
			 Player player = (Player) sender;
			 
			 if(args.argsLength() == 0){
				 Map map = OvercastPGM.getInstance().getMatch().getMap();
				 InfoModule info = map.getInfo();
				 player.sendMessage(new String[] {info.getFormattedMapTitle()});
				 info.getMapInformation(player);
			 }
			 
			 
			 if(args.argsLength() >= 1){
				 Map map = OvercastPGM.getInstance().getLoader().getMap(args.getJoinedStrings(0));
				 
				 if(map == null){
					 player.sendMessage(ChatColor.RED + "No maps matched query");
					 return;
				 }
				 
				 InfoModule info = map.getInfo();
			     player.sendMessage(info.getFormattedMapTitle());
			     info.getMapInformation(player);
			 }
		 }
	} 
	
	
	@Command(aliases = {"nextmap", "next", "nm" , "mapnext"}, desc = "check out the next map")
	public static void next(final CommandContext args, CommandSender sender){
		Match match = OvercastPGM.getInstance().getMatch();
		Map next = match.getNext();
		
		sender.sendMessage(ChatColor.DARK_PURPLE + "Next map: " + next.getShortDescription());
	} 
	 
}
