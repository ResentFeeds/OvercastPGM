package overcast.pgm.module.modules.kits;

import java.util.List;

import javax.annotation.Nullable;

import overcast.pgm.builder.Builder;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleInfo;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.KitUtils;

@ModuleInfo(name = "kits")
public class KitModule extends Module {

	private String id;
	private boolean force;
	private boolean clear;
	private boolean clearItems;
	private List<String> parents;
	private List<ItemKit> items;
	private List<ArmorKit> armor;
	private List<PotionKit> potions;
	// fix these
	private HealthKit health;
	private HungerKit hunger;
	private GamemodeKit gamemode;
	private float walkspeed;

	public KitModule(String id, boolean force, boolean clear, boolean clearItems, List<String> parents,
			List<ItemKit> items, List<ArmorKit> armor, List<PotionKit> potions, @Nullable HealthKit health,
			@Nullable HungerKit hunger, GamemodeKit gamemode, float walkspeed) {
		this.id = id;
		this.force = force;
		this.clear = clear;
		this.clearItems = clearItems;
		this.parents = parents;
		this.items = items;
		this.armor = armor;
		this.potions = potions;
		this.health = health;
		this.hunger = hunger;
		this.gamemode = gamemode;
		this.walkspeed = walkspeed;
	}

	@Override
	public Class<? extends Builder> builder() {
		return KitBuilder.class;
	}

	@Override
	public MatchModule createMatchModule(Match match) {
		return new KitMatchModule(match, this.armor);
	}

	public String getID() {
		return this.id;
	}

	public List<ItemKit> getItems() {
		return this.items;
	}

	public List<ArmorKit> getArmor() {
		return this.armor;
	}

	public boolean isForced() {
		return this.force;
	}

	public boolean isClearable() {
		return this.clear;
	}

	public boolean clearItems() {
		return this.clearItems;
	}

	public List<String> getParents() {
		return this.parents;
	}

	public boolean hasHealthKit() {
		return this.getHealthKit() != null;
	}

	public boolean hasHungerKit() {
		return this.getHungerKit() != null;
	}

	public HungerKit getHungerKit() {
		return this.hunger;
	}

	public GamemodeKit getGamemodeKit() {
		return this.gamemode;
	}

	public HealthKit getHealthKit() {
		return this.health;
	}

	public boolean hasPotions() {
		return this.getPotions() != null;
	}

	public List<PotionKit> getPotions() {
		return this.potions;
	}

	public boolean hasParents() {
		return this.getParents() != null;
	}

	public void applyKit(OvercastPlayer p) {

		GamemodeKit gamemode = this.getGamemodeKit();
		gamemode.apply(p);

		if (clear) {
			p.getInventory().setArmorContents(null);
		}

		if (clear || (clearItems)) {
			p.getInventory().clear();
		}

		if (hasParents()) {
			for (String parent : getParents()) {
				KitModule parentkit = KitUtils.getKit(parent);
				parentkit.applyKit(p);
			}
		}

		if (hasHealthKit()) {
			HealthKit health = this.getHealthKit();
			health.apply(p, this.force);
		}

		HungerKit hunger = this.getHungerKit();

		hunger.apply(p, this.force);

		p.setWalkSpeed(this.walkspeed != 0.2f ? this.walkspeed / 5 : 0.2f);

		if (hasPotions()) {
			for (PotionKit potion : this.getPotions()) {
				potion.apply(p, this.force);
			}
		}

		for (ItemKit items : getItems()) {
			if (items != null) {
				items.apply(p, this.force);
			}
		}

		for (ArmorKit armor : getArmor()) {
			switch (armor.getArmorType()) {
			case HELMET:
				p.getInventory().setHelmet(armor.getStack());
				break;
			case CHESTPLATE:
				p.getInventory().setChestplate(armor.getStack());
				break;
			case LEGGINGS:
				p.getInventory().setLeggings(armor.getStack());
				break;
			case BOOTS:
				p.getInventory().setBoots(armor.getStack());
				break;
			}
		}

	}
}
