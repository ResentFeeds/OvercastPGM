package overcast.pgm.pagination.pages;

import org.bukkit.ChatColor;

import overcast.pgm.OvercastPGM;
import overcast.pgm.map.Map;
import overcast.pgm.match.Match;
import overcast.pgm.pagination.PaginatedResult;

public class RotationMapsPage<T> extends PaginatedResult<T> {

	@Override
	public String formatHeader(int page, int maxPages) {
		String header = header(page, maxPages);
		return header;
	}

	@Override
	public String format(T entry, int index) {
		Match match = OvercastPGM.getInstance().getMatch();
		Map map = OvercastPGM.getInstance().getRotation().getMap((String) entry);
		int newID = index + 1;
		String format = null;
		if(match.getNext().equals(map)){
			format = ChatColor.DARK_AQUA + "" + newID + ". " + map.getShortDescription();
		} else {
			format = newID + ". " + map.getShortDescription();
		}

		String result = format;
		return result;
	}

	public String header(String prefix, String mid, String suffix) {
		StringBuilder builder = new StringBuilder();
		builder.append(prefix).append(mid).append(suffix);

		return builder.toString();
	}

	public String header(int page, int max) {
		ChatColor strike = ChatColor.STRIKETHROUGH;
		ChatColor blue = ChatColor.BLUE;
		ChatColor white = ChatColor.WHITE;
		ChatColor aqua = ChatColor.AQUA;
		ChatColor dark_aqua = ChatColor.DARK_AQUA;
		String prefix = blue + "" + strike + "------------ ";
		String mid = white + " Current Rotation " + dark_aqua + "(" + aqua
				+ page + dark_aqua + " of " + aqua + max + dark_aqua + ") ";
		String suffix = blue + "" + strike + "------------ ";
		String header = header(prefix, mid, suffix);
		return header;
	}

}
