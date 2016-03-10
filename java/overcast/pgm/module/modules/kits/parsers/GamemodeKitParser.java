package overcast.pgm.module.modules.kits.parsers;

import org.bukkit.GameMode;
import org.w3c.dom.Element;

import overcast.pgm.module.modules.kits.KitParser;
import overcast.pgm.util.XMLUtils;

public class GamemodeKitParser extends KitParser {

	private GameMode mode;

	public GamemodeKitParser(Element element) {
		super(element);
		
		this.mode = element == null ? GameMode.SURVIVAL : XMLUtils.parseGameMode(element.getTextContent());
	} 
	
	public GameMode getGameMode(){
		return this.mode;
	}

}
