package overcast.pgm.module.modules.filter.types;

import org.bukkit.Material;
import org.w3c.dom.Element;

import overcast.pgm.util.XMLUtils;

public class MaterialFilterParser {

	private Material mat;

	public MaterialFilterParser(Element element){
	     this.mat = XMLUtils.parseMaterial(element.getTextContent());
	}
	
	
	public Material getMaterial(){
		return this.mat;
	}
}
