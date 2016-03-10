package overcast.pgm.builder;
 

import org.w3c.dom.Document;

import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.ModuleFactory;
import overcast.pgm.xml.XMLParseException;

public abstract class Builder {

	public abstract ModuleCollection<Module> build(Document doc) throws XMLParseException ;
	
	public BuilderAbout getAbout(){
		return new BuilderAbout(getClass());
	} 
	
	public ModuleCollection<Module> build(Document doc, ModuleFactory fac) throws XMLParseException {
		return null;
	}
}
