package overcast.pgm.module.modules.maxheight;

import overcast.pgm.builder.Builder;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleInfo;

@ModuleInfo(name = "maxheight", desc = "prevents from building to a defined height limit")
public class MaxHeightModule extends Module {

	public int height;

	public MaxHeightModule(int height) {
		this.height = height;
	}

	@Override
	public Class<? extends Builder> builder() {
		return MaxBuildHeightBuilder.class;
	}

	@Override
	public MatchModule createMatchModule(Match match) {
		return new MaxBuildHeightMatchModule(match, this.height);
	}

	public int getHeight() {
		return this.height;
	}
}
