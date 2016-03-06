package overcast.pgm.module.modules.friendlyfire;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.player.OvercastPlayer;

public class FriendlyFireMatchModule extends MatchModule implements Listener {

	/** Not tested */

	boolean friendlyfire;
	boolean friendlyfirerefund;

	public FriendlyFireMatchModule(Match match, boolean friendlyfire, boolean friendlyfirerefund) {
		super(match);
		this.friendlyfire = friendlyfire;
		this.friendlyfirerefund = friendlyfirerefund;
	}

	@Override
	public void load() {
		match.registerEvents(this);
	}

	@Override
	public void unload() {
	}

	@Override
	public void enable() {
	}

	@Override
	public void disable() {
	}

	@EventHandler
	public void onPlayer(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		Entity damager = event.getDamager();
		if (damager instanceof Player && entity instanceof Player) {
			Player Dplayer = (Player) damager;
			Player KPlayer = (Player) entity;
			OvercastPlayer ODplayer = OvercastPlayer.getPlayers(Dplayer);
			OvercastPlayer OKplayer = OvercastPlayer.getPlayers(KPlayer);

			if (ODplayer.getTeam() == OKplayer.getTeam()) {
				event.setCancelled(true);
			}
		}
	}

}
