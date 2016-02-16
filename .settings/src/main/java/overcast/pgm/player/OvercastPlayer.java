package overcast.pgm.player;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
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

	public OvercastPlayer(Player player) {
		this.player = player;
		this.name = this.player.getName();
		this.tutManager = new TutorialManager(OvercastPGM.getInstance().getMatch());
		this.channel = ChannelFactory.getChannel(this);
		this.inventory = this.player.getInventory();
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

		return tutorial.createItem(getTutorialStage());
	}
}
