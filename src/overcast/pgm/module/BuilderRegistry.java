package overcast.pgm.module;

import java.util.ArrayList;
import java.util.List;

import overcast.pgm.builder.Builder;
import overcast.pgm.module.modules.armorkeep.ArmorKeepBuilder;
import overcast.pgm.module.modules.broadcast.BroadcastBuilder;
import overcast.pgm.module.modules.damage.DisableDamageBuilder;
import overcast.pgm.module.modules.filter.FilterBuilder; 
import overcast.pgm.module.modules.internal.InternalBuilder;
import overcast.pgm.module.modules.itemkeep.ItemKeepBuilder;
import overcast.pgm.module.modules.itemremove.ItemRemoveBuilder;
import overcast.pgm.module.modules.killreward.KillRewardBuilder;
import overcast.pgm.module.modules.kits.KitBuilder;
import overcast.pgm.module.modules.maxheight.MaxBuildHeightBuilder;
import overcast.pgm.module.modules.objective.wool.WoolBuilder;
import overcast.pgm.module.modules.observers.ObserverBuilder;
import overcast.pgm.module.modules.projectiles.bow.BowProjectileBuilder;
import overcast.pgm.module.modules.projectiles.custom.ProjectileBuilder;
import overcast.pgm.module.modules.region.RegionBuilder;
import overcast.pgm.module.modules.spawn.SpawnBuilder;
import overcast.pgm.module.modules.team.TeamBuilder;
import overcast.pgm.module.modules.timelimit.TimeBuilder;
import overcast.pgm.module.modules.tntracker.TNTTrackerBuilder;
import overcast.pgm.module.modules.toolrepair.ToolRepairBuilder;
import overcast.pgm.module.modules.tutorial.TutorialBuilder;

public class BuilderRegistry {

	protected ModuleFactory factory;


	List<Class<? extends Builder>> builderClasses;
	 

	public BuilderRegistry() { 
		this.builderClasses = new ArrayList<>();
		
		loadBuilders();
	}    
	
	
	public void addBuilder(Class<? extends Builder> builder){
		 builderClasses.add(builder);
	}

	public void loadBuilders() { 
		addBuilder(FilterBuilder.class);
		addBuilder(DisableDamageBuilder.class);
		addBuilder(InternalBuilder.class);
		addBuilder(KitBuilder.class);
		addBuilder(MaxBuildHeightBuilder.class);
		addBuilder(ObserverBuilder.class);
		addBuilder(TeamBuilder.class);
		addBuilder(RegionBuilder.class);
		addBuilder(TNTTrackerBuilder.class);
		addBuilder(BowProjectileBuilder.class);
		addBuilder(TutorialBuilder.class); // need to work on 
		addBuilder(ItemRemoveBuilder.class);
		addBuilder(KillRewardBuilder.class);
		addBuilder(ArmorKeepBuilder.class);
		addBuilder(ItemKeepBuilder.class);
		addBuilder(KillRewardBuilder.class);
		addBuilder(ToolRepairBuilder.class);
		addBuilder(TimeBuilder.class);
		addBuilder(BroadcastBuilder.class); // need to work on
	    addBuilder(SpawnBuilder.class); // need to work on
	    addBuilder(ProjectileBuilder.class); // need to work on 
		addBuilder(WoolBuilder.class); // need to work on 
	}


	public List<Class<? extends Builder>> getBuilders(){
		return this.builderClasses;
	}
}