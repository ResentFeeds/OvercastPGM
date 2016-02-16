package overcast.pgm.module.modules.filter;

import overcast.pgm.builder.Builder;
import overcast.pgm.module.Module;

public class FilterModule extends Module {

	private FilterNode node;

	public FilterModule(FilterNode node){
		this.node = node;
	}

	@Override
	public Class<? extends Builder> builder() {
		return FilterBuilder.class;
	}
	
	
	public FilterNode getFilterNode(){
		return this.node;
	}
}
