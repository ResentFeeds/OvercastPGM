package overcast.pgm.module.modules.filter.types;

import overcast.pgm.module.modules.filter.FilterState;

public class StaticFilter extends AbstractSingleFilter {
	
     private FilterState state;

	public StaticFilter(FilterState state) { 
    	 this.state = state;
	}

	@Override
	public FilterState query(Object obj) {
		return this.state;
	} 
}
