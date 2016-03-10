package overcast.pgm.event.tutorial;

import java.util.List;

import overcast.pgm.event.PGMEvent;
import overcast.pgm.module.modules.tutorial.TutorialStage;
import overcast.pgm.player.OvercastPlayer;

public class TutorialStartEvent extends PGMEvent {

	private OvercastPlayer player;
	private List<TutorialStage> stages;

	public TutorialStartEvent(OvercastPlayer player, List<TutorialStage> stages) {
		this.player = player;
		this.stages = stages;
	}

	public OvercastPlayer getPlayer() {
		return this.player;
	}

	public List<TutorialStage> getStages() {
		return this.stages;
	}

	public TutorialStage getFirstStage() {
		return this.stages.get(0);
	} 
}
