package overcast.pgm.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;

import overcast.pgm.OvercastPGM;
import overcast.pgm.map.Map;
import overcast.pgm.match.Match;
import overcast.pgm.module.modules.info.InfoModule;

public class MapInfoCommand {

	@Command(aliases = { "mapinfo", "mi" }, desc = "description about a certain map", usage = "<map>", min = 0, max = 1)
	public static void mapinfo(final CommandContext args, CommandSender sender) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			Match match = OvercastPGM.getInstance().getMatch();
			if (args.argsLength() == 0) {
				Map map = match.getMap();

				InfoModule info = map.getInfo();

				player.sendMessage(info.getFormattedMapTitle());
				info.getMapInformation(player);
				return;
			}

			if (args.argsLength() >= 1) {
				String name = args.getJoinedStrings(0);

				if (name == null || name == "")
					return;

				Map map = OvercastPGM.getInstance().getLoader().getMap(name);

				if (map == null) {
					player.sendMessage(ChatColor.RED + "No results matched");
					return;
				}

				InfoModule info = map.getInfo();
				player.sendMessage(info.getFormattedMapTitle());
				info.getMapInformation(player);
				return;
			}
		}
	}
}