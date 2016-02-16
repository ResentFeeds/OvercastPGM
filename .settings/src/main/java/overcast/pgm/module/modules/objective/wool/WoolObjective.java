package overcast.pgm.module.modules.objective.wool;

import java.util.HashSet;

import java.util.Set;

import org.bukkit.DyeColor;

import overcast.pgm.builder.Builder;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.ModuleInfo;
import overcast.pgm.module.modules.objective.ObjectiveModule;
import overcast.pgm.module.modules.region.types.BlockRegion;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.player.OvercastPlayer;


@ModuleInfo(name = "wool", desc = "capture the wool") 
public class WoolObjective extends ObjectiveModule {

	private Team team;
	private DyeColor color;

	private Set<OvercastPlayer> touched;
	private BlockRegion block;

	private OvercastPlayer placer = null;
	private String name;

	public WoolObjective(String id, Team team, DyeColor color, BlockRegion block, String name) {
		super(id);
		this.team = team;
		this.color = color;
		this.block = block;
		this.name = name;

		this.touched = new HashSet<>();
	}
	
	@Override
	public MatchModule createMatchModule(Match match) { 
		return new WoolMatchModule(match, this);
	}

	@Override
	public Class<? extends Builder> builder() {
		return WoolBuilder.class;
	}

	public Team getTeam() {
		return this.team;
	}

	public DyeColor getColor() {
		return this.color;
	}

	public Set<OvercastPlayer> getTouchedPlayers() {
		return this.touched;
	}

	public BlockRegion getMonumentRegion() {
		return this.block;
	}

	public void setPlacer(OvercastPlayer player) {
		this.placer = player;
	}

	public OvercastPlayer getPlacer() {
		return this.placer;
	}
	
	public String getMonumentName(){
		return this.name;
	}
}
