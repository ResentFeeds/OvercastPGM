package overcast.pgm.module.modules.itemremove;

import java.util.List;

import org.bukkit.Material;

import overcast.pgm.builder.Builder;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.Module;

public class ItemRemoveModule extends Module {

	private List<Material> removed;
	
	public ItemRemoveModule(List<Material> removed){
		this.removed = removed;
	}
	
	
	
	@Override
	public MatchModule createMatchModule(Match match) {
	    return new ItemRemoveMatchModule(match, this.removed);
	}
	
	@Override
	public Class<? extends Builder> builder() {
		return ItemRemoveBuilder.class;
	}
	
	
	
	public List<Material> getMaterials(){
		return this.removed;
	}

}
