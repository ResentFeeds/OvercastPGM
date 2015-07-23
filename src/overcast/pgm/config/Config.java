package overcast.pgm.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	private YamlConfiguration configuration;

	String prefix;

	private File config;

	private boolean copy;

	public Config(File dataFolder, String string, boolean copy) throws IOException {
		this.copy = copy;
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}

		this.config = new File(dataFolder, string);

		if (!(this.config.exists())){
			this.config.createNewFile();
		}
		
		this.configuration = YamlConfiguration.loadConfiguration(this.config);
		
		
		ConfigurationSection broadcast_section = this.configuration.createSection("broadcast");
		
		
		this.configuration.save(this.config);
	}
	
	public boolean isCopy(){
		return this.copy;
	}

	public YamlConfiguration getConfiguration() {
		return this.configuration;
	}
	
	
	public File getConfig(){
		return this.config;
	}
}
