package overcast.pgm.module.modules.kits.parsers;

import org.w3c.dom.Element;

import overcast.pgm.module.modules.kits.KitParser;

public class HealthKitParser extends KitParser {

    int half;

	public HealthKitParser(Element element) {
		super(element);
		this.half = Integer.parseInt(element.getTextContent());
	}   
	
	public int getHalf(){
		return this.half;
	}
}
