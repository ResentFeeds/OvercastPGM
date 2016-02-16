package overcast.pgm.module.modules.filter.types;

import java.util.List;

import com.google.common.collect.ImmutableList;

import overcast.pgm.module.modules.filter.Filter;

public abstract class AbstractMultiFilter implements Filter{

    List<Filter> filters;

	public AbstractMultiFilter(Iterable<? extends Filter> filters) { 
		this.filters = ImmutableList.copyOf(filters);
	}
	 
	 
	public List<Filter> getSubFilters(){
		return this.filters;
	}
}
