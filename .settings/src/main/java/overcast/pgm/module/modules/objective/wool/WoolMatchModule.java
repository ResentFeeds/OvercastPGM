package overcast.pgm.module.modules.objective.wool;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.util.Vector;

import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.modules.region.types.BlockRegion;
import overcast.pgm.player.OvercastPlayer;

public class WoolMatchModule extends MatchModule implements Listener {

	WoolObjective wool;

	public WoolMatchModule(Match match, WoolObjective wool) {
		super(match);
		this.wool = wool;
	}

	@Override
	public void load() {
		this.match.registerEvents(this);
	}

	@Override
	public void unload() {
		HandlerList.unregisterAll(this);
	}

	@Override
	public void enable() {
		this.match.registerEvents(this);
	}

	@Override
	public void disable() {
		HandlerList.unregisterAll(this);
	}
	
	
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		OvercastPlayer p = OvercastPlayer.getPlayers(event.getPlayer());
		
		Block block = event.getBlock();
		
		if(block != null){
			Location loc = block.getLocation();
			Vector vec = loc.toVector();
		    if(wool.getMonumentRegion() != null){
		    	BlockRegion monument = wool.getMonumentRegion();
		    	
		    	if(monument != null){ 
		    		if(monument.contains(vec) && p.getTeam() != wool.getTeam() || p.getTeam() == wool.getTeam()){
		    		     p.sendMessage("You cant place here");
		    		}
		    	}
		    }
		}
	}

}
