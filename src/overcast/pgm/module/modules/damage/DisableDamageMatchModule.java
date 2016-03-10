package overcast.pgm.module.modules.damage;

import java.util.Set;
 



import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule; 
import overcast.pgm.module.modules.tntracker.TNTTrackerMatchModule;
import overcast.pgm.player.OvercastPlayer;

/**
 * FIX TNT attributes not working ;/
 * 
 * @author tylern
 *
 */

// TODO finish this up (add DamageType) :)
public class DisableDamageMatchModule extends MatchModule implements Listener {

	Set<DamageCause> causes;
	private boolean self;
    boolean ally;
	boolean enemy;
	boolean other; 

	public DisableDamageMatchModule(Match match, Set<DamageCause> causes, boolean self, boolean ally, boolean enemy, boolean other) {
		super(match);
		this.causes = causes;
		this.self = self;
		this.ally = ally;
		this.enemy = enemy;
		this.other = other;
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
		this.causes.clear();
	}

	 @EventHandler
	    public void onEntityDamage(EntityDamageEvent event) {
	        if (causes.contains(event.getCause()) && (event.getCause() != DamageCause.BLOCK_EXPLOSION || event.getCause() != DamageCause.ENTITY_EXPLOSION)) {
	            event.setCancelled(true);
	        } else if (event.getCause() == DamageCause.BLOCK_EXPLOSION || event.getCause() == DamageCause.ENTITY_EXPLOSION) {
	            if (event instanceof EntityDamageByEntityEvent) {
	                if (event.getEntity() instanceof Player && TNTTrackerMatchModule.getWhoPlaced(((EntityDamageByEntityEvent) event).getDamager()) != null) {
	                    Player player = (Player) event.getEntity();
	                    UUID source = TNTTrackerMatchModule.getWhoPlaced(((EntityDamageByEntityEvent) event).getDamager());
	                    OvercastPlayer victim = OvercastPlayer.getPlayers(player);
	                    
	                    OvercastPlayer damager = OvercastPlayer.getPlayers(Bukkit.getPlayer(source));
	                    
	                    if (Bukkit.getOfflinePlayer(source).isOnline()) {
	                        if (Bukkit.getPlayer(source).equals(player)) {
	                            event.setCancelled(this.self);
	                        } else if (damager.getTeam() == victim.getTeam()) {
	                            event.setCancelled(this.ally);
	                        } else if (damager.getTeam() != victim.getTeam()) {
	                            event.setCancelled(this.enemy);
	                        } else {
	                            event.setCancelled(this.other);
	                        }
	                    }
	                }
	            } else if(causes.contains(DamageCause.BLOCK_EXPLOSION)) event.setCancelled(true);
	        }
	    }
	}
