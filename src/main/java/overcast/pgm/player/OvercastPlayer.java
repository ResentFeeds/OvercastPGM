package overcast.pgm.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.potion.PotionEffect;

import overcast.pgm.OvercastPGM;
import overcast.pgm.channels.Channel;
import overcast.pgm.channels.ChannelFactory;
import overcast.pgm.match.Match;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.module.modules.team.TeamManager;
import overcast.pgm.module.modules.tutorial.TutorialManager;
import overcast.pgm.module.modules.tutorial.TutorialModule;
import overcast.pgm.module.modules.tutorial.TutorialStage;
import overcast.pgm.util.TeamUtil;

public class OvercastPlayer {

	private Player player;

	private String nickname;

	private String name;

	private Channel channel;

	private PlayerInventory inventory;

	private TutorialManager tutManager;

	private TutorialStage stage;

	static List<OvercastPlayer> players = new ArrayList<>();

	public HashMap<Integer, ItemStack> items;

	public HashMap<Integer, ItemStack> hotbar;

	public OvercastPlayer(Player player) {
		this.player = player;
		this.name = this.player.getName();
		this.tutManager = new TutorialManager(OvercastPGM.getInstance().getMatch());
		this.channel = ChannelFactory.getChannel(this);
		this.inventory = this.player.getInventory();

		this.items = new HashMap<>();
		this.hotbar = new HashMap<>();
	}

	public boolean hasPotionEffects() {
		return this.player.getActivePotionEffects().size() > 0;
	}

	public void add() {
		players.add(this);
	}

	public void remove() {
		players.remove(this);
	}

	public static OvercastPlayer getPlayers(Player player) {
		for (OvercastPlayer players : getPlayers()) {
			if (players.getPlayer().equals(player)) {
				return players;
			}
		}

		return null;
	}

	public void closeInventory() {
		this.player.closeInventory();
	}

	public static OvercastPlayer getPlayers(String name) {
		for (OvercastPlayer players : getPlayers()) {
			if (players.getName().equals(name)) {
				return players;
			}
		}
		return null;
	}

	public static OvercastPlayer getPlayer(String string) {
		for (OvercastPlayer players : getPlayers()) {
			if (players.getName().equals(string)) {
				return players;
			}
		}

		return null;
	}

	public Player getPlayer() {
		return this.player;
	}

	public static List<OvercastPlayer> getPlayers() {
		return players;
	}

	public Team getTeam() {
		Team team = TeamManager.getTeam(this);
		return team != null ? team : null;
	}

	public boolean isObserver() {
		if (getTeam().equals(TeamUtil.getTeamModule().getObservers())) {
			return true;
		}
		return false;
	}

	public boolean hasTeam() {
		return getTeam() != null;
	}

	public boolean hasNickname() {
		return this.nickname != null;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname, String playerlistName) {
		this.nickname = nickname;
		this.player.setPlayerListName(playerlistName);
	}

	public String getName() {
		return this.name;
	}

	public void sendMessage(String message) {
		this.player.sendMessage(message);
	}

	public boolean isTutorialer() {
		return this.tutManager.hasTutorialer(this) ? true : false;
	}

	public Channel getChannel() {
		return this.channel;
	}

