package overcast.pgm.builder;

import java.util.List;

import org.w3c.dom.Document;

import overcast.pgm.module.Module;

public abstract class Builder {

	public abstract List<Module> build(Document doc);
	
	public BuilderAbout getAbout(){
		return new BuilderAbout(getClass());
	}

}
