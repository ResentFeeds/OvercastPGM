package overcast.pgm.module;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import overcast.pgm.builder.Builder;
import overcast.pgm.module.modules.filter.FilterContext;
import overcast.pgm.module.modules.region.RegionContext;

public class ModuleFactory {

	public List<Module> modules;
	public List<Builder> builders;
	public BuilderRegistry registry;
	public Document doc;

	/** filters and regions */

	private FilterContext filters;
	private RegionContext regions;

	public ModuleFactory(Document doc) {
		this.modules = new ArrayList<>();
		this.builders = new ArrayList<>();
		this.doc = doc;
		this.filters = new FilterContext(this);
		this.regions = new RegionContext();
		this.registry = new BuilderRegistry();

		for (Class<? extends Builder> builder : this.registry.getBuilders()) {
			try {
				Builder build = builder.newInstance();
				this.builders.add(build);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	// ORIGINAL
	public List<Module> build(Document doc, ModuleStage time) {
		List<Module> results = new ArrayList<>();
		for (Builder builder : builders) {
			try {
				if (builder.getAbout().getInfo().stage().equals(time)) {
					try {
						if (builder.build(doc) != null) {
							results.addAll(builder.build(doc));
						} else {
							 results.addAll(builder.build(doc, this));
						}
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
			} catch (NullPointerException e) {
				if (time != ModuleStage.NORMAL)
					;
				else
					try {
						if (builder.build(doc) != null) {
							results.addAll(builder.build(doc));
						} else {
							results.addAll(builder.build(doc, this));
						}
					} catch (Throwable t) {
						t.printStackTrace();
					}
			}
		}
		return results;
	}
	
	
	public boolean removeModule(Module module){
		return modules.remove(module);
	}

	public BuilderRegistry getRegistry() {
		return this.registry;
	}

	public Document getDocument() {
		return this.doc;
	}

	public RegionContext getRegionContext() {
		return this.regions;
	}

	public FilterContext getFilterContext() {
		return this.filters;
	}
}
