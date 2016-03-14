package overcast.pgm.module.modules.filter.types;

import overcast.pgm.module.modules.filter.Filter;
import overcast.pgm.module.modules.filter.FilterState;


public abstract class AbstractSingleFilter implements Filter {
	
    public abstract FilterState query(Object obj);

    @Override
    public FilterState evaluate(Object... objects) {
        for(Object obj : objects) {
            FilterState response = this.query(obj);
            if(response == FilterState.ALLOW || response == FilterState.DENY) {
                return response;
            }
        }
        return FilterState.ABSTAIN;
    } 
    
}
