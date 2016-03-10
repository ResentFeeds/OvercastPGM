package overcast.pgm.module.modules.killreward;

import java.util.List;

import overcast.pgm.module.modules.filter.Filter;
import overcast.pgm.module.modules.kits.KitModule;

public class KillReward {

	private List<Filter> filters;
	private KitModule kit;

	public KillReward(List<Filter> filters, KitModule kit) {
		this.filters = filters; 
		this.kit = kit;
	} 
	
	public List<Filter> getFilters(){
		return this.filters;
	}
	
	
	public KitModule getKit(){
		return this.kit;
	} 
}
