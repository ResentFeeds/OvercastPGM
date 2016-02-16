package overcast.pgm.module.modules.tutorial;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import overcast.pgm.event.tutorial.TutorialStartEvent;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.player.OvercastPlayer;

public class TutorialMatchModule extends MatchModule implements Listener {

	/** TODO MORE
	 * 
	 */
	
	List<TutorialStage> stages;

	public TutorialMatchModule(Match match, List<TutorialStage> stages) {
		super(match);
		this.stages = stages;
	}

	@Override
	public void load() {

	}

	@Override
	public void unload() {

	}

	@Override
	public void enable() {
		this.match.registerEvents(this);
	}

	@Override
	public void disable() {

	}

	  @EventHandler(priority = EventPriority.MONITOR)
	    public void onPlayerInteract(PlayerInteractEvent event) {
	        if(event.getItem() != null && event.getItem().getType() == TutorialModule.TUTORIAL_ITEM) {
	            OvercastPlayer player = OvercastPlayer.getPlayers(event.getPlayer());
	            if(player != null) {
	                TutorialModule module = getMatch().getModules().getModule(TutorialModule.class);
	                if(module != null) {
	                    TutorialStage current = player.getTutorialStage();

	                    TutorialStage next = null;
	                    switch(event.getAction()) {
	                    case LEFT_CLICK_AIR:
	                    case LEFT_CLICK_BLOCK:
	                        next = module.getPreviousStage(current);
	                        break;
	                    case RIGHT_CLICK_AIR:
	                    case RIGHT_CLICK_BLOCK:
	                        next = module.getNextStage(current);
	                        break;
	                    default:
	                        break;
	                    }

	                    if(next != null) {
	                        player.setTutorialStage(next);
	                    }
	                }
	            }
	        }
	    }
	
	@EventHandler
	public void onTutorialStart(TutorialStartEvent event){
		OvercastPlayer player = event.getPlayer();
		
		TutorialStage first = event.getFirstStage();
		
		if(first != null){
			 for(String m : first.getMessage()){
				  if(m != null){
					  player.sendMessage(m);
				  }
			 }
		}
	}
	
	
	
	
}
