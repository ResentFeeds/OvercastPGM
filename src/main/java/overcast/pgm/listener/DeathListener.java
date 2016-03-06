package overcast.pgm.listener;
//TODO create this listener //

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.sk89q.minecraft.util.commands.ChatColor;

import overcast.pgm.event.OvercastDeathEvent;
import overcast.pgm.player.OvercastPlayer;

public class DeathListener implements Listener {

	@EventHandler
	public void onEntityDeath(PlayerDeathEvent event) {
		if (event.getEntity() instanceof Player) {
			OvercastPlayer killed = OvercastPlayer.getPlayers((Player) event.getEntity());
			OvercastPlayer killer = OvercastPlayer.getPlayers(killed.getPlayer().getKiller());
			ItemStack item = killer.getPlayer().getItemInHand();
			OvercastDeathEvent deathEvent = new OvercastDeathEvent(killed, killer, event, item);
			Bukkit.getPluginManager().callEvent(deathEvent);
		}
	}

	@EventHandler
	public void onOvercastDeath(OvercastDeathEvent event) {
		 String deathMessage = "";
		 if(event.getCause().equals(DamageCause.VOID)){
			 deathMessage = "fell out of the world ";
		 }else if(event.getCause().equals(DamageCause.DROWNING)){
			 deathMessage = "has drowned to death ";
		 }

		 String message = event.getKilledColoredName() + ChatColor.WHITE + deathMessage;
		 PlayerDeathEvent pD = event.getPlayerDeathEvent();
		 
		 pD.setDeathMessage(message);
	}
}