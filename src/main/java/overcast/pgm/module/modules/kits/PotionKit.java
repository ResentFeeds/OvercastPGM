package overcast.pgm.module.modules.kits;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import overcast.pgm.module.modules.kits.parsers.PotionKitParser;
import overcast.pgm.player.OvercastPlayer;

public class PotionKit {

	private int duration;
	private int amplifier;
	private PotionEffectType type;
 
	public PotionKit(int duration, int amplifier, PotionEffectType type){
		 this.duration = duration;
		 this.amplifier = amplifier;
		 this.type = type;
	}  
	
	
	public PotionKit(PotionKitParser parser){
		 this(parser.getDuration(), parser.getAmplifier(), parser.getEffectType());
	}
	
	public int getDuration(){
		return this.duration;
	}
	
	
	public int getAmplifier(){
		return this.amplifier;
	}
	
	
	public PotionEffectType getType(){
		return this.type;
	} 
	
	
	public void apply(OvercastPlayer p, boolean force){
		if(force){
			  p.addPotionEffect(new PotionEffect(this.type, (int) this.duration / 2, (int) this.amplifier / 2));
		}else{ 
			PotionEffect effect = new PotionEffect(this.type, this.duration, this.amplifier);  
			p.addPotionEffect(effect);
		}
	}
}
