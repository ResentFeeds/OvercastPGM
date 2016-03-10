package overcast.pgm.module.modules.filter.types;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import overcast.pgm.module.modules.filter.FilterState;

public class HoldingFilter extends AbstractSingleFilter {

	private Material mat;
	
	public HoldingFilter(Material mat){
		this.mat = mat;
	}
	@Override
	public FilterState query(Object obj) {
		if(obj instanceof Player){
			Player player = (Player) obj;
			
			if(player != null){
				if(player.getItemInHand().getType().equals(this.mat)){
					return FilterState.ALLOW;
				}else{
					return FilterState.DENY;
				}
			}
		}
		
		
		return FilterState.ABSTAIN;
	}

	public Material getMaterial(){
		return this.mat;
	}
}
