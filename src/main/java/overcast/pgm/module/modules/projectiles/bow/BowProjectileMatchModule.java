package overcast.pgm.module.modules.projectiles.bow;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile; 
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.projectiles.ProjectileSource;

import overcast.pgm.OvercastPGM;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.modules.kits.PotionKit;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.Log;
import overcast.pgm.util.StringUtils;

public class BowProjectileMatchModule extends MatchModule implements Listener {

	BowProjectileModule bow;
	EntityType entity;
	double velocity;
	PotionKit potion; 

	public BowProjectileMatchModule(Match match, BowProjectileModule bow) {
		super(match);
		this.entity = bow.getEntityType();
		this.velocity = bow.getVelocity();
		this.potion = bow.getPotionKit(); 
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

	//TODO try 
	@EventHandler
	public void onShoot(ProjectileLaunchEvent event) {
		if (event.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getEntity();
			if (arrow.getShooter() instanceof Player) {
				Player shooter = (Player) arrow.getShooter();
				if (shooter.getItemInHand().getType() == Material.BOW) {
					event.setCancelled(true);
					Class<? extends Projectile> projClazz = StringUtils.classForString(this.entity);
					if (projClazz != null) {
						Projectile pro = shooter.launchProjectile(projClazz);
						pro.setVelocity(arrow.getVelocity().multiply(this.velocity)); 
					} 

					shooter.setMetadata("custom", new FixedMetadataValue(OvercastPGM.getInstance(), true));
				}
			}
		}
	}

	@EventHandler
	public void onProjectileHit(EntityDamageByEntityEvent event) {
		if (event.getCause().equals(DamageCause.PROJECTILE)) {
			if (event.getDamager() instanceof Player) {
				ProjectileSource source = ((Projectile) event.getDamager()).getShooter();

				if (source instanceof Player) {
					((Player) source).playSound(((Player) source).getLocation(), Sound.SUCCESSFUL_HIT, 0.2F, 0.5F);
				}

				if (event.getDamager().hasMetadata("custom") && event.getEntityType().equals(this.entity)) {
					if (event.getEntity() instanceof Player) {
						Player player = (Player) event.getEntity();

						if (hasPotionKit()) {
							Log.info("potion kit");
							if (event.getDamager().equals(player)) {
								this.potion.apply(OvercastPlayer.getPlayers((Player) event.getDamager()), false);
							} else {
								this.potion.apply(OvercastPlayer.getPlayers(player), false);
							}
						}
					}

					event.getDamager().remove();
				}
			}
		}
	}

	private boolean hasPotionKit() {
		return this.bow.hasPotion();
	}

}
