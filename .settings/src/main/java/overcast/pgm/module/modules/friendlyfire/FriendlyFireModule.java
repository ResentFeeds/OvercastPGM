package overcast.pgm.module.modules.friendlyfire;

import overcast.pgm.builder.Builder;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleInfo;
@ModuleInfo(name = "friendlyfire", desc = "lets you to kill other teammates")
public class FriendlyFireModule extends Module {


    boolean friendlyfire;
    boolean friendlyfirerefund;

	public FriendlyFireModule(boolean friendlyfire, boolean friendlyfirerefund){
		this.friendlyfire = friendlyfire;
		this.friendlyfirerefund = friendlyfirerefund;
	}
	
	@Override
	public Class<? extends Builder> builder() {
		return FriendlyFireBuilder.class;
	}
	
	public boolean isFriendlyFireEnabled(){
		return this.friendlyfire;
	}
	
	
	
	@Override
	public MatchModule createMatchModule(Match match) {
		return new FriendlyFireMatchModule(match, this.friendlyfire, this.friendlyfirerefund);
	}
	
	
	public boolean isFriendlyFireRefundEnabled(){
		return this.friendlyfirerefund;
	}
}
