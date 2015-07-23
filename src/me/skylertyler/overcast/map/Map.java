package me.skylertyler.overcast.map;

import java.io.File;

public class Map {

	String DEFAULT_XML = "map.xml";
	
	private File source;
	
	boolean hasMapXML = false;
	
	public Map(File source){
		this.source = source;
		
		for(File file : this.getSource().listFiles()){
			if(file.getName().equals(this.DEFAULT_XML)){
				hasMapXML = true;
			}
		}
	}

	
	public File getSource(){
		return this.source;
	}
}
