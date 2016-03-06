package overcast.pgm.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.StringUtils;

public class OvercastDeathEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private OvercastPlayer killed;
    private OvercastPlayer killer;

	private DamageCause cause;

	private PlayerDeathEvent event;

	private ItemStack item;
 

    public OvercastDeathEvent(OvercastPlayer killed, OvercastPlayer killer, PlayerDeathEvent event, ItemStack item) {
        this.killed = killed;
        this.killer = killer;
        this.event = event;
        this.item = item;
        this.cause = killed.getPlayer().getLastDamageCause().getCause();
    }
    
    
    public String getKillerName(){
    	return this.killer.hasNickname() ? killer.getNickname() : killer.getName();
    }
    
    
    public String getKilledName(){
    	return this.killed.hasNickname() ? killed.getNickname() : killed.getName();
    }
    
    
    public String getItemName(){ 
    	return this.item.hasItemMeta() ? this.item.getItemMeta().getDisplayName() : StringUtils.getName(this.item.getType().name());
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public OvercastPlayer getKilledPlayer(){
    	return this.killed;
    }

    public OvercastPlayer getKiller() {
        return killer;
    }  
    
    public PlayerDeathEvent getPlayerDeathEvent(){
    	return this.event;
    }
    
    public DamageCause getCause(){
    	return this.cause;
    }


	public String getKillerColoredName() {
		return this.killer.getTeam().getColor() + getKillerName();
	}
	
	
	public String getKilledColoredName() {
		return this.killed.getTeam().getColor() + getKillerName();
	}
}