package overcast.pgm.module.modules.kits;

import org.w3c.dom.Attr;

import overcast.pgm.parser.AttributeParser;
import overcast.pgm.util.KitUtils;

public class KitReference extends AttributeParser {

	 KitModule kit;

	public KitReference(Attr attribute) {
		super(attribute);  
		this.kit = KitUtils.getKit(attribute.getNodeValue());
	} 
	
	public KitModule getKit(){
		return this.kit;
	}
}
