package overcast.pgm.rotation;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.collect.Lists;

public class Rotation {

	private List<RotationSlot> slots;
	private File file;

	public Rotation(File file) {
		this.slots = Lists.newArrayList();
	
		this.file = file;
		// TODO check how i did the rotation.yml last time!
		if (!file.exists() || file.isDirectory() || file.isHidden()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileConfiguration yaml = YamlConfiguration.loadConfiguration(this.file);

		ConfigurationSection rotation = yaml.createSection("rotation");
		
		try {
			yaml.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		};
		
		if(rotation == null) return;
		

	}

	public File getFile() {
		return this.file;
	}

	public List<RotationSlot> getSlots() {
		return this.slots;
	}

	public boolean hasSlots() {
		return this.getSlots().size() > 0;
	}

	/**
	 * get a slot by its source
	 * 
	 * @param sources name
	 * @return
	 */
	public RotationSlot getSlot(String name) {
		for (RotationSlot slot : this.getSlots()) {
			if (slot.getMap().getSource().getName().equals(name)) {
				return slot;
			}
		}
		return null;
	}
}
