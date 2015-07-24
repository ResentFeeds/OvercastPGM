package overcast.pgm.module.modules.info;

import java.util.List;

import overcast.pgm.builder.Builder;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleInfo;
import overcast.pgm.util.MojangUtils;

@ModuleInfo(name = "info", desc = "description of a map", listener = false)
public class InfoModule extends Module {

	private String name;
	private String objective;
	private List<Author> authors;
	private List<Contributor> contributors;
	private List<Rule> rules;

	public InfoModule() {
	}

	public InfoModule(String name, String objective, List<Author> authors,
			List<Contributor> contributors, List<Rule> rules) {
		this.name = name;
		this.objective = objective;
		this.authors = authors;
		this.contributors = contributors;
		this.rules = rules;

		//Log.info(this.toString());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InfoModule [name=");
		builder.append(name);
		builder.append(", objective=");
		builder.append(objective);
		builder.append(", authors=");
		/** authors */
		for (Author author : this.getAuthors()) {
			String name = MojangUtils.getNameByUUID(author.getUUID());
			builder.append(name + " & " + author.getContribution() + " ");
		}
		builder.append(", contributors=");
		/** authors */
		for (Contributor contributor : this.getContributors()) {
			String name = MojangUtils.getNameByUUID(contributor.getUUID());
			builder.append(name + " & " + contributor.getContribution() + " ");
		} 
		builder.append(", rules=");
		for(Rule rule : this.getRules()){
			builder.append(rule.getRule() + " ");
		} 
		builder.append("]");
		return builder.toString();
	}

	public String getName() {
		return this.name;
	}

	public String getObjective() {
		return this.objective;
	}

	public List<Author> getAuthors() {
		return this.authors;
	}

	public List<Contributor> getContributors() {
		return this.contributors;
	}

	public List<Rule> getRules() {
		return this.rules;
	}

	@Override
	public void unload() {
		// do nothing
	}

	@Override
	public Class<? extends Builder> builder() {
		return InfoBuilder.class;
	}
}
