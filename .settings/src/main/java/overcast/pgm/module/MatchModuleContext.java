package overcast.pgm.module;

import java.util.Set;

import overcast.pgm.match.Match;
import overcast.pgm.util.Log;

import com.google.common.collect.Sets;

public class MatchModuleContext {

	protected Set<MatchModule> matchModules;

	public MatchModuleContext() {
		this.matchModules = Sets.newHashSet();
	}

	public void add(MatchModule module) {
		this.matchModules.add(module);
	}

	public void remove(MatchModule module) {
		this.matchModules.remove(module);
	}

	@SuppressWarnings("unchecked")
	public <T extends MatchModule> T getMatchModule(Class<T> matchModuleClass) {
		for (MatchModule matchModule : this.matchModules) {
			if (matchModuleClass.isInstance(matchModule)) {
				return (T) matchModule;
			}
		}
		return null;
	}

	public void load() {
		for (MatchModule matchModule : this.matchModules) {
			matchModule.load();
		}
	}

	public void unload() {
		for (MatchModule matchModule : this.matchModules) {
			matchModule.unload();
		}
	}

	public void enable() {
		for (MatchModule matchModule : this.matchModules) {
			matchModule.enable();
		}
	}

	public void disable() {
		for (MatchModule matchModule : this.matchModules) {
			matchModule.disable();
		}
	}


	public void addMatchModules(Match match) {
		for (Module module : match.getModules()) {
			if (module != null) {
				MatchModule mm = module.createMatchModule(match);
				if (mm != null) {
					this.matchModules.add(mm);
				}
			}
		}

		int size = this.matchModules.size();
		Log.info("there are " + size + " MatchModules loaded!");
	}
}
