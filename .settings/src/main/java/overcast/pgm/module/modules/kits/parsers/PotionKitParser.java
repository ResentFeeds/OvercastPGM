package overcast.pgm.module.modules.kits.parsers;

import org.bukkit.potion.PotionEffectType;
import org.w3c.dom.Element;

import overcast.pgm.module.modules.kits.KitParser;
import overcast.pgm.util.NumberUtils;
import overcast.pgm.util.XMLUtils;

public class PotionKitParser extends KitParser {

	int duration;
	int amplifier;
	PotionEffectType effect;

	public PotionKitParser(Element element) {
		super(element); 
		this.duration = element.hasAttribute("duration") ? NumberUtils.parseInteger(element.getAttribute("duration")) : (int) Double.POSITIVE_INFINITY;
		this.amplifier = element.hasAttribute("amplifier") ? NumberUtils.parseInteger(element.getAttribute("amplifier")) : 1;
		this.effect = XMLUtils.parsePotionEffect(element.getTextContent());
	} 
	
	public int getDuration(){
		return this.duration;
	}
	
	
	public int getAmplifier(){
		return this.amplifier;
	} 
	
	public PotionEffectType getEffectType(){
		return this.effect;
	}
}
