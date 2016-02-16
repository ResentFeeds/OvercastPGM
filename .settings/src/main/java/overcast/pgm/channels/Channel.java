package overcast.pgm.channels;
import static org.bukkit.ChatColor.*;
import java.util.ArrayList;
import java.util.List;
import overcast.pgm.player.OvercastPlayer;

public abstract class Channel {

	List<OvercastPlayer> players;

	String name;

	boolean color = false;

	public Channel(String name) {
		this.name = name;
		this.players = new ArrayList<>();
	}

	public List<OvercastPlayer> getMembers() {
		return this.players;
	}

	public void addMember(OvercastPlayer overcast) {
		this.players.add(overcast);
		overcast.sendMessage(YELLOW + "Your channel is now " + this.getTechinalName());
	}
	
	public void removeMember(OvercastPlayer overcast){
		this.players.remove(overcast);
	}

	public String getName() {
		return this.name;
	}

	public String getTechinalName() {
		String[] string = new String[] { "Channel" };
		
		String result = null;
		
		for (String channel : string) {
			result = channel;
		}
		return getName() + " " + result;
	}

	public boolean hasColor() {
		return this.color;
	}

	public abstract String format(OvercastPlayer player, String message);

	public boolean hasPlayer(OvercastPlayer p) {
		return this.players.contains(p);
	}
}
