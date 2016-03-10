package overcast.pgm.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;

import overcast.pgm.OvercastPGM;
import overcast.pgm.pagination.pages.RotationMapsPage;

public class RotationCommand {
    /** fix */
	@Command(aliases = { "rotation", "rot" }, desc = "all the current maps in the rotation")
	public static void rotation(final CommandContext args, CommandSender sender) {
		RotationMapsPage<String> rotation = new RotationMapsPage<String>();
		if (args.argsLength() == 0) {
			rotation.display(sender, OvercastPGM.getInstance().getRotation()
					.getRotationNames(), 1);
		}

		if (args.argsLength() == 1) {
			int page = args.getInteger(1);
			if (page != 0 || page < rotation.getMaxPages()) {
				rotation.display(sender, OvercastPGM.getInstance()
						.getRotation().getRotationNames(), page);
			} else {
				sender.sendMessage(new String[] { ChatColor.RED
						+ "Unknown page selected! " + rotation.getMaxPages()
						+ " total pages." });
			}
		}
	}

}
