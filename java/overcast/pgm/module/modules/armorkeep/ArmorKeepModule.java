package overcast.pgm.module.modules.armorkeep;

import java.util.List;

import org.bukkit.Material;

import overcast.pgm.builder.Builder;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.Module;

public class ArmorKeepModule extends Module {

	private List<Material> kept;

	public ArmorKeepModule(List<Material> kept) {
		this.kept = kept;
	}

	public List<Material> getMaterials() {
		return this.kept;
	}
	
	
	@Override
	public MatchModule createMatchModule(Match match) { 
		return new ArmorKeepMatchModule(match, this.kept);
	} 

	@Override
	public Class<? extends Builder> builder() {
		return ArmorKeepBuilder.class;
	}
}
