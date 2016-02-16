package overcast.pgm.module.modules.filter.types;

import overcast.pgm.module.modules.filter.FilterState;

public class KillStreakFilter extends AbstractSingleFilter {

	public int amount;

	public int max;

	public int min;

	public KillStreakFilter(int amount, int max, int min) {
		this.amount = amount;
		this.max = max;
		this.min = min;
	}

	public int getAmount() {
		return this.amount;
	}

	public int getMax() {
		return this.max;
	}

	public int getMin() {
		return this.min;
	}

	@Override
	public FilterState query(Object obj) {
		if(obj instanceof Integer){
			Integer current = (Integer) obj;
			if(current == amount){
				return FilterState.ALLOW;
			}else{
				return FilterState.DENY;
			}
		}
		
		return FilterState.ABSTAIN;
	}

}
