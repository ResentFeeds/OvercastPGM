package overcast.pgm.util;

import org.bukkit.entity.Player;
import static org.bukkit.ChatColor.*;
public class MessageUtils {
	
	public static void warningMessage(Player player, String message) {
		player.sendMessage(GOLD + Characters.ERROR.getUTF() + message);
 } 
}
