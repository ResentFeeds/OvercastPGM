package overcast.pgm.module.modules.filter.types;

import org.bukkit.Material;

import overcast.pgm.module.modules.filter.FilterState;

public class MaterialFilter extends AbstractSingleFilter {

	private Material mat;

	public MaterialFilter(Material mat) {
		this.mat = mat;
	}
	
	
	public MaterialFilter(MaterialFilterParser parser){
		this(parser.getMaterial());
	}

	@Override
	public FilterState query(Object obj) {
		if (obj instanceof Material) {
			Material mat = (Material) obj;
			if (mat.equals(this.mat)) {
				return FilterState.ALLOW;
			} else {
				return FilterState.ABSTAIN;
			}
		}
		return FilterState.ABSTAIN;
	}

}
