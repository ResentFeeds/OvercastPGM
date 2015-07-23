package overcast.pgm.match;

public class Match {

	public int id;
	private MatchState state;

	public Match(int id) {
		this.id = id;
		this.state = MatchState.LOAD;
	}

	public MatchState getState() {
		return this.state;
	}
}
