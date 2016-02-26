package overcast.pgm.module.modules.killreward;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.modules.filter.Filter;
import overcast.pgm.module.modules.filter.types.KillStreakFilter;
import overcast.pgm.module.modules.kits.KitModule;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.Log;

public class KillRewardMatchModule extends MatchModule implements Listener {

	private List<KillReward> rewards;

	public KillRewardMatchModule(Match match, List<KillReward> rewards) {
		super(match);
		this.rewards = rewards;
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
	public void onPlayerDeath(PlayerDeathEvent event) {
		int times = 0;
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			Player killer = player.getKiller();
			++times;
			Log.info(""+times); 
			for (KillReward reward : this.rewards) {
				if (reward != null) {
					for (Filter filter : reward.getFilters()) {
						if (filter instanceof KillStreakFilter) {
							KillStreakFilter ks = (KillStreakFilter) filter;
							if (ks != null) { 
								KitModule kit = reward.getKit();
								if (ks.evaluate(times).isAllowed()) {
									if (kit != null) {
										kit.applyKit(OvercastPlayer.getPlayers(killer));
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
