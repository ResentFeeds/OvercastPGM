package overcast.pgm.module.modules.tutorial;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import overcast.pgm.builder.Builder;
import overcast.pgm.match.Match;
import overcast.pgm.module.MatchModule;
import overcast.pgm.module.Module;

public class TutorialModule extends Module {

	// NOT FINISHED
	public static final Material TUTORIAL_ITEM = Material.EMERALD;
	private List<TutorialStage> stages;

	public TutorialModule(List<TutorialStage> stages) {
		this.stages = stages;
	}

	@Override
	public Class<? extends Builder> builder() {
		return TutorialBuilder.class;
	}

	@Override
	public MatchModule createMatchModule(Match match) {
		return new TutorialMatchModule(match, this.stages);
	}

	public List<TutorialStage> getStages() {
		return this.stages;
	}

	public boolean hasStages() {
		return this.getStages().size() > 0 || this.getStages() != null;
	}

	public TutorialStage getNextStage(TutorialStage stage) {
		return this.getStage(stage, 1);
	}

	public TutorialStage getPreviousStage(TutorialStage stage) {
		return this.getStage(stage, -1);
	}

	public ItemStack createItem(TutorialStage stage) {
		ItemStack item = new ItemStack(TUTORIAL_ITEM);
		ItemMeta meta = item.getItemMeta();

		if (stage == null) {
			meta.setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "View Tutorial");
		} else {
			TutorialStage next = this.getNextStage(stage);
			TutorialStage prev = this.getPreviousStage(stage);

			StringBuilder sb = new StringBuilder();
			if (prev != null) {
				sb.append(ChatColor.DARK_GRAY).append("Left Click").append(ChatColor.AQUA).append(" \u00AB ")
						.append(ChatColor.RED).append(prev.getTitle());
			}
			if (prev != null && next != null) {
				sb.append(ChatColor.AQUA).append(" \u23A5 ");
			}
			if (next != null) {
				sb.append(ChatColor.GREEN).append(next.getTitle()).append(ChatColor.AQUA).append(" \u00BB ")
						.append(ChatColor.DARK_GRAY).append("Right Click");
			}

			meta.setDisplayName(sb.toString());
		}

		item.setItemMeta(meta);
		return item;
	}

	public TutorialStage getStage(TutorialStage start, int offset) {
		int curIndex = start != null ? this.stages.indexOf(start) : -1;
		int nextIndex = curIndex + offset;

		if (nextIndex >= 0 && nextIndex < this.stages.size()) {
			return this.stages.get(nextIndex);
		} else {
			return null;
		}
	}

}
