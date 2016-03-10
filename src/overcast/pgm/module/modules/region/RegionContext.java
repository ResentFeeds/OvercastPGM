package overcast.pgm.module.modules.region;

import java.util.ArrayList;
import java.util.List; 

import org.bukkit.util.Vector;

import overcast.pgm.util.ContextStore;

public class RegionContext extends ContextStore<Region>{
   
	private RegionParser parser;

	public RegionContext(){    
		this.parser = new RegionParser(this);
	}
	
	/** gets all the regions containing this vector */
	
	public List<Region> getContaining(Vector point) {
        List<Region> result = new ArrayList<Region>();
        for(Region region : this.store.values()) {
            if(region.contains(point)) {
                result.add(region);
            }
        }
        return result;
    }

	public RegionParser getParser() {
		return parser;
	} 
	 
}
