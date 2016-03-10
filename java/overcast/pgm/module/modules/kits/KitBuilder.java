package overcast.pgm.module.modules.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import overcast.pgm.builder.Builder;
import overcast.pgm.builder.BuilderInfo;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.ModuleStage;
import overcast.pgm.module.modules.kits.parsers.ArmorKitParser;
import overcast.pgm.module.modules.kits.parsers.BookKitParser;
import overcast.pgm.module.modules.kits.parsers.GamemodeKitParser;
import overcast.pgm.module.modules.kits.parsers.HealthKitParser;
import overcast.pgm.module.modules.kits.parsers.ItemKitParser;
import overcast.pgm.module.modules.kits.parsers.PotionKitParser;
import overcast.pgm.util.KitUtils;
import overcast.pgm.util.NumberUtils;
import overcast.pgm.util.XMLUtils;
import overcast.pgm.xml.XMLParseException;

@BuilderInfo(stage = ModuleStage.START)
public class KitBuilder extends Builder {

	@Override
	public ModuleCollection<Module> build(Document doc) throws XMLParseException {
		Element root = doc.getDocumentElement();

		Node kitsTag = root.getElementsByTagName("kits").item(0);

		/**
		 * if <kits> tag isn't there it wouldn't work.
		 */
		return kitsTag == null ? null : parseKits(kitsTag, "kit");
	}

	private ModuleCollection<Module> parseKits(Node kitsTag, String string) {
		ModuleCollection<Module> modules = new ModuleCollection<>();
		if (kitsTag.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) kitsTag;

			List<Element> children = XMLUtils.getChildElements(element);

			for (Element child : children) {
				if (child != null) {
					if (child.getTagName().equals(string)) {
						modules.add(parseKit(child));
					}
				}
			}
		}
		return modules;
	}

	public static KitModule parseKit(Element child) {
		List<Element> children = XMLUtils.getChildElements(child);
		List<ItemKit> items = new ArrayList<>();
		List<ArmorKit> armor = new ArrayList<>();
		List<BookKit> books = new ArrayList<>();
		List<PotionKit> potions = new ArrayList<>();
		boolean force = XMLUtils.parseBoolean(child.getAttribute("force"));
		String id = child.hasAttribute("id") ? child.getAttribute("id") : null;
		List<String> parents = child.hasAttribute("parents") ? parseParents(child.getAttribute("parents")) : null;
		HealthKit health = null;
		GamemodeKit gamemode = null;
		float walkspeed = 0.2f ;
		float saturation = 0;
		int foodlevel = 20;
		boolean clearItems = false;
		boolean clear = false;
		for (Element c : children) {
			switch (c.getTagName()) {
			case "item":
				items.add(new ItemKit(new ItemKitParser(c)));
				break;
			case "book":
				books.add(new BookKit(new BookKitParser(c)));
				break;
			case "helmet":
				armor.add(new ArmorKit(ArmorType.HELMET, new ArmorKitParser(c)));
				break;
			case "chestplate":
				armor.add(new ArmorKit(ArmorType.CHESTPLATE, new ArmorKitParser(c)));
				break;
			case "leggings":
				armor.add(new ArmorKit(ArmorType.LEGGINGS, new ArmorKitParser(c)));
				break;
			case "boots":
				armor.add(new ArmorKit(ArmorType.BOOTS, new ArmorKitParser(c)));
			case "clear":
				clear = true;
			case "clear-items":
				clearItems = true;
				break;
			case "potion":
				potions.add(new PotionKit(new PotionKitParser(c)));
				break;
			case "health":
				health = new HealthKit(new HealthKitParser(c));
				break;
			case "saturation":
				saturation = NumberUtils.parseFloat(c.getTextContent());
			case "foodlevel":
				foodlevel = NumberUtils.parseInteger(c.getTextContent());
				break;
			case "game-mode":
				gamemode = new GamemodeKit(new GamemodeKitParser(c));
				break;
			case "walk-speed": 
				walkspeed = NumberUtils.parseFloat(c.getTextContent());
				break;
			}
		}
		HungerKit hunger = new HungerKit(saturation, foodlevel);
		return new KitModule(id, force, clear, clearItems, parents, items, armor, potions, books, health, hunger,
				gamemode != null ? gamemode : new GamemodeKit(GameMode.SURVIVAL), walkspeed);
	}

	private static List<String> parseParents(String parent) {
		List<String> parents = new ArrayList<>();

		if (parent.contains(",")) {
			List<String> list = ImmutableList.copyOf(Splitter.on(',').split(parent));

			for (String p : list) {
				if (p != null) {
					parents.add(p);
				}
			}
		} else {
			parents.add(parent);
		}

		return parents;
	}

	public static KitModule getKit(String parent) {
		return KitUtils.getKit(parent);
	}
}
