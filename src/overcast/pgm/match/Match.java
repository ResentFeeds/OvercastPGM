package overcast.pgm.match;

import org.w3c.dom.Document;

import overcast.pgm.builder.Builder;
import overcast.pgm.map.Map;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleFactory;
import overcast.pgm.util.DocUtil;

public class Match {

	//TODO need to find a way for modules to load at different times :)
	//TODO find a way to make the InfoModule load by itself in the MapLoader // will remove rotation packages
	
	//TODO finish Internal Module 
	
	//TODO starting ending cycling 

	
	
	public int id; /** the id of the match */
	
	private MatchState state; /** the current state of the match */
	
	private Map map; /** current map */
	
	private Document document;  /** the document being parsed */
	// modules
	private ModuleFactory factory;

	public Match(int id, Map map) {
		this.id = id;
		this.state = MatchState.LOAD;
		this.map = map;
		this.document = DocUtil.parse(getMap().getXML());
		this.factory = new ModuleFactory(this.getDocument());
		
		for (Class<? extends Module> modules : this.getFactory()
				.getModules()) {
			try {
				Class<? extends Builder> builder = modules.newInstance()
						.builder();
				Builder build = builder.newInstance();
				build.build(this.getDocument());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	} 

	public Document getDocument() {
		return this.document;
	}

	public MatchState getState() {
		return this.state;
	}

	public Map getMap() {
		return this.map;
	}

	public ModuleFactory getFactory() {
		return this.factory;
	}
}
