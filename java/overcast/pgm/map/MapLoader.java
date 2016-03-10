package overcast.pgm.map;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import overcast.pgm.OvercastPGM;
import overcast.pgm.module.modules.info.InfoModule;
import overcast.pgm.module.modules.info.Version;
import overcast.pgm.util.Log;

public class MapLoader {

	private List<Map> loaded;
	private List<String> names;


	public MapLoader(File dir) throws Exception {
		this.loaded = new ArrayList<>();
		if (!dir.exists()) {
			dir.mkdir();
		}

		File[] all = dir.listFiles();

		for (File source : all) {
			if (!source.exists()) {
				throw new Exception("There are no maps loaded!");
			}

			if (source.isDirectory()) {
				if (isLoadable(source)) {
					Map map = new Map(source);

					Version latest = OvercastPGM.getInstance().getXMLProto();
					Version proto = map.getInfo().getProto();

					if (proto.isGreater(latest)) {
						Log.warning(map.getInfo().getName() + " didn't load because of the proto is greater than "
								+ latest.toString());
					} else {

						this.loaded.add(map);
					}
				}
			}
		}

		this.names = new ArrayList<>();

		for (Map map : this.loaded) {
			if (map != null) {
				String name = map.getInfo().getName();
				this.names.add(name);  
			}
		}
	}

	public static boolean isLoadable(File dir) {
		File xml = new File(dir, "map.xml");
		File level = new File(dir, "level.dat");
		File region = new File(dir, "region");

		boolean vaildXML = xml.isFile() && !xml.isHidden();

		boolean vaildLEVEL = level.isFile() && !level.isHidden();

		boolean vaildREGION = region.isDirectory() && !region.isHidden();

		boolean loadable = vaildXML && vaildLEVEL && vaildREGION;

		return loadable;
	}

	public List<Map> getLoadedMaps() {
		return this.loaded;
	}

	public Map getMap(String name) {
		for (Map loaded : this.loaded) {
			if (loaded != null) {
				InfoModule info = loaded.getInfo();

				if (info.getName().equalsIgnoreCase(name)) {
					return loaded;
				}
			}
		}
		return null;
	}

	public List<String> getLoadedMapNames() {
		return this.names;
	}
}
