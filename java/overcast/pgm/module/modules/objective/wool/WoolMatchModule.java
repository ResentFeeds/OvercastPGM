package overcast.pgm.module.modules.objective.wool;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.Wool;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.modules.region.types.BlockRegion;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.BlockUtils;
import overcast.pgm.util.BukkitUtils;
import overcast.pgm.util.Characters;
//FIXME wools not working when placed :/
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
	public void onBlockPlace(BlockPlaceEvent event) {
		OvercastPlayer player = OvercastPlayer.getPlayers(event.getPlayer());
		Block block = event.getBlock();
		if (!player.isObserver()) {
			Team team = player.getTeam();
			Team owner = wool.getTeam();

			BlockRegion monument = wool.getMonumentRegion(); 
			if (monument.contains(block.getLocation())) {
				Log.info("monumment vector checked");
				if (team.equals(owner) && BlockUtils.isWool(block)) {
					Wool w = new Wool(wool.getColor());
					if (block.getData() == w.getData()) {
						String who = player.getTeam().getColor() + player.getName();
						String placed = ChatColor.WHITE + " placed the "
								+ BukkitUtils.convertDyeColorToChatColor(wool.getColor()) + wool.getWoolName().toUpperCase();
						String t = ChatColor.WHITE + " for " + player.getTeam().getColoredName();
						String message = who + placed + t;
						Bukkit.broadcastMessage(message);
						
							try {
								wool.setCompleted(true, Characters.Check);
							} catch (Exception e) { 
								e.printStackTrace();
							} 
					} else {
						event.setCancelled(true);
					}
				} else {
					event.setCancelled(true);
					player.sendMessage(
							"You may not place at the " + BukkitUtils.convertDyeColorToChatColor(wool.getColor())
									+ wool.getWoolName().toUpperCase());
				}
			} else {
				return;
			}
		}
	}
}
