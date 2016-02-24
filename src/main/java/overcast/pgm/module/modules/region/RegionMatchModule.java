package overcast.pgm.module.modules.region;

import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.google.common.collect.ArrayListMultimap;

import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.modules.kits.KitModule;
import overcast.pgm.module.modules.region.types.CircleRegion;
import overcast.pgm.module.modules.region.types.CylinderRegion;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.MessageUtils;

public class RegionMatchModule extends MatchModule implements Listener {

	private RFAContext context;

	private RegionContext rcontext;

	public RegionMatchModule(Match match, RFAContext context) {
		super(match);
		this.rcontext = match.getFactory().getRegionContext();
		this.context = context;
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
		this.match.getFactory().getRegionContext().getAll().clear();
		HandlerList.unregisterAll(this);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		List<Region> contains = rcontext.getContaining(event.getBlock().getLocation().toVector());

		for (Region region : contains) {
			if (region instanceof CylinderRegion) {
				CylinderRegion cuboid = (CylinderRegion) region;
				String name = rcontext.getName(cuboid);

				event.setCancelled(true);

				if (name != null) {
					MessageUtils.warningMessage(event.getPlayer(),
							ChatColor.RED + "You cant break blocks here " + name);
				} else {

					MessageUtils.warningMessage(event.getPlayer(), ChatColor.RED + "You cant break blocks here");
				}
			} else if (region instanceof CircleRegion) {
				CircleRegion circle = (CircleRegion) region;

				String name = rcontext.getName(circle);
				event.setCancelled(true);
				if (name != null) {
					MessageUtils.warningMessage(event.getPlayer(),
							ChatColor.RED + "You cant break blocks here " + name);
				} else {

					MessageUtils.warningMessage(event.getPlayer(), ChatColor.RED + "You cant break blocks here");
				}
			}
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {

		OvercastPlayer player = OvercastPlayer.getPlayers(event.getPlayer());
		Location from = event.getFrom();
		Location to = event.getTo();

		if (from == to)
			return;

		List<Region> contains = rcontext.getContaining(to.toVector());

		for (Region r : contains) {
			if (r != null && r instanceof CircleRegion) {
				CircleRegion circle = (CircleRegion) r;

				if (circle != null) {
					List<KitModule> kits = getMatch().getModules().getModules(KitModule.class);

					KitModule kit = kits.get(0);

					if (kit != null) {
						kit.applyKit(player);
					}
				}
			}
		}

	}

}
