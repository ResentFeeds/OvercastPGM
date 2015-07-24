package overcast.pgm;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import overcast.pgm.config.Config;
import overcast.pgm.map.Map;
import overcast.pgm.map.MapLoader;
import overcast.pgm.match.Match;
import overcast.pgm.util.Log;

//TODO add ModuleFactory adding removing clearing etc.
// add InitModule, 
public class OvercastPGM extends JavaPlugin {

	public static OvercastPGM instance;

	public void onEnable() {
		instance = this;
		try {
			new Config(getDataFolder(), "config.yml", true);
		} catch (IOException e) {
			e.printStackTrace();
			Log.warning("cant create config file due to a IOException");
		}

		// File parent = new File("Rotation");
		//
		// if(!parent.isDirectory() || parent.isFile() || parent.isHidden()){
		// parent.mkdir();
		// }
		File rot = new File("rotation");
		
		try {
			new MapLoader(rot);
		} catch (Exception e) {
			e.printStackTrace();
		}

		new Match(1, new Map(new File(rot, "Copy")));

	}

	public void onDisable() {
		instance = null;
	}

	public void addCommand(String name, CommandExecutor command) {
		this.getCommand(name).setExecutor(command);
	}

	public void loadCommands() {
	}

	public static OvercastPGM getInstance() {
		return instance;
	}

}
