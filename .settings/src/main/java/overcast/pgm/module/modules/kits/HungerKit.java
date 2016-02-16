package overcast.pgm.module.modules.kits;

import overcast.pgm.player.OvercastPlayer;

public class HungerKit {

	private Float saturation;
	private Integer hungerlevel;

	public HungerKit(float saturation, int hungerlevel){
		 this.saturation = saturation;
		 this.hungerlevel = hungerlevel;
	} 
	
	public int getHungerlevel() {
		return hungerlevel;
	} 
	
	
	public float getSaturation(){
		return this.saturation;
	}
	
	public void setSaturation(float saturation){
		this.saturation = saturation;
	}

	public void setFoodLevel(int food) {
		 this.hungerlevel = food;
	}
	
	public void apply(OvercastPlayer p, boolean force){
		if(this.saturation != null && (force || p.getSaturation() < this.saturation)){
			p.setSaturation(this.saturation);
		}
		
		
		if(this.hungerlevel != null && (force || p.getHungerLevel() < this.hungerlevel)){
			p.setHungerlevel(this.hungerlevel);
		}
	}
}
