package overcast.pgm.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

import org.bukkit.Skin;
import org.bukkit.craftbukkit.v1_8_R1.util.Skins;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mojang.authlib.properties.PropertyMap;

import overcast.pgm.player.OvercastPlayer;

public class MojangUtils {

	public static UUID getUUID(String attr) {
		return UUID.fromString(attr);
	}

	public static String getNameByUUID(UUID uuid) {
		try {
			JSONObject result = (JSONObject) new JSONParser().parse(new InputStreamReader(new URL(
					"https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", ""))
							.openStream()));
			return (String) result.get("name");
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getSkin(UUID uuid) {
		try {
			JSONObject result = (JSONObject) new JSONParser().parse(new InputStreamReader(new URL(
					"https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", ""))
							.openStream()));
			return (String) result.get("value");
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Skin getSkin(String data, String name) {
		Skin skin = new Skin(data, name);
		return skin;
	}

	public static Skin addSkin(Player player) { 
		String data = getSkin(player.getUniqueId());

		if (data == null)
			return null;

		String name = player.getName();

		if (name == null)
			return null;

		Skin skin = getSkin(data, name);

		return skin;

	}
}
