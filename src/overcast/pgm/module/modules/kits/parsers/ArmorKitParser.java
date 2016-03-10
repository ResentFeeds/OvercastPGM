package overcast.pgm.module.modules.kits.parsers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.w3c.dom.Element;

import overcast.pgm.module.modules.kits.KitParser;
import overcast.pgm.util.BukkitUtils;
import overcast.pgm.util.NumberUtils;
import overcast.pgm.util.XMLUtils;
import overcast.pgm.xml.XMLParseException;

public class ArmorKitParser extends KitParser {

	ItemStack stack;
	
	Material[] colorables = new Material[] { Material.LEATHER_BOOTS,
			Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS,
			Material.LEATHER_HELMET };
	
	Material[] allowed = new Material[] { Material.LEATHER_BOOTS,
			Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS,
			Material.LEATHER_HELMET, Material.CHAINMAIL_BOOTS,
			Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_HELMET,
			Material.CHAINMAIL_LEGGINGS, Material.IRON_HELMET,
			Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS,
			Material.IRON_BOOTS, Material.GOLD_HELMET,
			Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS,
			Material.GOLD_BOOTS, Material.DIAMOND_HELMET,
			Material.DIAMOND_LEGGINGS, Material.DIAMOND_CHESTPLATE,
			Material.DIAMOND_BOOTS };
	
	ItemMeta meta;

	boolean locked;

	private Map<Enchantment, Integer> enchantments;
 
	public ArmorKitParser(Element child)  {
		super(child); 
		this.enchantments = new HashMap<>();
		short damage = child.hasAttribute("damage") ? NumberUtils.parseShort(child.getAttribute("damage")) : 0;
		int amount = child.hasAttribute("amount") ? NumberUtils.parseInteger(child.getAttribute("amount")) : 1;
		
		this.locked = child.hasAttribute("locked") ? XMLUtils.parseBoolean(child.getAttribute("locked")) : false;
		
		this.stack = XMLUtils.parseItem(child.getAttribute("material"), amount, damage);
		
		List<Element> children = XMLUtils.getChildElements(child);

		for (Element c : children) {
			if (c != null) {
				switch (c.getTagName()) {
				case "enchantment":
					int level = c.hasAttribute("level") ? NumberUtils.parseInteger(c.getAttribute("level")) : 1;
					this.enchantments.put(XMLUtils.parseEnchantment(c.getTextContent()), level);
					break;
				}
			}
		}
		
		this.meta = this.stack.getItemMeta(); 
		
		String name = child.hasAttribute("name") ? BukkitUtils.colorize(child.getAttribute("name")) : null;
		String lore = child.hasAttribute("lore") ? child.getAttribute("lore") : null; 
		List<String> newLore = ItemKitParser.parseLore(lore);
		List<String> colored = BukkitUtils.colorizeList(newLore); 
		
		
		if(BukkitUtils.isAllowed(this.stack.getType(), this.allowed)){
		  if(isColorable(this.stack.getType())){
		    if(this.meta instanceof LeatherArmorMeta){
		    	LeatherArmorMeta armor = (LeatherArmorMeta) this.meta;
		    	String colors = child.hasAttribute("color") ? child.getAttribute("color") : null;
		        if(colors != null){
		         	Color color = XMLUtils.parseColor(colors); 
		    	    armor.setColor(color); 
		        }
		    }
		  }
		}else{
			try {
				throw new XMLParseException("<" + child.getTagName() + "> has a invalid material");
			} catch (XMLParseException e) { 
				e.printStackTrace();
			}
		}
		
		// add enchantments if there are any!
		if(!this.enchantments.isEmpty()){
			for(Entry<Enchantment, Integer> entry : this.enchantments.entrySet()){
				this.meta.addEnchant(entry.getKey(), entry.getValue(), true);
			}
		}

		this.meta.setLore(colored);
		this.meta.setDisplayName(name);
		this.stack.setItemMeta(this.meta);
	}

	private boolean isColorable(Material type) {
		for(Material colorable : this.colorables){
			 if(type.equals(colorable)){
				 return true;
			 }
		} 
		return false;
	}
	 

	public ItemStack getItemStack() {
		return this.stack;
	}

	public boolean getLocked() {
		return this.locked;
	}
}