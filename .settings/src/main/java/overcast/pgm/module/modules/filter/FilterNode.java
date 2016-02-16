package overcast.pgm.module.modules.filter;

import java.util.List;

public class FilterNode {

	private List<Filter> parents;
	private List<Filter> children;

	public FilterNode(List<Filter> parents, List<Filter> children) {
		this.parents = parents;
		this.children = children;
	} 
	
	
	public List<Filter> getParents(){
		return this.parents;
	}
	
	
	public List<Filter> getChildren(){
		return this.children;
	}
}
