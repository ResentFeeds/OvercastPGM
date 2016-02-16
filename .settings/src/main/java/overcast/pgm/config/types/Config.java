package overcast.pgm.config.types;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.inventory.ItemStack;

import overcast.pgm.channels.Channel;
import overcast.pgm.channels.ChannelFactory;
import overcast.pgm.config.OvercastConfig;
import overcast.pgm.server.ServerType;
import overcast.pgm.util.ServerUtils;

public class Config extends OvercastConfig {

	ServerType type;
	Channel def;

	Map<Integer, ItemStack> items = new HashMap<>();

	public Config(String name) {
		super(name);
	}

	@Override
	public void load() {
		YamlConfiguration configuration = this.getConfiguration();
		YamlConfigurationOptions options = configuration.options();
		options.header("a OvercastPGM configuration file");

		ConfigurationSection server = configuration.createSection("server");

		String type = "type";

		if (hasConfigurationSection(server)) {
			if (!hasString(server, type)) {
				server.set(type, "playable");
			}
		}

		ConfigurationSection channel = configuration.createSection("channel");

		if (hasConfigurationSection(channel)) {
			if (!hasString(channel, "default")) {
				channel.set("default", "Global");
			}
		}   

		options.copyHeader(true);
		try {
			save();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.type = ServerUtils.getType(server.getString("type"));
		this.def = ChannelFactory.getChannel(channel.getString("default"));
	}

	public boolean isDevelopment() {
		return this.type == ServerType.DEVELOPMENT;
	}

	public boolean isPlayable() {
		return this.type == ServerType.PLAYABLE;
	}

	public Channel getDefaultChannel() {
		return this.def;
	} 
	
	
	public Map<Integer, ItemStack> getItems(){
		return this.items;
	}
}
