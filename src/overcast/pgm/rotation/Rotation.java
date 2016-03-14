package overcast.pgm.rotation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import overcast.pgm.map.Map;
import overcast.pgm.map.MapLoader;

public class Rotation {

	private List<Map> rotation = null;

	private List<String> rotationNames;

	private File rotationFile;
	private MapLoader loader;

	int postion;

	public Rotation(File rotationFile, MapLoader loader) {
		this.rotationFile = rotationFile;
		this.loader = loader;
		this.rotationNames = new ArrayList<>();

		load(YamlConfiguration.loadConfiguration(this.rotationFile));

	
		for (Map rot : this.rotation) {
			String name = rot.getInfo().getName();
			this.rotationNames.add(name);
		}
	}

	public void load(YamlConfiguration configuration) {
		if (configuration.get("rotation.maps") == null) {
			configuration.set("rotation.maps", Arrays.asList("map"));
		} else {
			setRotation(parseRotation(configuration
					.getStringList("rotation.maps")));
		}
		try {
			configuration.save(this.rotationFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private List<Map> parseRotation(List<String> stringList) {
		List<Map> rotation = new ArrayList<>();

		for (int i = 0; i < stringList.size(); i++) {

			Map loaded = this.getLoader().getMap(stringList.get(i));

			if (loaded != null) {
				rotation.add(loaded);
			}
		}
		return rotation;
	}

	public MapLoader getLoader() {
		return this.loader;
	}

	public void move() {
		this.postion++;
		if (this.postion >= this.rotation.size()) {
			this.postion = 0;
		}
		
		//Log.info("[OvercastPGM] position == " + this.postion);
	}

	public List<Map> getRotationMaps() {
		return this.rotation;
	}

	// only if needed!
	public boolean hasStringList(ConfigurationSection section, String map) {
		return section.getStringList(map) != null ? true : false;
	}

	public boolean hasConfigurationSection(
			ConfigurationSection configurationSection) {
		return configurationSection != null ? true : false;
	}

	public void setRotation(List<Map> list) {
		this.rotation = list;
	}

	public int getPostion() {
		return this.postion;
	}

	public Map getMap(String entry) {
		for (Map map : this.getRotationMaps()) {
			if (map.getInfo().getName().equals(entry)) {
				return map;
			}
		}
		return null;
	}

	public List<String> getRotationNames() {
		return this.rotationNames;
	}
}