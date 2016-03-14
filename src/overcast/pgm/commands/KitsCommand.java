package overcast.pgm.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;

import overcast.pgm.module.modules.kits.KitModule;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.KitUtils;

public class KitsCommand {

	@Command(aliases = "kit", desc = "a kits testing command", min = 0, max = 1, usage = "[id]")
	public static void kits(final CommandContext args, CommandSender sender) {
		if (sender instanceof Player) {
			OvercastPlayer p = OvercastPlayer.getPlayers((Player) sender);
			if (!KitUtils.getKits().isEmpty()) {
				if (args.argsLength() == 0) {
					for (KitModule kitModule : KitUtils.getKits()) {
						if (kitModule != null) {
							p.sendMessage(kitModule.getID());
						}
					}
					return;
				} 

				KitModule kit = KitUtils.getKit(args.getJoinedStrings(0));

				if (kit != null) {
					kit.applyKit(p);
				} else {
					sender.sendMessage(ChatColor.RED + "No kit found by that name!");
				}
			}else{
				p.sendMessage(ChatColor.RED + "No kits found on this map!");
			}
		}
	}
}
