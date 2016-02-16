package overcast.pgm.module.modules.observers;

import overcast.pgm.builder.Builder;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleInfo;
@ModuleInfo(name = "observers")
public class ObserverModule extends Module {

	public ObserverModule(){
		//nothing
	}

	@Override
	public Class<? extends Builder> builder() {
		return ObserverBuilder.class;
	}
	
	
	
	@Override
	public MatchModule createMatchModule(Match match) {
		return new ObserverMatchModule(match);
	}
	
}
