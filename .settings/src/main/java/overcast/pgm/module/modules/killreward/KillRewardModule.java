package overcast.pgm.module.modules.killreward;

import java.util.List;

import overcast.pgm.builder.Builder;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.Module;

public class KillRewardModule extends Module {

	private List<KillReward> rewards;


	public KillRewardModule(List<KillReward> rewards){
		this.rewards = rewards;
	}
	
	
	@Override
	public MatchModule createMatchModule(Match match) {
		return new KillRewardMatchModule(match, this.rewards);
	}
	

	@Override
	public Class<? extends Builder> builder() {
		return KillRewardBuilder.class;
	}

	
	
	public List<KillReward> getKillRewards(){
		return this.rewards;
	}
}
