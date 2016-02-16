package overcast.pgm.timer;

import overcast.pgm.match.Match;

public class MatchTimer extends OvercastTimer {

	public MatchTimer(int sec, Match match) {
		super(sec, match);
	}

	@Override
	public void run() {
		this.sec++;
	}
}
