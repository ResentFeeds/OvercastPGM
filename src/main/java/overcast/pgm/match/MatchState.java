package overcast.pgm.match;

import org.bukkit.ChatColor;

import overcast.pgm.util.Characters;

public enum MatchState {

	LOAD(ChatColor.GOLD), START(ChatColor.GREEN), RUNNING(ChatColor.YELLOW), CYCLE(
			ChatColor.RED), ENDED(ChatColor.DARK_RED);

	private ChatColor color;

	MatchState(ChatColor color) {
		this.color = color;
	}

	public static String toString(Match match) {
		MatchState state = match.getState();
		if (state != null) {
			ChatColor color = state.getColor();
			String name = match.getMap().getInfo().getName();

			String utf_raquo = Characters.Raquo.getUTF();
			
			String utf_laquo = Characters.Laquo.getUTF();
			
			return color + " " + utf_raquo + " " + ChatColor.WHITE
					+ name + color + " " + utf_laquo;
		}

		return null;
	}

	public ChatColor getColor() {
		return this.color;
	}
}
