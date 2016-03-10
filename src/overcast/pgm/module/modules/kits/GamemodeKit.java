package overcast.pgm.module.modules.kits;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import overcast.pgm.module.modules.kits.parsers.GamemodeKitParser;
import overcast.pgm.player.OvercastPlayer;

public class GamemodeKit {

	private GameMode gamemode;

	public GamemodeKit(GameMode gamemode) {
		this.gamemode = gamemode;
	}

	public GamemodeKit(GamemodeKitParser parser) {
		this(parser.getGameMode());
	}

	public GameMode getGameMode() {
		return this.gamemode;
	}

	public void apply(OvercastPlayer player) {
		Player p = player.getPlayer();
		p.setGameMode(gamemode);
	}
}
