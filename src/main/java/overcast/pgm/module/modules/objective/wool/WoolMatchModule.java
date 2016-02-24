package overcast.pgm.module.modules.objective.wool;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.Wool;
import org.bukkit.util.Vector;

import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.modules.region.types.BlockRegion;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.BlockUtils;
import overcast.pgm.util.BukkitUtils;

// TODO
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
			Vector vector = block.getLocation().toVector();

			BlockRegion monument = wool.getMonumentRegion();

			if (monument.contains(vector)) {
				if (team.equals(owner)) {
					if (BlockUtils.isWool(block)) {
						Wool w = new Wool(wool.getColor());
						if (w.getData() == block.getData()) {
							String who = player.getTeam().getColor() + player.getName();
							String placed = ChatColor.GRAY + " placed the "
									+ BukkitUtils.convertWoolNameToChatColor(wool.getWoolName())
									+ wool.getWoolName().toUpperCase();
							String t = " WOOL" + ChatColor.GRAY + " for " + player.getTeam().getColor()
									+ player.getTeam().getName();
							String message = who + placed + t;
							Bukkit.broadcastMessage(message);
						}
					} else {
						event.setCancelled(true);
					}
				} else {
					event.setCancelled(true);
					player.sendMessage(
							"You may not place at the " + BukkitUtils.convertWoolNameToChatColor(wool.getWoolName())
									+ wool.getWoolName().toUpperCase());
				}
			}
		}
	}
}