	public boolean hasChannel() {
		return this.getChannel() != null;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public boolean hasPermssion(Permission permssion) {
		return this.player.hasPermission(permssion);
	}

	public boolean isOperator() {
		return this.player.isOp();
	}

	public void setBoots(ItemStack stack) {
		player.getInventory().setBoots(stack);
	}

	public void setHelmet(ItemStack stack) {
		player.getInventory().setHelmet(stack);
	}

	public void setLeggings(ItemStack stack) {
		player.getInventory().setLeggings(stack);
	}

	public void setChestplate(ItemStack stack) {
		player.getInventory().setChestplate(stack);
	}

	public PlayerInventory getInventory() {
		return this.inventory;
	}

	public void addPotionEffect(PotionEffect effect) {
		player.addPotionEffect(effect);
	}

	public void setHealth(double d) {
		player.setHealth(d);
	}

	public double getHealth() {
		return player.getHealth();
	}

	public float getSaturation() {
		return player.getSaturation();
	}

	public boolean setTutorialStage(TutorialStage newStage) {
		TutorialModule module = OvercastPGM.getInstance().getMatch().getModules().getModule(TutorialModule.class);
		if (module == null || !this.isObserver())
			return false;

		long now = System.currentTimeMillis();

		// if((this.tutorialStageChangeTime + 500) > now) return false;

		if (this.stage == null && newStage != null) {
			//
		}

		this.stage = newStage;
		// this.tutorialStageChangeTime = now;

		if (newStage != null) {
			// update tutorial item
			PlayerInventory inv = getPlayer().getInventory();
			for (int i = 0; i < 9; i++) {
				ItemStack item = inv.getItem(i);
				if (item != null && item.getType() == TutorialModule.TUTORIAL_ITEM) {
					inv.setHeldItemSlot(i);
					inv.setItem(i, module.createItem(newStage));
				}
			}
			newStage.execute(this.getPlayer());
		}

		return true;
	}

	public void setSaturation(Float saturation) {
		player.setSaturation(saturation);
	}

	public void setHungerlevel(Integer hungerlevel) {
		player.setFoodLevel(hungerlevel);
	}

	public int getHungerLevel() {
		return player.getFoodLevel();
	}

	public TutorialManager getTutorialManger() {
		return this.tutManager;
	}

	public TutorialStage getTutorialStage() {
		return this.stage;
	}

	public ItemStack getItem() {
		Match match = OvercastPGM.getInstance().getMatch();
		TutorialModule tutorial = match.getModules().getModule(TutorialModule.class);

		return tutorial.createItem(null);
	}

	// work on potions and auto updating when a player views another players
	// inventory */

	// testing
	public void viewInventory(OvercastPlayer p) {
		Inventory inv = Bukkit.createInventory(this.player, this.inventory.getSize() + (9),
				getTeam().getColor() + this.getName());

		for (int i = 0; i < this.inventory.getSize(); i++) {
			ItemStack stack = this.inventory.getItem(i);

			if (stack != null) {
				if (i != 0 && i > 8) {
					this.items.put(i, stack);
				} else {
					int newslot = (i + 36);
					this.hotbar.put(newslot, stack);
				}
			}
		}

		for (Entry<Integer, ItemStack> entry : this.items.entrySet()) {
			inv.setItem(entry.getKey(), entry.getValue());
		}
		for (Entry<Integer, ItemStack> entry : this.hotbar.entrySet()) {

			inv.setItem(entry.getKey(), entry.getValue());
		}

		ItemStack[] armorC = this.inventory.getArmorContents();
		int i = 3;
		for (ItemStack armor : armorC) {
			if (armor != null) {
				inv.setItem(i, armor);
				i--;
			}
		}

		// potions (stil working)

		ItemStack potions = new ItemStack(Material.GLASS_BOTTLE);
		ItemMeta potionsMeta = potions.getItemMeta();
		potionsMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.ITALIC + "Potion Effects");
		List<String> effects = new ArrayList<>();
		if (this.hasPotionEffects()) {
			for (PotionEffect effect : this.player.getActivePotionEffects()) {
				String name = effect.getType().getName().replaceAll("_", " ").toLowerCase();

				int amplifier = effect.getAmplifier();
				String outcome = ChatColor.YELLOW + name + " " + amplifier;
				effects.add(outcome);
			}
		}

		if (effects.size() != 0) {
			potionsMeta.setLore(effects);
		} else {
			potionsMeta.setLore(Arrays.asList(ChatColor.YELLOW + "No potion effects"));
		}
		potions.setItemMeta(potionsMeta);

		// health (need to test)
		ItemStack healthStack = new ItemStack(Material.REDSTONE);
		healthStack.setAmount((int) getHealth());
		ItemMeta healthMeta = healthStack.getItemMeta();
		healthMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.ITALIC + "Health Level");
		healthStack.setItemMeta(healthMeta);

		// hunger (need to test)
		ItemStack hungerStack = new ItemStack(Material.COOKED_BEEF);
		hungerStack.setAmount(this.getHungerLevel());
		ItemMeta hungerMeta = hungerStack.getItemMeta();
		hungerMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.ITALIC + "Hunger Level");
		hungerStack.setItemMeta(hungerMeta);

		inv.setItem(6, potions);
		inv.setItem(7, hungerStack);
		inv.setItem(8, healthStack);
		p.openInventory(inv); 
	}

	public void updateInventory() {
		this.player.updateInventory();
	}

	public void teleport(Location loc) {
		this.player.teleport(loc);
	}

	public void setWalkSpeed(float speed) {
		this.player.setWalkSpeed(speed);
	}

	public void openInventory(Inventory inv) {
		this.player.openInventory(inv);
	}

	public Location getLocation() {
		return this.player.getLocation();
	}

	public InventoryView getOpenInventory() {
		return this.player.getOpenInventory();
	}
}
