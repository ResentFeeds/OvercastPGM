package overcast.pgm.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import overcast.pgm.OvercastPGM;
import overcast.pgm.match.Match;
import overcast.pgm.module.modules.filter.Filter;
import overcast.pgm.module.modules.filter.FilterContext;
import overcast.pgm.module.modules.filter.FilterModule;
import overcast.pgm.module.modules.filter.FilterNode;
import overcast.pgm.util.XMLUtils;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;

public class CubeCommand {

	@Command(aliases = "cube", desc = "generate a cube at the players position", usage = "<radius> <material>")
	public static void cube(final CommandContext args, CommandSender sender) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			int radius = 2;
			if (args.argsLength() == 0) {
				spawnCube(player, Material.STONE, radius);
				return;
			}

			if (args.argsLength() == 2) {
				radius = args.getInteger(0);
				String material = args.getJoinedStrings(1);
				Material result = XMLUtils.parseMaterial(material);

				if (result == null) {
					player.sendMessage(ChatColor.RED + "The material of " + ChatColor.DARK_RED + material
							+ ChatColor.RED + " is invalid!");
					return;
				}
				spawnCube(player, result, radius);
				return;
			}
		}
	}

	@Command(aliases = "filters", desc = "a filters command")
	public static void filters(final CommandContext args, CommandSender sender) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			Match match = OvercastPGM.getInstance().getMatch();
			FilterModule filterMod = match.getModules().getModule(FilterModule.class);

			if (filterMod != null) {
				FilterContext context = match.getFactory().getFilterContext();

				for (Filter entry : context.getAll()) {
					if (context.getName(entry) != null) {
						player.sendMessage(context.getName(entry));
					}
				}

				FilterNode node = filterMod.getFilterNode();
				List<Filter> children = node.getChildren();

				for (Filter filter : children) {
					if (filter != null) {
						if (context.getName(filter) != null) {
							player.sendMessage(context.getName(filter));
						}
					}
				}
			}
		}
	}

	public static void spawnCube(Player player, Material mat, int radius) {
		Location location = player.getLocation();
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();

		/** subtract 1 to each so it will same size as the radius */
		for (int bx = x; bx >= x - radius + 1; bx--) {
			for (int by = y; by <= y + radius - 1; by++) {
				for (int bz = z; bz <= z + radius - 1; bz++) {
					Location loc = new Location(player.getWorld(), bx, by, bz);

					Block block = loc.getBlock();

					block.setType(mat);
				}
			}
		}
	}
	
	
	public static void spawnSphere(Player player, Material mat, int radius) {
		Location location = player.getLocation();
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();

		/** subtract 1 to each so it will same size as the radius */
		for (int bx = x; bx >= x - radius + 1; bx--) {
			for (int by = y; by <= y + radius - 1; by++) {
				for (int bz = z; bz <= z + radius - 1; bz++) {
					Location loc = new Location(player.getWorld(), bx, by, bz);

				
		
				}
			}
		}
	}
}
