package overcast.pgm.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.WitherSkull;

import overcast.pgm.module.modules.objective.wool.WoolObjective;

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

	public static boolean hasLessCharacters(Object obj, int length) {
		if (obj instanceof String) {
			String string = (String) obj;

			if (length != 0) {
				if (string.length() <= length) {
					return true;
				}
			}
		}
		return false;
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

	public static Class<? extends Projectile> classForString(EntityType type) {
		HashMap<EntityType, Class<? extends Projectile>> values = new HashMap<>();

		values.put(EntityType.SNOWBALL, Snowball.class);
		values.put(EntityType.ENDER_PEARL, EnderPearl.class);
		values.put(EntityType.SMALL_FIREBALL, SmallFireball.class);
		values.put(EntityType.FIREBALL, Fireball.class);
		values.put(EntityType.EGG, Egg.class);
		values.put(EntityType.WITHER_SKULL, WitherSkull.class);
		values.put(EntityType.PRIMED_TNT, null);
		for (Entry<EntityType, Class<? extends Projectile>> value : values.entrySet()) {
			if (value.getKey().equals(type)) {
				return value.getValue();
			}
		}

		return Arrow.class;
	}

	public static String getTechnicalName(String disabled) {
		return disabled.trim().toUpperCase().replaceAll(" ", "_");
	}

	public static String getName(String disabled) {
		return disabled.replaceAll("_", " ");
	}

	public static String getPrefix(DyeColor color) {
		if (color != null) {
			switch (color) {
			case RED:
				return "red";
			case BLACK:
				return "black";
			case BLUE:
				return "blue";
			case BROWN:
				return "brown";
			case CYAN:
				return "cyan";
			case GRAY:
				return "gray";
			case GREEN:
				return "green";
			case LIGHT_BLUE:
				return "light blue";
			case LIME:
				return "lime";
			case MAGENTA:
				return "Magenta";
			case ORANGE:
				return "orange";
			case PINK:
				return "pink";
			case PURPLE:
				return "purple";
			case SILVER:
				return "silver";
			case WHITE:
				return "white";
			case YELLOW:
				return "yellow";
			}
		}
		return null;
	}

	public static List<String> to(Collection<WoolObjective> value) {
		List<String> strings = new ArrayList<>();
		if (value.size() != 0) {
			for (WoolObjective wool : value) {
				strings.add((wool.isCompleted() ? ChatColor.GREEN : ChatColor.RED) + wool.getWoolName());
			}
		}
		return strings;
	}
}
