package overcast.pgm.parser;

import org.bukkit.Material;
import org.w3c.dom.Attr;

import com.google.common.collect.Sets;

public class MaterialParser extends AttributeParser implements Parser<Material, String> {
	
	
	private ParserResult<Material> parsers;
	
	private Material material;

	public MaterialParser(Attr attribute) {
		super(attribute); 
		
		this.parsers = parse(attribute.getNodeValue());
		
		for(Material result : parsers.getEntries()){
			 this.material = result;
		}
	}

	@Override
	public ParserResult<Material> parse(String input) {
		for (Material materials : Material.values()) {
			if (materials.name().replace('_', ' ').equals(input)) {
				return new ParserResult<Material>(Sets.newHashSet(materials));
			}
		}
		return null;
	}

	
	public Material getMaterial(){
		return this.material;
	}
}
