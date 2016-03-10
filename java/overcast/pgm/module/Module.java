package overcast.pgm.module;

import overcast.pgm.builder.Builder;
import overcast.pgm.match.Match;

public abstract class Module {
	
	
	public ModuleAbout getAbout(){
		return new ModuleAbout(getClass());
	}
	
	public abstract Class<? extends Builder> builder();

	public boolean hasBuilder() {
		return this.builder() != null;
	}
	
	

	
	

    public MatchModule createMatchModule(Match match) { return null; }


}
