package overcast.pgm.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
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
	
	
	
	public static EntityType getEntity(String name){
		switch(name){
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
		for(Material allow : allowed){
			if(type.equals(allow)){
				return true;
			}
		} 
		return false;
	} 
	
	
	
	public static ChatColor convertWoolNameToChatColor(String woolName){
		if (woolName.equalsIgnoreCase("lime wool")){
			return ChatColor.GREEN;
		}
		else if (woolName.equalsIgnoreCase("purple wool")) {
			return ChatColor.DARK_PURPLE;
		}
		else if (woolName.equalsIgnoreCase("yellow wool")) {
			return ChatColor.YELLOW;
		}
		else if (woolName.equalsIgnoreCase("orange wool")) {
			return ChatColor.YELLOW;
		}
		else if (woolName.equalsIgnoreCase("magenta wool")) {
			return ChatColor.LIGHT_PURPLE;
		}
		else if (woolName.equalsIgnoreCase("light blue wool")) {
			return ChatColor.AQUA;
		}
		else if (woolName.equalsIgnoreCase("pink wool")) {
			return ChatColor.LIGHT_PURPLE;
		}
		else if (woolName.equalsIgnoreCase("black wool")) {
			return ChatColor.BLACK;
		}
		else if (woolName.equalsIgnoreCase("blue wool")) {
			return ChatColor.BLUE;
		}
		else if (woolName.equalsIgnoreCase("white wool") || woolName.equalsIgnoreCase("wool")) {
			return ChatColor.WHITE;
		}
		else if (woolName.equalsIgnoreCase("gray wool")) {
			return ChatColor.GRAY;
		}
		else if (woolName.equalsIgnoreCase("brown wool")) {
			return ChatColor.GRAY;
		}
		else if (woolName.equalsIgnoreCase("red wool")) {
			return ChatColor.RED;
		}
		else{
			return ChatColor.GRAY;
		}
	}
}