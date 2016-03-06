package overcast.pgm.module.modules.team;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Color;

public class Team {

	/** team attributes */
	private String id;
	private String name;
	private ChatColor color;
	private int max;
	private int overfill;

	private boolean plural;

	/** members */
	private List<UUID> members;

	public Team(String id, String name, ChatColor color, int max, int overfill, boolean plural) {
		this.members = new ArrayList<>();
		this.id = id;
		this.name = name;
		this.color = color;
		this.max = max;
		this.overfill = overfill;
	}

	public Team(String id, String name, ChatColor color, int max, boolean plural) {
		this(id, name, color, max, (int) (max * 0.25), plural);
	}

	public void addMember(UUID uuid) {

		this.members.add(uuid);
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(ChatColor color) {
		this.color = color;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(int max) {
		this.max = max;
	}

	/**
	 * @param overfill the overfill to set
	 */
	public void setOverfill(int overfill) {
		this.overfill = overfill;
	}

	/**
	 * @param plural the plural to set
	 */
	public void setPlural(boolean plural) {
		this.plural = plural;
	}

	public String getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public ChatColor getColor() {
		return this.color;
	}

	public int getMax() {
		return this.max;
	}

	public int getMaxOverfill() {
		return this.overfill;
	}

	public List<UUID> getMembers() {
		return this.members;
	}

	public boolean isPlural() {
		return this.plural;
	}

	public boolean isMember(UUID uniqueId) {
		return this.getMembers().contains(uniqueId);
	}

	public void removePlayer(UUID uniqueId) {
		this.getMembers().remove(uniqueId);
	}

	public Color chatColorToDyeColor() {
		switch (color) {
		case AQUA:
			return Color.AQUA;
		case BLACK:
			return Color.BLACK;
		case BLUE:
			return Color.BLUE;
		case DARK_AQUA:
			return Color.NAVY;
		case DARK_BLUE:
			return Color.BLUE;
		case DARK_GRAY:
			return Color.GRAY;
		case DARK_GREEN:
			return Color.GREEN;
		case DARK_PURPLE:
			return Color.PURPLE;
		case DARK_RED:
			return Color.RED;
		case GOLD:
			return Color.ORANGE;
		case GRAY:
			return Color.SILVER;
		case GREEN:
			return Color.LIME;
		case LIGHT_PURPLE:
			break;
		case RED:
			return Color.RED;
		case YELLOW:
			return Color.YELLOW;
		default:
			return Color.WHITE;
		}
		return null;
	}
}
