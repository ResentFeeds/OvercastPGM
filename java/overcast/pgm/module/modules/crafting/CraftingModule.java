package overcast.pgm.module.modules.crafting;

import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import overcast.pgm.builder.Builder;
import overcast.pgm.module.Module;
import overcast.pgm.module.modules.kits.ItemKit;

public class CraftingModule extends Module {

	private final Recipe recipe;
	private final boolean override;
	private final boolean override_all;
	private final ItemKit result;

	/** shapeless recipe */

	public CraftingModule(Recipe recipe, boolean override, boolean override_all, ItemKit result) {
		this.recipe = recipe;
		this.override = override;
		this.override_all = override_all;
		this.result = result;
	}

	public boolean isShaped() {
		if (this.recipe instanceof ShapedRecipe) {
			return true;
		}
		return false;
	}

	public boolean isShapeless() {
		if (this.recipe instanceof ShapelessRecipe) {
			return true;
		}
		return false;
	}

	public boolean isSmelt() {
		if (this.recipe instanceof FurnaceRecipe) {
			return true;
		}
		return false;
	}

	public FurnaceRecipe getFurnaceRecipe() {
		FurnaceRecipe furnace = (FurnaceRecipe) this.recipe; 
		return furnace;
	}

	public ShapelessRecipe getShapeless() {
		ShapelessRecipe shapeless = (ShapelessRecipe) this.recipe;
		return shapeless;
	}

	public ShapedRecipe getShaped() {
		ShapedRecipe shaped = (ShapedRecipe) this.recipe;
		return shaped;
	}

	@Override
	public Class<? extends Builder> builder() {
		return CraftingBuilder.class;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public boolean isOverride() {
		return override;
	}

	public boolean isOverride_all() {
		return override_all;
	}

	public ItemKit getResult() {
		return result;
	}
}
