package overcast.pgm.module.modules.objective.wool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import java.util.Set;

import org.bukkit.DyeColor;

import overcast.pgm.builder.Builder;
import overcast.pgm.match.Match;
import overcast.pgm.match.MatchHandler;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.ModuleInfo;
import overcast.pgm.module.modules.objective.ObjectiveModule;
import overcast.pgm.module.modules.region.types.BlockRegion;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.Characters;
import overcast.pgm.util.StringUtils;

@ModuleInfo(name = "wool", desc = "capture the wool")
public class WoolObjective extends ObjectiveModule {

	private Team team;
	private DyeColor color;

	private Set<OvercastPlayer> touched;
	private BlockRegion block;

	private OvercastPlayer placer = null;

	public WoolObjective(String id, boolean required, Team team, DyeColor color, BlockRegion block) {
		super(id, required, Characters.Cross);
		this.team = team;
		this.color = color;
		this.block = block;

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

	/**
	 * 
	 * @param team
	 * @return the teams objectives
	 */

	public static Collection<WoolObjective> getObjectives(Team team) {
		Collection<WoolObjective> teamWools = new ArrayList<>();
		ModuleCollection<WoolObjective> wools = MatchHandler.getMatchHandler().getMatch().getModules().getModules(WoolObjective.class);
		for (WoolObjective wool : wools) {
			if (wool != null) {
				if (wool.getTeam().getName().equals(team.getName())) {
					teamWools.add(wool);
				}
			}
		}
		return teamWools;
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

	public String getWoolName() {
		String prefix = StringUtils.getPrefix(this.color);
        
		return prefix + " wool";
	}
}
