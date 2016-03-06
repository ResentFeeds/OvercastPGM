package overcast.pgm.util;

import java.util.List;

import overcast.pgm.match.MatchHandler;
import overcast.pgm.module.modules.kits.KitModule;

public class KitUtils {
	
	public static List<KitModule> getKits() {
		List<KitModule> kitModules = MatchHandler.getMatchHandler().getMatch().getModules().getModules(KitModule.class);
		return kitModules;
	}

	public static KitModule getKit(String id) {
		for (KitModule kitModule : getKits()) {
			if (kitModule != null) {
				if (kitModule.getID().equals(id)) {
					return kitModule;
				}
			}
		}
		return null;
	}

}
