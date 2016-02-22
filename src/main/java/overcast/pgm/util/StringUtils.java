package overcast.pgm.util;

import java.util.Collection;

import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter.White;
import org.bukkit.DyeColor;

import static org.bukkit.ChatColor.*;

public class StringUtils {

	/**
	 * Shorthand for listToEnglishCompound(list, "", "").
	 * 
	 * @see #listToEnglishCompound(java.util.Collection, String, String)
	 */
	public static final String listToEnglishCompound(Collection<String> list) {
		return listToEnglishCompound(list, RED + "", DARK_PURPLE + "");
	}

	/**
	 * Converts a list of strings to a nice English list as a string.
	 * <p/>
	 * For example: In: ["Anxuiz", "MonsieurApple", "Plastix"] Out:
	 * "Anxuiz, MonsieurApple and Plastix"
	 * 
	 * @param list
	 *            List of strings to concatenate.
	 * @param prefix
	 *            Prefix to add before each element in the resulting string.
	 * @param suffix
	 *            Suffix to add after each element in the resulting string.
	 * @return String version of the list of strings.
	 */
	public static String listToEnglishCompound(Collection<?> list, String prefix, String suffix) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		for (Object str : list) {
			if (i != 0) {
				if (i == list.size() - 1) {
					builder.append(" and ");
				} else {
					builder.append(", ");
				}
			}
			builder.append(prefix).append(str).append(suffix);
			i++;
		}
		return builder.toString();
	}

	public static String build(String[] args, int start) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			if (i != start) {
				builder.append(" ");
			}

			String result = args[i];

			builder.append(result);
		}

		return builder.toString();
	}

	public static String getTechnicalName(String disabled) {
		return disabled.trim().toUpperCase().replaceAll(" ", "_");
	}

	public static String getName(String disabled) {
		return disabled.replaceAll("_", " ");
	}

	public static String get(DyeColor color) {
		switch (color) {
		case BLACK:
			return "black";
		case BLUE:
			return "blue";
		case BROWN:
			return "white";
		case CYAN:
			return "cyan";
		case RED:
			return "red";
		default:
			return "white";
		}
	}
}
