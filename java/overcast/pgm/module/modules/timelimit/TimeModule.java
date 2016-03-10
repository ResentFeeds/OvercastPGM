package overcast.pgm.module.modules.timelimit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import overcast.pgm.OvercastPGM;
import overcast.pgm.builder.Builder;
import overcast.pgm.module.TaskedModule;

public class TimeModule extends TaskedModule{ 
	 
	private int time;
	private int count;

	public TimeModule(int time){
		this.time = time;  
	}
	 

	@Override
	public void run(){ 
		if(this.time != 0){   
			this.time--; 
			if (this.time % 5 == 0 && this.time > 5 || this.time <= 5 && this.time != 0) {
				Bukkit.getServer().broadcastMessage(status(this.time));
			}
		}else{
			Bukkit.getScheduler().cancelTask(this.count);
		}
	} 
	



	//TODO make this better ;)
	@Override
	public String status(int amount) {
		return ChatColor.LIGHT_PURPLE + "[" + amount + "]";
	} 

	@Override
	public Class<? extends Builder> builder() {
	    return TimeBuilder.class;
	} 
	
	
	public int getCount(){
		return this.count;
	}
	
	
	
	public int getTime(){
		return this.time;
	}


	@Override
	public void create() { 
		this.count = Bukkit.getScheduler().scheduleAsyncRepeatingTask(OvercastPGM.getInstance(), this, 0, 20);
	} 
}
