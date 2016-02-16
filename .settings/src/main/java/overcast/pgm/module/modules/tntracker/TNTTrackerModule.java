package overcast.pgm.module.modules.tntracker;

import overcast.pgm.builder.Builder;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleInfo;

@ModuleInfo(name = "tnt", desc= "tracks who placed a block of TNT")
public class TNTTrackerModule extends Module{

	
	public TNTTrackerModule(){
		
	}
	
	@Override
	public MatchModule createMatchModule(Match match) {
		return new TNTTrackerMatchModule(match);
	}
	

	@Override
	public Class<? extends Builder> builder() {
		return TNTTrackerBuilder.class;
	}
}
