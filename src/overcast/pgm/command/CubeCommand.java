package overcast.pgm.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import overcast.pgm.OvercastPGM;
import overcast.pgm.match.Match;
import overcast.pgm.module.modules.filter.Filter;
import overcast.pgm.module.modules.filter.FilterContext;
import overcast.pgm.module.modules.filter.FilterModule;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.BukkitUtils;
import overcast.pgm.util.MessageUtils;
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

	@Command(aliases = "inventory", desc = "view a players inventory", usage = "[player]", min = 1, max = 1)
	public static void inventory(final CommandContext args, CommandSender sender) {
		if (sender instanceof Player) {
			OvercastPlayer player = OvercastPlayer.getPlayers((Player) sender);
			if (player.isObserver()) {
				OvercastPlayer who = OvercastPlayer.getPlayer(args.getJoinedStrings(0));
				if (who != null) {
					if (!who.isObserver()) {
						who.viewInventory(player);
					} else {
						// send them a message if they
						MessageUtils.warningMessage(player.getPlayer(),
								ChatColor.WHITE + "Player's inventory is not currently viewable");
					}
				} else {
					MessageUtils.warningMessage(player.getPlayer(), ChatColor.RED + "The player " + ChatColor.DARK_RED
							+ args.getJoinedStrings(0) + ChatColor.RED + " is currently not online!");
				}
			} else {
				MessageUtils.warningMessage(player.getPlayer(),
						ChatColor.WHITE + "Player's inventory is not currently viewable");
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

				List<Filter> children = filterMod.getChildren();

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

	@Command(aliases = "killall", desc = "kill all the mobs")
	public static void clearEntities(final CommandContext args, CommandSender sender) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			clearEntities(p);
		}
	}

	@Command(aliases = "spawn", desc = "spawn a entity", usage = "<entity> <amount>", min = 1, max = 2)
	public static void spawnEntity(final CommandContext args, CommandSender sender) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			int amount = 1;
			EntityType def = EntityType.ZOMBIE;

			String name = args.getString(0);
			EntityType entity = BukkitUtils.getEntity(name);

			if (args.argsLength() == 0) {
				spawnEntity(player, amount, def);
				return;
			}

			if (args.argsLength() == 1) {
				if (entity != null) {
					spawnEntity(player, amount, entity);
					return;
				}
			}

			if (args.argsLength() == 2) {
				int newAmount = args.getInteger(1);
				if (entity != null && newAmount >= 1) {
					spawnEntity(player, newAmount, entity);
				}
			}
		}
	}

	public static void clearEntities(Player player) {
		World world = player.getWorld();
		int amount = 0;
		for (Entity entity : world.getEntities()) {
			if (entity != null && entity.getType() != EntityType.PLAYER) {
				++amount;
				entity.remove();
			}
		}

		player.sendMessage(amount != 0 ? ChatColor.WHITE + "Removed " + ChatColor.RED + "" + amount + ChatColor.WHITE
				+ " " + (amount == 1 ? "entity" : "entities") : ChatColor.RED + "No entities on your world");

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

	public static void spawnEntity(Player player, int amount, EntityType type) {
		for (int i = 0; i < amount; i++) {
			player.getWorld().spawnEntity(player.getLocation(), type);
		}
	}
}
