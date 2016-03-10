package overcast.pgm.module.modules.kits;

import com.google.common.base.Preconditions;

import overcast.pgm.module.modules.kits.parsers.HealthKitParser;
import overcast.pgm.player.OvercastPlayer;

public class HealthKit {

	private int half;

	public HealthKit(int half){
		Preconditions.checkArgument(0 < half && half <= 20);
		this.half = half;
	}
	
	
	public HealthKit(HealthKitParser parser){
		this(parser.getHalf());
	}
	
	
	public int getHalf(){
		return this.half;
	}
	 
	
	public void apply(OvercastPlayer p, boolean force){
		if(force || p.getHealth() < this.half){
			p.setHealth(this.half);
		}else{
			p.setHealth(p.getHealth() + this.half);
		}
	}
}
