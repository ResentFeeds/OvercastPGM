package overcast.pgm.commands;

import java.util.List;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import overcast.pgm.OvercastPGM;
import overcast.pgm.pagination.pages.LoadedMapsPage;
import overcast.pgm.pagination.pages.RotationMapsPage;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;

public class LoadedMapsCommand {

	@Command(aliases = { "maps", "ml", "maplist" }, desc = "description about all the loaded maps")
	public static void ml(final CommandContext args, CommandSender sender) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			LoadedMapsPage<String> loadedMaps = new LoadedMapsPage<String>();
			List<String> loaded = OvercastPGM.getInstance().getLoader()
					.getLoadedMapNames();
			loadedMaps.display(player, loaded, args.getInteger(0, 1));
		}
	}

	@Command(aliases = { "rotation", "rot", "rota", "maprot", "maprotation" }, desc = "Shows the current map rotation", usage = "[page]", min = 0, max = 1)
	public static void rotation(CommandContext args, CommandSender sender)
			throws CommandException {
		List<String> rotationList = OvercastPGM.getInstance().getRotation()
				.getRotationNames();
		new RotationMapsPage<String>() {
		}.display(sender, rotationList, args.getInteger(0) == 0 ? 1 : 3);
	}
}
