package overcast.pgm.timer;

import org.bukkit.Bukkit;

import overcast.pgm.match.Match;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.modules.broadcast.BroadcastModule;
import overcast.pgm.util.TimeUtil;

public class MatchTimer extends OvercastTimer {

	private ModuleCollection<BroadcastModule> bModules;

	public MatchTimer(int sec, Match match) {
		super(sec, match);

		boolean broadcasts = match.getModules().isModuleLoaded(BroadcastModule.class);
		if (broadcasts) {
			this.bModules = match.getModules().getModules(BroadcastModule.class);
		}
	}

	@Override
	public void run() {
		this.sec++;

		for (BroadcastModule b : bModules) {
			if (b != null) {
				b.run();
			}
		}

		Bukkit.broadcastMessage(TimeUtil.formatIntoHHMMSS(this.sec));
	}
}
