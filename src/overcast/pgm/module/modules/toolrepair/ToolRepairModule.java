package overcast.pgm.module.modules.toolrepair;

import java.util.List;

import org.bukkit.Material;

import overcast.pgm.builder.Builder;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.Module;

public class ToolRepairModule extends Module {

	private List<Material> tools;

	public ToolRepairModule(List<Material> tools) {
		this.tools = tools;
	}

	@Override
	public Class<? extends Builder> builder() {
		return ToolRepairBuilder.class;
	}

	@Override
	public MatchModule createMatchModule(Match match) {
		return new ToolRepairMatchModule(match, this.tools);
	}

	public List<Material> getTools() {
		return this.tools;
	}

}
