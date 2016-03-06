package overcast.pgm.module.modules.projectiles.custom;

import org.bukkit.entity.EntityType;
import org.bukkit.event.block.Action;
import org.bukkit.potion.PotionEffect;
import overcast.pgm.builder.Builder;
import overcast.pgm.match.MatchHandler;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.util.TimeUtil;

public class ProjectileModule extends Module {

	private final String id;
	private String name;
	private EntityType projectile;
	private double velocity;
	private double damage;
	private ActionType both;
	private PotionEffect effect;
	private int cooldown;

	public ProjectileModule(String id, String name, boolean throwable, EntityType projectile, double velocity,
			double damage, ActionType both, PotionEffect effect, int cooldown) {
		this.id = id;
		this.name = name;
		this.projectile = projectile;
		this.velocity = velocity;
		this.damage = damage;
		this.both = both;
		this.effect = effect;
		this.cooldown = cooldown;
	}

	public boolean hasID() {
		return this.id != null;
	}

	/**
	 * 
	 * @return the id
	 */

	public String getID() {
		return this.id;
	}

	/**
	 * @return the damage
	 */
	public double getDamage() {
		return damage;
	}

	/**
	 * 
	 * @return the cooldown
	 */

	public int getCooldown() {
		return this.cooldown;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the projectile
	 */
	public EntityType getProjectile() {
		return projectile;
	}

	/**
	 * @return the velocity
	 */
	public double getVelocity() {
		return velocity;
	}

	/**
	 * @return the type of action
	 */

	public ActionType getBoth() {
		return both;
	}

	/**
	 * @return the effect
	 */
	public PotionEffect getEffect() {
		return effect;
	}

	public static ProjectileModule getProjectile(String id) {
		ModuleCollection<ProjectileModule> projModule = MatchHandler.getMatchHandler().getMatch().getModules()
				.getModules(ProjectileModule.class);
		for (ProjectileModule projectile : projModule) {
			if (projectile.getID().equals(id)) {
				return projectile;
			}
		}
		return null;
	}

	@Override
	public Class<? extends Builder> builder() {
		return ProjectileBuilder.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String actions = "";
		for (Action a : getBoth().getActions()) {
			actions += a.name().toLowerCase().replaceAll("_", " ") + ", ";
		}
		return "ProjectileModule [id=" + id + ", name=" + name + ", projectile=" + projectile + ", velocity=" + velocity
				+ ", damage=" + damage + ", action=" + actions + ", cooldown=" + TimeUtil.formatIntoHHMMSS(cooldown)
				+ "]";
	}
}
