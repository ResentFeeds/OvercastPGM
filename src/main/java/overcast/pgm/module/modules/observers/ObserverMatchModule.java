package overcast.pgm.module.modules.observers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupExperienceEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.module.modules.team.TeamManager;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.TeamPicker;
import overcast.pgm.util.TeamUtil;

public class ObserverMatchModule extends MatchModule implements Listener {

	public ObserverMatchModule(Match match) {
		super(match);
	}

	@Override
	public void load() {
		this.match.registerEvents(this);
	}

	@Override
	public void unload() {
		// nothing
	}

	@Override
	public void enable() {
		this.match.registerEvents(this);
	}

	@Override
	public void disable() {
		// nothing
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		OvercastPlayer player = OvercastPlayer.getPlayers(event.getPlayer());
		if (player.isObserver()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		OvercastPlayer player = OvercastPlayer.getPlayers(event.getPlayer());

		if (player.isObserver()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();

		if (entity instanceof Player) {
			Player victim = (Player) entity;
			OvercastPlayer player = OvercastPlayer.getPlayers(victim);

			if (player.isObserver()) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		OvercastPlayer player = OvercastPlayer.getPlayers(event.getPlayer());

		if (player.isObserver()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBucketFill(PlayerBucketFillEvent event) {
		OvercastPlayer player = OvercastPlayer.getPlayers(event.getPlayer());

		if (player.isObserver()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBucket(PlayerBucketEmptyEvent event) {
		OvercastPlayer player = OvercastPlayer.getPlayers(event.getPlayer());

		if (player.isObserver()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event) {
		OvercastPlayer player = OvercastPlayer.getPlayers(event.getPlayer());

		if (player.isObserver()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onXpPickup(PlayerPickupExperienceEvent event) {
		OvercastPlayer player = OvercastPlayer.getPlayers(event.getPlayer());
		if (player.isObserver()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBottleThrow(PlayerInteractEvent event) {
		OvercastPlayer player = OvercastPlayer.getPlayers(event.getPlayer());
		Action a = event.getAction();
		if (player.isObserver()) {
			ItemStack is = player.getPlayer().getItemInHand();

			if (is.getType().equals(Material.LEATHER_HELMET) && is.hasItemMeta()) {
				if (a == Action.LEFT_CLICK_AIR || a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK
						|| a == Action.LEFT_CLICK_BLOCK && is.getItemMeta().getDisplayName().equals("Team Selection")) {
					event.setCancelled(true);
					TeamPicker picker = new TeamPicker(this.match);
					Inventory inventory = picker.teamPickerView();
					player.getPlayer().openInventory(inventory);
				}
			}

			if (is.getType() == Material.EXP_BOTTLE && a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
				event.setCancelled(true);
			}
		}
	}
	
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractAtEntityEvent event){
	    Player player = event.getPlayer();
	    OvercastPlayer p = OvercastPlayer.getPlayers(player);
	    if(event.getRightClicked() instanceof Player && p.isObserver()){
	    	Player clickedPlayer = (Player) event.getRightClicked();
	    	OvercastPlayer clickedP = OvercastPlayer.getPlayers(clickedPlayer); 
	    	Team team = clickedP.getTeam();
	    	if(team != TeamUtil.getTeamModule().getObservers()){
	    		 clickedP.viewInventory(p);
	    	}
	    }
	}

	@EventHandler
	public void onProjectile(ProjectileLaunchEvent event) {
		/** TODO */
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			OvercastPlayer p = OvercastPlayer.getPlayers(player);

			if (p.isObserver()) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void invclick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player p = (Player) event.getWhoClicked();
			OvercastPlayer player = OvercastPlayer.getPlayers(p);
			if (player.isObserver()) {
				if (ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Pick your team")) {
					if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
						return; // if it is nothing or air, stop the code here..

					} else if (event.getCurrentItem().getType().equals(Material.EYE_OF_ENDER)) {
						event.setCancelled(true);
						p.closeInventory();
					} else if (event.getCurrentItem().getType().equals(Material.LEATHER_HELMET)) {
						LeatherArmorMeta lm = (LeatherArmorMeta) event.getCurrentItem().getItemMeta();
						String name = ChatColor.stripColor(lm.getDisplayName());
                        event.setCancelled(true);
						Team team = TeamUtil.getTeam(name);
						int members = team.getMembers().size();
						int max = team.getMax();
						if (team != null) {
						   if(!(members > max))
							TeamManager.addPlayer(team, player);
							p.closeInventory();
						}
					}
				}
			}
		}
	}
}
