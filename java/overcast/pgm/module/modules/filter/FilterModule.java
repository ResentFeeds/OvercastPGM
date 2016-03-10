package overcast.pgm.module.modules.filter;

import java.util.List;

import overcast.pgm.builder.Builder;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.Module;

public class FilterModule extends Module {
 
	private List<Filter> parents;
	private List<Filter> children;

	public FilterModule(List<Filter> parents, List<Filter> children) {
		this.parents = parents;
		this.children = children;
	} 
	
	
	public List<Filter> getParents(){
		return this.parents;
	}
	
	
	public List<Filter> getChildren(){
		return this.children;
	}

	@Override
	public Class<? extends Builder> builder() {
		return FilterBuilder.class;
	}
	
	
	
	@Override
	public MatchModule createMatchModule(Match match) {
		return new FilterMatchModule(match, this.children);
	} 
}
