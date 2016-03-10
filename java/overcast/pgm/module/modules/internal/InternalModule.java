package overcast.pgm.module.modules.internal;

import overcast.pgm.builder.Builder;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleInfo;

@ModuleInfo(name = "internal", desc = "Prevent compass teleports above Y 255")
public class InternalModule extends Module {

	// Not done with this yet

	private boolean internal;


	public InternalModule(boolean internal) {
		this.internal = internal;	}


	@Override
	public Class<? extends Builder> builder() {
		return InternalBuilder.class;
	}

	public boolean isInternal() {
		return this.internal;
	}
	
	
	@Override
	public MatchModule createMatchModule(Match match) {
	    return new InternalMatchModule(match, this.internal);
	}

	@Override
	public String toString() {
		return "InternalModule [internal=" + internal + "]";
	}
}
