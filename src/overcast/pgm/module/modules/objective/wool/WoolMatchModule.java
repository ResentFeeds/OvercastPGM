package overcast.pgm.module.modules.objective.wool;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import overcast.pgm.event.PlayerPlaceWoolEvent;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.modules.region.types.BlockRegion;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.BlockUtils;
import overcast.pgm.util.BukkitUtils;
import overcast.pgm.util.Characters;
import overcast.pgm.util.Log;
import overcast.pgm.util.MessageUtils;

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
		this.wool.clearTouched();
		HandlerList.unregisterAll(this);
	}

	@Override
	public void enable() {
		this.match.registerEvents(this);
	}

	@Override
	public void disable() {
		this.wool.clearTouched();
		HandlerList.unregisterAll(this);
	}

	// NEED TO FIX
	@EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent event) {
		OvercastPlayer player = OvercastPlayer.getPlayers(event.getPlayer());
		Team team = player.getTeam();
		Team owner = this.wool.getTeam();

		BlockRegion monument = this.wool.getMonument();
		if (monument.contains(event.getBlock().getLocation().toVector())) {
			if (team == owner) {
				if (BlockUtils.isWool(event.getBlock()) && event.getBlock().getData() == wool.getColor().getDyeData()) {
					Log.info("wool placed ");
				} else {
					event.setCancelled(true);
					MessageUtils.warningMessage(player.getPlayer(),
							ChatColor.RED + "Only " + BukkitUtils.convertDyeColorToChatColor(this.wool.getColor())
									+ this.wool.getWoolName().toUpperCase() + ChatColor.RED + " may be placed here!");
				}
			} else {
				event.setCancelled(true);
				MessageUtils.warningMessage(player.getPlayer(),
						ChatColor.RED + "Only " + BukkitUtils.convertDyeColorToChatColor(this.wool.getColor())
								+ this.wool.getWoolName().toUpperCase() + ChatColor.RED + " may be placed here!");
			}
		} 
	}

	// FIXME broadcasts is going off 2 to 8 times

	@EventHandler
	public void onWoolPlace(PlayerPlaceWoolEvent event) {
		if (event.getWhoPlaced() != null) {
			OvercastPlayer who = event.getWhoPlaced();
			WoolObjective wool = event.getWoolObjective();
			Team whosTeam = who.getTeam();
			try {
				wool.setCompleted(true, Characters.Check);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (wool.isCompleted()) {
				Bukkit.broadcastMessage(whosTeam.getColor() + (who.hasNickname() ? who.getNickname() : who.getName())
						+ ChatColor.WHITE + " placed " + BukkitUtils.convertDyeColorToChatColor(wool.getColor())
						+ wool.getWoolName() + ChatColor.WHITE + " for " + wool.getTeam().getColoredName());
				return;
			}
		}
	}

	@EventHandler
	public void onWoolPickup(PlayerPickupItemEvent event) {
		ItemStack pickedUp = event.getItem().getItemStack();
		OvercastPlayer player = OvercastPlayer.getPlayers(event.getPlayer());

		if (pickedUp.getType() == Material.WOOL && pickedUp.getData().getData() == this.wool.getColor().getData()
				&& player.getTeam() == this.wool.getTeam()) {
			this.wool.handleTouches(player);	
		}
	}
}
