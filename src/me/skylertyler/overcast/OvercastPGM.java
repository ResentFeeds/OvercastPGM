package me.skylertyler.overcast;

import org.bukkit.plugin.java.JavaPlugin;

public class OvercastPGM extends JavaPlugin {

	public static OvercastPGM instance;

	public void onEnable() {
		instance = this;
	}
	
	
	public void onDisable(){
		instance = null;
	}
	
	public static OvercastPGM getInstance() {
		if (instance == null)
			return null;
		return instance;
	}
	
	
	

}
