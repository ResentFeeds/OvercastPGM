package overcast.pgm.parser;

import java.util.Set;

public class ParserResult<T>{

	private Set<T> entries;

	public ParserResult(Set<T> entries) {
		this.entries = entries;
	}

	public Set<T> getEntries() {
		return entries;
	}
}
