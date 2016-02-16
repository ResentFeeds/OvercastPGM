package overcast.pgm.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import overcast.pgm.OvercastPGM;

public abstract class OvercastConfig implements OvercastConfigLoader {

	String name; /** name of the file */ 
	
	File file; /** the file that loads the configuration */
	
	YamlConfiguration configuration; /** configuration */
	
	/** constructor */
	
	public OvercastConfig(String name){
		this.name = name;
		this.file = new File(OvercastPGM.getInstance().getDataFolder(), this.name);
		this.configuration = YamlConfiguration.loadConfiguration(this.file);
		
		load();
	}
	
	/** save the configuration */
	
	public void save() throws IOException{
		this.configuration.save(this.file);
	}
	
	
	public YamlConfiguration getConfiguration(){
		return this.configuration;
	}
	
	
	
	public boolean hasConfigurationSection(ConfigurationSection section){
	   return section != null ? true : false;
	}
	
	public boolean hasString(ConfigurationSection section, String child){
		return section.getString(child) != null ? true : false;
	}
}
