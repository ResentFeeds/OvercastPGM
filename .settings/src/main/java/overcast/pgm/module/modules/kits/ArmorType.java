package overcast.pgm.module.modules.kits;

public enum ArmorType {

	HELMET,
	CHESTPLATE,
	LEGGINGS,
	BOOTS;
	
	public String toLowerCase(){
		return name().toLowerCase();
	}
}
