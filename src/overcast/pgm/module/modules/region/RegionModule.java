package overcast.pgm.module.modules.region;

import overcast.pgm.builder.Builder;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleInfo;

@ModuleInfo(name = "regions")
public class RegionModule extends Module {
 
	 private RFAContext context;

	public RegionModule(RFAContext context) { 
		  this.context = context;
	}
	
	public RFAContext getRFAContext(){
		return this.context;
	}
	
	@Override
	public MatchModule createMatchModule(Match match) {
		return new RegionMatchModule(match, this.context);
	}

	@Override
	public Class<? extends Builder> builder() {
		return RegionBuilder.class;
	} 
}
