package overcast.pgm.map;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import overcast.pgm.util.Log;

public class MapLoader {

	private List<Map> loaded;

	public MapLoader(File dir) throws Exception {
		this.loaded = new ArrayList<>();

		if (!dir.exists()) {
			dir.mkdir();
		}

		File[] all = dir.listFiles();

		for (File file : all) {
			if (!file.exists()) {
				throw new Exception("There are no maps loaded!");
			}
				if (file.isDirectory()) {
					if(isLoadable(file)){
						this.loaded.add(new Map(file));
					}
				}
			}
		
		
		for(Map map : this.loaded){
			Log.info(map.getSource().getName());
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
}
