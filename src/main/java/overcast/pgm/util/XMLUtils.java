package overcast.pgm.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import overcast.pgm.module.modules.kits.ArmorType;
import overcast.pgm.module.modules.projectiles.custom.ActionType;
import overcast.pgm.xml.InvalidXMLException;
import overcast.pgm.xml.XMLParseException;

public class XMLUtils {

	public static boolean parseBoolean(String value) {
		switch (value) {
		case "on":
		case "yes":
		case "true":
			return true;

		case "off":
		case "no":
		case "false":
			return false;

		default:
			return false;
		}
	}

	public static boolean parseBoolean(String text, Boolean def) {
		if (text.equalsIgnoreCase("on") || text.equalsIgnoreCase("yes") || text.equalsIgnoreCase("true")) {
			return true;
		}

		if (text.equalsIgnoreCase("off") || text.equalsIgnoreCase("no") || text.equalsIgnoreCase("false")) {
			return false;
		}
		return text == null ? def : parseBoolean(text);
	}

	public static Boolean parseBoolean(Attr attr, Boolean def) throws XMLParseException {
		return attr == null ? def : parseBoolean(attr.getValue(), def);
	}

	public static ChatColor parseChatColor(String color) {
		for (ChatColor colors : ChatColor.values()) {
			if (colors.name().replace("_", " ").toLowerCase().equals(color)) {
				return colors;
			}
		}
		return null;
	}

	public static DamageCause getCause(String text) {
		for (DamageCause causes : DamageCause.values()) {
			if (causes.name().replace("_", " ").toLowerCase().equals(text)) {
				return causes;
			}
		}
		return null;
	}

	public static Vector parseVector(Attr attr, String value) throws InvalidXMLException {
		String[] components = value.trim().split("\\s*,\\s*");
		if (components.length != 3) {
			throw new InvalidXMLException("Invalid vector format", attr);
		}
		try {
			return new Vector(NumberUtils.parseDouble(components[0]), NumberUtils.parseDouble(components[1]),
					NumberUtils.parseDouble(components[2]));
		} catch (NumberFormatException e) {
			throw new InvalidXMLException("Invalid vector format", attr, e);
		}
	}

	public static Vector parseVector(Attr attr) throws InvalidXMLException {
		return parseVector(attr, attr.getValue());
	}

	public static List<Element> getChildElements(Element parent) {
		List<Element> elements = new ArrayList<>();
		NodeList lists = parent.getChildNodes();
		for (int i = 0; i < lists.getLength(); i++) {
			Node node = lists.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				elements.add(element);
			}
		}

		return elements;
	}

	public static Element getChildElement(Element parent, String tag) {
		NodeList lists = parent.getChildNodes();
		for (int i = 0; i < lists.getLength(); i++) {
			Node node = lists.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				if (element.getTagName().equals(tag)) {
					return element;
				}
			}
		}
		return null;
	}

	public static Element getUniqueChild(Element parent, String text) {
		List<Element> children = getChildElements(parent);

		for (Element child : children) {
			if (child.getTagName().equals(text)) {
				return child;
			}
		}
		return null;
	}

	public static List<Element> getUniqueChildren(Element parent, String text) {
		List<Element> children = new ArrayList<>();
		if (parent != null) {

			Element child = getChildElement(parent, text);

			for (Element element : getChildElements(parent)) {
				if (element != null) {
					if (element.getTagName().equals(child.getTagName())) {
						children.add(element);
					}
				}
			}
		}

		return children;
	}

	public static List<Element> getElements(Element parent, String text) {
		List<Element> matching = new ArrayList<>();
		Element element = getUniqueChild(parent, text);
		matching.add(element);
		return matching;
	}

	public static ItemStack parseItem(String textContent, int amount, short damage) {
		Material mat = parseMaterial(textContent);

		ItemStack stack = new ItemStack(mat, amount, damage);
		return stack;
	}

	public static Material parseMaterial(String input) {
		for (Material mat : Material.values()) {
			if (mat.name().equalsIgnoreCase(input)) {
				return mat;
			}
		}
		return Material.getMaterial(input.toUpperCase().replaceAll(" ", "_"));
	}

	public static Color parseColor(String color) {
		return Color.fromRGB(Integer.valueOf(color.substring(0, 2), 16), Integer.valueOf(color.substring(2, 4), 16),
				Integer.valueOf(color.substring(4, 6), 16));
	}

	public static ArmorType parseArmorType(String tagName) {
		for (ArmorType type : ArmorType.values()) {
			if (type.toLowerCase().equals(tagName)) {
				return type;
			}
		}
		return null;
	}

	public static Enchantment parseBukkitEnchantment(String input) {
		for (Enchantment enchantment : Enchantment.values()) {
			if (enchantment.getName().replace("_", " ").equalsIgnoreCase(input)) {
				return enchantment;
			}
		}

		return null;
	}

	public static Enchantment parseMinecraftEnchantment(String input) {
		switch (input) {
		case "feather falling":
			return Enchantment.PROTECTION_FALL;
		default:
			return null;
		}
	}

	public static Enchantment parseEnchantment(String input) {
		Enchantment bukkit = parseBukkitEnchantment(input);
		return bukkit != null ? bukkit : parseMinecraftEnchantment(input);
	}

	public static PotionEffectType parsePotionEffect(String textContent) {
		return PotionEffectType.getByName(StringUtils.getTechnicalName(textContent));
	}

	public static Vector parseVector(Element element) {
		String[] split = element.getTextContent().split(",");
		if (split.length != 3) {
			try {
				throw new InvalidXMLException("Invalid vector format", element);
			} catch (InvalidXMLException e) {
				e.printStackTrace();
			}
		}
		try {
			return new Vector(NumberUtils.parseDouble(split[0]), NumberUtils.parseDouble(split[1]),
					NumberUtils.parseDouble(split[2]));
		} catch (NumberFormatException e) {
			try {
				throw new InvalidXMLException("Invalid vector format", element, e);
			} catch (InvalidXMLException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	public static GameMode parseGameMode(String input) {
		for (GameMode mode : GameMode.values()) {
			if (mode.name().equalsIgnoreCase(input)) {
				return mode;
			}
		}
		return null;
	}

	public static DyeColor parseDyeColor(String text) {
		String name = text.replace(" ", "_").toUpperCase();
		return DyeColor.valueOf(name);
	}

	public static Environment parseDimension(Element element) {
		String text = element.getTextContent();
		for (Environment dimension : Environment.values()) {
			String name = StringUtils.getName(dimension.name());
			if (text.equalsIgnoreCase(name)) {
				return dimension;
			}
		}
		return null;
	}

	public static List<Action> parseAction(String attribute) {
		List<Action> acts = new ArrayList<>();

		if (attribute != null) {
			switch (attribute) {
			case "right":
				acts.add(Action.RIGHT_CLICK_AIR);
				acts.add(Action.RIGHT_CLICK_BLOCK);
				break;
			case "left":
				acts.add(Action.LEFT_CLICK_AIR);
				acts.add(Action.LEFT_CLICK_BLOCK);
				break;
			case "both":
				acts.add(Action.RIGHT_CLICK_AIR);
				acts.add(Action.RIGHT_CLICK_BLOCK);
				acts.add(Action.LEFT_CLICK_AIR);
				acts.add(Action.LEFT_CLICK_BLOCK);
			}
		}
		return acts;
	}

	public static ActionType parseActionType(String attribute) {
		for (ActionType actType : ActionType.values()) {
			if (actType.name().toLowerCase().equalsIgnoreCase(attribute)) {
				return actType;
			}
		}

		return null;
	}
}