package overcast.pgm.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.util.ChatPaginator;

import com.google.common.base.Strings;

public class BukkitUtils {

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
		return ChatColor.translateAlternateColorCodes(character,
				ChatColor.translateAlternateColorCodes(character, s));
	}

	public static List<String> colorizeList(List<String> list) {
		List<String> result = new ArrayList<String>();

		for (String line : list) {
			result.add(colorize(line));
		}

		return result;
	}

	public static String dashedChatMessage(String message, String c,
			ChatColor color) {
		return dashedChatMessage(message, c, color.toString());
	}

	public static String dashedChatMessage(String message, String c,
			String dashFormatting) {
		message = " " + message + " ";
		String dashes = Strings.repeat(c,
				(ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH
						- ChatColor.stripColor(message).length() - 2) / 2);
		return dashFormatting + dashes + message + dashFormatting + dashes;
	}

	public static String dashedChatMessage(String message, String c,
			ChatColor dashColor, ChatColor messageColor) {
		message = " " + message + " ";
		String dashes = Strings.repeat(c,
				(ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH
						- ChatColor.stripColor(message).length() - 2)
						/ (c.length() * 2));
		return dashColor + dashes + ChatColor.RESET + messageColor + message
				+ ChatColor.RESET + dashColor + dashes;
	}

}