package overcast.pgm.module.modules.tutorial;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import overcast.pgm.module.modules.region.types.PointProvider;
import overcast.pgm.module.modules.tutorial.parser.TutorialStageParser;
import overcast.pgm.util.LocationUtils;

public class TutorialStage {

	private String title;
	private List<String> message;
	private PointProvider teleport;

	public TutorialStage(String title, List<String> message, PointProvider teleport) {
		this.title = title;
		this.message = message;
		this.teleport = teleport;
	}

	public TutorialStage(TutorialStageParser parser) {
		this(parser.getTitle(), parser.getMessage(), parser.getPointProvider());
	}

	public String getTitle() {
		return this.title;
	}

	public List<String> getMessage() {
		return this.message;
	}

	public PointProvider getTeleport() {
		return this.teleport;
	}

	public void execute(Player player) { 

		this.sendMessage(player);

		if (this.teleport != null) {
			Location teleport = LocationUtils.convertVectorToLocation(getTeleport().getPoint());
			if (teleport != null) { 
				teleport.setYaw(getTeleport().getYaw());
				teleport.setPitch(getTeleport().getPitch()); 
				player.teleport(teleport);
				player.setFlying(true);
			} else {
				player.sendMessage("    " + ChatColor.YELLOW + ChatColor.BOLD
						+ "WARNING: Unable to safely teleport you to tutorial spot!");
			}
			player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 0.5f, 1.0f);
		} else {
			player.playSound(player.getLocation(), Sound.PISTON_EXTEND, 0.5f, 2.0f);
		}
	}  

	public void sendMessage(Player player) {
		player.sendMessage("");
		player.sendMessage("    " + ChatColor.YELLOW + ChatColor.BOLD + this.title);
		for (String message : this.message)
			player.sendMessage(message);
		player.sendMessage(""); // blank line to separate from other chat spam
	} 
}
