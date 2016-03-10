package overcast.pgm.module.modules.filter.types;

import java.util.Arrays;

import overcast.pgm.module.modules.filter.Filter;
import overcast.pgm.module.modules.filter.FilterState;

public class AllFilter extends AbstractMultiFilter {
 

	public AllFilter(Iterable<Filter> filters) {  
		super(filters);
	} 
  

	@Override
	public FilterState evaluate(Object... objects){
		 FilterState result = FilterState.ABSTAIN;
		 for(Filter filter : this.filters){
			 if(filter != null){
				 FilterState filterResult  = filter.evaluate(objects);
				 
				 if(filterResult == FilterState.ALLOW){
					 return filterResult;
				 }else if(filterResult == FilterState.DENY){
					 result = filterResult; 
				 }
			 }
		 }
		 return result;
	} 
	
	
	
	public Filter ofFilter(Filter...filters){
		 switch(filters.length){
		 case 0:
			return new StaticFilter(FilterState.ABSTAIN);
		 case 1:
			 return filters[0];
		 default:
			 return new AllFilter(Arrays.asList(filters));
		 } 
	}
}
