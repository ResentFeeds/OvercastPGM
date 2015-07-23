package overcast.pgm.map;

import java.io.File;

public class Map {

	private File source;
	private File xml;

	public Map(File source) {
		this.source = source;
		this.xml = new File(source, "map.xml");

	}

	public File getSource() {
		return this.source;
	}

	public File getXML() {
		return this.xml;
	}
}
