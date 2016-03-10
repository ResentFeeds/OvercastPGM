package overcast.pgm.module.modules.region;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;

public class RFAContext {
     
	/** RFAContext */
	
	

    protected final ArrayListMultimap<RFAScope, RegionFilterApplication> rfas = ArrayListMultimap.create();
    protected final List<RegionFilterApplication> byPriority = new ArrayList<>(); 
	
	public RFAContext(){  
	}  
	
	
	public void addRFA(RegionFilterApplication rfa){
		this.rfas.put(rfa.getScope(), rfa);
		this.byPriority.add(rfa);
	}


	public ArrayListMultimap<RFAScope, RegionFilterApplication> getRfas() {
		return this.rfas;
	}


	public List<RegionFilterApplication> getByPriority() {
		return this.byPriority;
	} 
}
