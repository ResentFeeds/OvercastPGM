package overcast.pgm.module;

import overcast.pgm.match.Match;

public abstract class MatchModule {

	protected final Match match;

	public MatchModule(Match match) {
		this.match = match;
	}

	public abstract void load();

	public abstract void unload();
	
    public abstract void enable();
    
    public abstract void disable();


	public Match getMatch() {
		return this.match;
	}

}
