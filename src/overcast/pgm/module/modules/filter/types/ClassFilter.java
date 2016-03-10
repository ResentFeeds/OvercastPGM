package overcast.pgm.module.modules.filter.types;

import overcast.pgm.module.modules.filter.FilterState;

public class ClassFilter extends AbstractSingleFilter {

	private FilterState allow;
	private Class<?>[] classes;

	public ClassFilter(FilterState allow, Class<?>[] classes) {
		this.allow = allow;
		this.classes = classes;
	}

	public ClassFilter(Class<?>[] classes) {
		this(FilterState.ALLOW, classes);
	}

	@Override
	public FilterState query(Object obj) {
		for (Class<?> clazz : this.classes) {
			if (clazz.isInstance(obj)) {
				return this.allow;
			}
		}

		return FilterState.ABSTAIN;
	}
}
