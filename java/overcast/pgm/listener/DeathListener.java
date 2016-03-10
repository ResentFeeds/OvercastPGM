package overcast.pgm.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.sk89q.minecraft.util.commands.ChatColor;

import overcast.pgm.event.OvercastDeathEvent;
import overcast.pgm.map.Map;
import overcast.pgm.match.Match;
import overcast.pgm.match.MatchHandler;
import overcast.pgm.module.modules.spawn.Spawn;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.LocationUtils;

//FIXME make this work on for tnt and fall (with the amount of blocks) 
public class DeathListener implements Listener {

	@EventHandler
	public void onEntityDeath(PlayerDeathEvent event) {
		if (event.getEntity() instanceof Player) {
			OvercastPlayer killed = OvercastPlayer.getPlayers((Player) event.getEntity());
			OvercastPlayer killer = OvercastPlayer.getPlayers(killed.getPlayer().getKiller());
			ItemStack item = killer != null ? killer.getPlayer().getItemInHand() : null;
			OvercastDeathEvent deathEvent = new OvercastDeathEvent(killed, killer, event, item);
			Bukkit.getPluginManager().callEvent(deathEvent);
		}
	}

	//FIXME the weapon name is all caps ;/
	@EventHandler
	public void onOvercastDeath(OvercastDeathEvent event) {
		String deathMessage = "";
		if (event.getCause().equals(DamageCause.VOID)) {
			deathMessage = " fell out of the world ";
		} else if (event.getCause().equals(DamageCause.DROWNING)) {
			deathMessage = " has drowned to death ";
		} else if (event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
			String by = "";
			if (event.getKiller().getItem().getType() != Material.AIR) {
				by += " was slained by " + event.getKillerColoredName() + "'s" + event.getItemName();
			} else {
				by += event.getKillerName() + "'s" + ChatColor.WHITE + "fists";
			}

			deathMessage = by;
		}

		String message = event.getKilledColoredName() + ChatColor.WHITE + deathMessage;
		PlayerDeathEvent pD = event.getPlayerDeathEvent();

		pD.setDeathMessage(message);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		Entity damaged = event.getEntity();
		Entity damager = event.getDamager();

		if (damaged != null && damager != null) {
			if (damaged instanceof Player && damager instanceof Player) {
				OvercastPlayer damagedP = OvercastPlayer.getPlayers((Player) damaged);
				OvercastPlayer damagerP = OvercastPlayer.getPlayers((Player) damager);

				Team damagedTeam = damagedP.getTeam();
				Team damagerTeam = damagerP.getTeam();

				Match match = MatchHandler.getMatchHandler().getMatch();
				Map map = match.getMap();
				boolean friendlyFireEnabled = map.getInfo().isFriendlyFireEnabled();

				if (damagedTeam == damagerTeam) {
					event.setCancelled(!friendlyFireEnabled);
				}
			}
		}
	}
	
	
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event){ 
		 OvercastPlayer player = OvercastPlayer.getPlayers(event.getPlayer());
		 
		 Team team = player.getTeam();
		 Spawn spawn = LocationUtils.getSpawn(team);
		 
		 if(spawn != null){
			  boolean kit = spawn.getKit() != null;
			  if(kit){
				  spawn.getKit().applyKit(player);
			  }
			  spawn.teleport(player);
		 }
	}
}