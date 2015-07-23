package me.skylertyler.overcast.utils;

import java.io.File;

public class FileUtil {

	public static void clean(File dir, String sequence) {
		File[] list = dir.listFiles();
		for (File file : list) {
			if (file.getName().contains(sequence)) {
				file.delete();
			}
		}
	}

	public static void clearMaps(File dir) {
		clean(dir, "map-");
	}
}