package overcast.pgm.module.modules.filter;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.modules.filter.types.HoldingFilter;
import overcast.pgm.util.StringUtils;

public class FilterMatchModule extends MatchModule implements Listener {

	private final List<Filter> children;

	public FilterMatchModule(Match match, List<Filter> children) {
		super(match);
		this.children = children;
	}

	@Override
	public void load() {
		
	}

	@Override
	public void unload() {
		HandlerList.unregisterAll(this);
	}

	@Override
	public void enable() {
		match.registerEvents(this);
	}

	@Override
	public void disable() {
		HandlerList.unregisterAll(this);
	}

	@EventHandler
	public void onPlayer(PlayerItemHeldEvent event) {
		for (Filter filter : getChildren()) {
			if (filter != null) {
				if (filter instanceof HoldingFilter) {
					HoldingFilter holding = (HoldingFilter) filter;

					if (holding.query(event.getPlayer()).isAllowed()) {
						event.getPlayer().sendMessage(
								ChatColor.GOLD + "You got a " + StringUtils.getName(holding.getMaterial().name()));
					}
				}
			}
		}
	}

	public List<Filter> getChildren() {
		return this.children;
	}

}
