package overcast.pgm.builder;


import org.jdom2.Document;

import overcast.pgm.module.Module;

public abstract class Builder {

	public abstract Module build(Document doc);
	
	public BuilderAbout getAbout(){
		return new BuilderAbout(getClass());
	}

}
