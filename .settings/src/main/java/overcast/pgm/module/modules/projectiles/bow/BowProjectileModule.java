package overcast.pgm.module.modules.projectiles.bow;

import org.bukkit.entity.EntityType;

import overcast.pgm.builder.Builder;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.Module;
import overcast.pgm.module.modules.kits.PotionKit;

public class BowProjectileModule extends Module {
 
	EntityType type;
	double velocity;
	PotionKit potion;
	
	public BowProjectileModule(EntityType type, double velocity, PotionKit potion) { 
		this.type = type;
		this.velocity = velocity;
		this.potion = potion;
	}
	
	
	@Override
	public MatchModule createMatchModule(Match match) {
		return new BowProjectileMatchModule(match, this);
	}
	
	
	public boolean hasPotion(){
		return getPotionKit() != null;
	}
	
	
	public EntityType getEntityType(){
		return this.type;
	}
	
	
	public double getVelocity(){
		return this.velocity;
	}
	
	
	public PotionKit getPotionKit(){
		return this.potion;
	}
	
	@Override
	public Class<? extends Builder> builder() { 
		return BowProjectileBuilder.class;
	}
	
}
