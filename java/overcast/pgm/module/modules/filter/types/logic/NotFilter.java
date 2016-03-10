package overcast.pgm.module.modules.filter.types.logic;

import overcast.pgm.module.modules.filter.Filter;
import overcast.pgm.module.modules.filter.FilterState;
import overcast.pgm.module.modules.filter.types.AbstractMultiFilter;

public class NotFilter extends AbstractMultiFilter {

	
	public NotFilter(Iterable<? extends Filter> filters) {
		super(filters);
	}

	@Override
	public FilterState evaluate(Object... obj) {
		boolean abstain = true;
		for (Filter subF : getSubFilters()) {
			if (subF != null) {
				if (!(subF.evaluate(obj)).equals(FilterState.ABSTAIN))
					abstain = false; 
				if (subF.evaluate(obj).equals(FilterState.ALLOW)) {
					return FilterState.DENY;
				}
			}
		}

		return abstain ? FilterState.ABSTAIN : FilterState.ALLOW;
	}
}
