package overcast.pgm.module.modules.observers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
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
import overcast.pgm.util.Teleporter;

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
					player.openInventory(inventory);
				}
			}

			if (is.getType().equals(Material.EXP_BOTTLE) && a == Action.RIGHT_CLICK_AIR
					|| a == Action.RIGHT_CLICK_BLOCK) {
				event.setCancelled(true);
			}

			if (is.getType().equals(Material.SKULL_ITEM) && is.hasItemMeta()) {
				if (a == Action.LEFT_CLICK_AIR || a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK
						|| a == Action.LEFT_CLICK_BLOCK && is.getItemMeta().getDisplayName().equals("Teleporter")
								&& is.getData().getData() == (byte) 3) {
					event.setCancelled(true);
					Teleporter teleporter = new Teleporter(OvercastPlayer.getPlayers());
					teleporter.viewInventory(player);
				}
			}

			if (event.getClickedBlock() != null && !event.getPlayer().isSneaking()
					&& event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (event.getClickedBlock().getType().equals(Material.CHEST)
						|| event.getClickedBlock().getType().equals(Material.TRAPPED_CHEST)) {
					Inventory chest = Bukkit.createInventory(null,
							((Chest) event.getClickedBlock().getState()).getInventory().getSize());
					for (int i = 0; i < ((Chest) event.getClickedBlock().getState()).getInventory().getSize(); i++) {
						chest.setItem(i, ((Chest) event.getClickedBlock().getState()).getInventory().getItem(i));
					}
					event.setCancelled(true);
					player.openInventory(chest);
				}
			}

			if (event.getClickedBlock() != null && !event.getPlayer().isSneaking()
					&& event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (event.getClickedBlock().getType().equals(Material.FURNACE)
						|| event.getClickedBlock().getType().equals(Material.BURNING_FURNACE)) {
					Inventory furnace = Bukkit.createInventory(null, InventoryType.FURNACE);
					for (int i = 0; i < ((Furnace) event.getClickedBlock().getState()).getInventory().getSize(); i++) {
						furnace.setItem(i, ((Furnace) event.getClickedBlock().getState()).getInventory().getItem(i));
					}
					event.setCancelled(true);
					player.openInventory(furnace);
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		OvercastPlayer p = OvercastPlayer.getPlayers((Player) event.getPlayer());

		String name = ChatColor.stripColor(event.getInventory().getName());
		OvercastPlayer whos = OvercastPlayer.getPlayers(name);

		if (p != null && whos != null) {
			if (whos.hotbar.size() > 0) {
				whos.hotbar.clear();
			}

			if (whos.items.size() > 0) {
				whos.items.clear();
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractAtEntityEvent event) {
		Player player = event.getPlayer();
		OvercastPlayer p = OvercastPlayer.getPlayers(player);
		if (event.getRightClicked() instanceof Player && p.isObserver()) {
			Player clickedPlayer = (Player) event.getRightClicked();
			OvercastPlayer clickedP = OvercastPlayer.getPlayers(clickedPlayer);
			Team team = clickedP.getTeam();
			if (team == TeamUtil.getTeamModule().getObservers())
				return;

			clickedP.viewInventory(p);
		}
	}

	// test this tomorrow ;)
	@EventHandler
	public void onInventory(InventoryPickupItemEvent event) {
		Inventory inv = event.getInventory();

		String name = ChatColor.stripColor(inv.getName());
		ItemStack stack = event.getItem().getItemStack();
		for (OvercastPlayer overcast : OvercastPlayer.getPlayers()) {
			if (name.equals(overcast.getName())) {
				if (stack != null) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onProjectile(ProjectileLaunchEvent event) {
		if (event.getEntity().getShooter() instanceof HumanEntity) {
			HumanEntity human = (HumanEntity) event.getEntity().getShooter();

			if (human instanceof Player) {
				Player player = (Player) human;
				OvercastPlayer p = OvercastPlayer.getPlayers(player);
				if (p.isObserver()) {
					event.setCancelled(true);
				}
			}
		}
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
				// picker inventory
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
							if (!(members > max))
								TeamManager.addPlayer(team, player);
							p.closeInventory();
						}
					}
				}

				// teleporter inventory
				if (ChatColor.stripColor(event.getInventory().getName()).equals("Teleporter")) {
					if (event.getCurrentItem().getType().equals(Material.SKULL_ITEM)
							&& event.getCurrentItem().hasItemMeta()) {
						OvercastPlayer overcast = OvercastPlayer
								.getPlayer(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
						event.setCancelled(true);
						player.closeInventory();
						player.teleport(overcast.getLocation());
					}
				}

				// prevent a player from moving items from another players
				// inventory
				for (OvercastPlayer overcast : OvercastPlayer.getPlayers()) {
					if (overcast != null) {
						if (ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase(overcast.getName())) {
							event.setCancelled(true);
						}
					}
				}
				
				
				
				if(event.getInventory().getName().equals("Furnace") || event.getInventory().getName().equals("Chest")){
					if(event.getCurrentItem() != null){
						if(event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT){
							 event.setCancelled(true);
						}
					}
				}
			}
		}
	}
}
