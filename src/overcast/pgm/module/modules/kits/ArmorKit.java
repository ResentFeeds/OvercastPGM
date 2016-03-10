package overcast.pgm.module.modules.kits;

import org.bukkit.inventory.ItemStack;

import overcast.pgm.module.modules.kits.parsers.ArmorKitParser;

public class ArmorKit {

	ArmorType type;
	ItemStack stack;
	boolean locked;
	
	public ArmorKit(ArmorType type, ItemStack stack, boolean locked){
		this.type = type;
		this.stack = stack;
		this.locked = locked;
	}
	
	
	public ArmorKit(ArmorType type ,ArmorKitParser parser) {
		this(type, parser.getItemStack(), parser.getLocked());
	}


	public ArmorType getArmorType(){
		return this.type;
	}

	public ItemStack getStack(){
		return this.stack;
	}
	
	
	public boolean isLocked(){
		return this.locked;
	}
}
