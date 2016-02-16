package overcast.pgm.pagination.pages;


import org.bukkit.ChatColor;

import overcast.pgm.OvercastPGM;
import overcast.pgm.map.Map;
import overcast.pgm.pagination.PaginatedResult;

public class LoadedMapsPage<T> extends PaginatedResult<T> {

	@Override
	public String formatHeader(int page, int maxPages) {
		String header = header(page, maxPages);
		return header;
	}

	@Override
	public String format(T entry, int index) {
		Map map = OvercastPGM.getInstance().getLoader().getMap((String) entry);
		return (index + 1) + ". " + map.getShortDescription();
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
		String prefix = blue +  "" + strike +  "------------ ";
		String mid = white + " Loaded Maps " + dark_aqua + "(" + aqua + page
				+ dark_aqua + " of " + aqua + max + dark_aqua + ") ";
		String suffix = blue +  "" + strike +  "------------ ";
		String header = header(prefix, mid, suffix);
		return header;
	}
}
