package overcast.pgm.module.modules.tutorial;

import java.util.List;

import overcast.pgm.match.Match;
import overcast.pgm.player.OvercastPlayer;

public class TutorialManager {
 
	List<OvercastPlayer> tutorialers;
	private TutorialModule tutorial;

	public TutorialManager(Match match) {
	   // this.tutorial = match.getModules().getModule(TutorialModule.class);
	}

	public List<OvercastPlayer> getTutorialers() {
		return this.tutorialers;
	}

	public void addTutorialer(OvercastPlayer p) {
		if (!hasTutorialer(p)) {
			this.tutorialers.add(p);
		}
	}

	public void removeTutorialer(OvercastPlayer p) {
		if (hasTutorialer(p)) {
			this.tutorialers.remove(p);
		}
	}

	public boolean hasTutorialer(OvercastPlayer p) {
		if (this.tutorialers.contains(p)) {
			return false;
		}

		return true;
	}

	public TutorialModule getTutorial() {
		return this.tutorial;
	}
	
	
	public boolean hasTutorial(){
		return this.tutorial != null;
	}
}
