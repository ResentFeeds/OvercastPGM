package overcast.pgm.module.modules.maxheight;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.MessageUtils;

public class MaxBuildHeightMatchModule extends MatchModule implements Listener {

	int height;
	String message;

	public MaxBuildHeightMatchModule(Match match, int height) {
		super(match);
		this.height = height;
		this.message = ChatColor.RED
				+ "You have reached the maximum build height ("
				+ ChatColor.AQUA + height + ChatColor.RED + " blocks)";
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
		OvercastPlayer p = OvercastPlayer.getPlayers(event.getPlayer());
		if (p.hasTeam() && !p.isObserver()) {
			if (event.getBlock().getY() > this.height) {
				event.setCancelled(true);
				MessageUtils.warningMessage(p.getPlayer(), this.message);
			}
		}
	}

	@EventHandler
	public void onBlockPlace(BlockBreakEvent event) {
		OvercastPlayer p = OvercastPlayer.getPlayers(event.getPlayer());
		if (p.hasTeam() && !p.isObserver()) {
			if (event.getBlock().getY() > this.height) {
				event.setCancelled(true);
				MessageUtils.warningMessage(p.getPlayer(), this.message);
			}
		}
	}

	@EventHandler
	public void onBucketFill(PlayerBucketFillEvent event) {
		OvercastPlayer p = OvercastPlayer.getPlayers(event.getPlayer());
		if (p.hasTeam() && !p.isObserver()) {
			if (event.getBlockClicked().getY() > this.height) {
				event.setCancelled(true);
				MessageUtils.warningMessage(p.getPlayer(), this.message);
			}
		}
	}

	@EventHandler
	public void onBucketEmpty(PlayerBucketEmptyEvent event) {
		OvercastPlayer p = OvercastPlayer.getPlayers(event.getPlayer());
		if (p.hasTeam() && !p.isObserver()) {
			if (event.getBlockClicked().getY() > this.height) {
				event.setCancelled(true);
				MessageUtils.warningMessage(p.getPlayer(), this.message);
			}
		}
	}
}