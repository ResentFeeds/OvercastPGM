package overcast.pgm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.ChatPaginator;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

import overcast.pgm.OvercastPGM;
import overcast.pgm.player.OvercastPlayer;

public class BukkitUtils {

	private static final Map<DyeColor, ChatColor> DYE_CHAT_MAP = ImmutableMap.<DyeColor, ChatColor> builder()
			.put(DyeColor.BLACK, ChatColor.BLACK).put(DyeColor.BLUE, ChatColor.DARK_BLUE)
			.put(DyeColor.GREEN, ChatColor.DARK_GREEN).put(DyeColor.CYAN, ChatColor.DARK_AQUA)
			.put(DyeColor.RED, ChatColor.RED).put(DyeColor.PURPLE, ChatColor.DARK_PURPLE)
			.put(DyeColor.ORANGE, ChatColor.GOLD).put(DyeColor.SILVER, ChatColor.GRAY)
			.put(DyeColor.GRAY, ChatColor.DARK_GRAY).put(DyeColor.LIGHT_BLUE, ChatColor.BLUE)
			.put(DyeColor.LIME, ChatColor.GREEN).put(DyeColor.BROWN, ChatColor.DARK_RED)
			.put(DyeColor.MAGENTA, ChatColor.LIGHT_PURPLE).put(DyeColor.YELLOW, ChatColor.YELLOW)
			.put(DyeColor.WHITE, ChatColor.WHITE).put(DyeColor.PINK, ChatColor.LIGHT_PURPLE).build();

	public static ChatColor convertDyeColorToChatColor(DyeColor color) {
		for (Entry<DyeColor, ChatColor> colors : DYE_CHAT_MAP.entrySet()) {
			if (colors.getKey().equals(color)) {
				return colors.getValue();
			}
		}

		return null;
	}

	public static void refreshInventory(final Inventory current) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(OvercastPGM.getInstance(), new Runnable() {
			public void run() {
				for (OvercastPlayer online : OvercastPlayer.getPlayers()) {
					if (online.getOpenInventory().getTitle().equals(current.getTitle())) {
						online.updateInventory();
					}
				}
			}
		}, 0, 20);
	}

	/** Makes strings have pretty colors */
	public static String colorize(String s) {
		if (s != null) {
			if (s.contains("`")) {
				return color(s, '`');
			} else {
				if (s.contains("§")) {
					return color(s, '§');
				}
			}
		}
		return s;
	}

	public static String color(String s, char character) {
		return ChatColor.translateAlternateColorCodes(character, ChatColor.translateAlternateColorCodes(character, s));
	}

	public static List<String> colorizeList(List<String> list) {
		List<String> result = new ArrayList<String>();

		for (String line : list) {
			result.add(colorize(line));
		}

		return result;
	}

	public static String dashedChatMessage(String message, String c, ChatColor color) {
		return dashedChatMessage(message, c, color.toString());
	}

	public static String dashedChatMessage(String message, String c, String dashFormatting) {
		message = " " + message + " ";
		String dashes = Strings.repeat(c,
				(ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH - ChatColor.stripColor(message).length() - 2) / 2);
		return dashFormatting + dashes + message + dashFormatting + dashes;
	}

	public static String dashedChatMessage(String message, String c, ChatColor dashColor, ChatColor messageColor) {
		message = " " + message + " ";
		String dashes = Strings.repeat(c,
				(ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH - ChatColor.stripColor(message).length() - 2)
						/ (c.length() * 2));
		return dashColor + dashes + ChatColor.RESET + messageColor + message + ChatColor.RESET + dashColor + dashes;
	}

	public static EntityType getEntity(String name) {
		switch (name) {
		case "zombie":
			return EntityType.ZOMBIE;
		case "giant":
			return EntityType.GIANT;
		case "creeper":
			return EntityType.CREEPER;
		case "armorstand":
			return EntityType.ARMOR_STAND;
		case "endercrystal":
			return EntityType.ENDER_CRYSTAL;
		case "endermite":
			return EntityType.ENDERMITE;
		case "guardian":
			return EntityType.GUARDIAN;
		}
		return null;
	}

	public static boolean isAllowed(Material type, Material... allowed) {
		for (Material allow : allowed) {
			if (type.equals(allow)) {
				return true;
			}
		}
		return false;
	}

	public static DamageCause getDamageType(String key) {
		for (DamageCause cause : DamageCause.values()) {
			if (key != null && StringUtils.getName(cause.name()).equals(key)) {
				return cause;
			}
		}

		return null;
	}
}