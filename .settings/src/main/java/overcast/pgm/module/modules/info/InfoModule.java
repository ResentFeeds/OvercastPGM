package overcast.pgm.module.modules.info;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
 
import org.bukkit.ChatColor; 
import org.bukkit.entity.Player;

import overcast.pgm.builder.Builder;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleInfo;
import overcast.pgm.util.BukkitUtils;
import overcast.pgm.util.MojangUtils;

@ModuleInfo(name = "info", desc = "info about the map")
public class InfoModule extends Module {

	private String name;
	private String objective;
	private Version version;
	private List<Author> authors;
	private List<Contributor> contributors;
	private List<Rule> rules;
	private Version proto;

	private HashMap<String, String> names;
	private HashMap<String, String> contributorNames; 

	/** authors names clearing */

	public InfoModule(Version proto, String name, String objective,
			Version version, List<Author> authors,
			List<Contributor> contributors, List<Rule> rules) {
		this.names = new HashMap<>();
		this.proto = proto;
		this.name = name;
		this.objective = objective;
		this.version = version;
		this.authors = authors;
		this.contributors = contributors;
		this.rules = rules;

		addAuthorNames();
		
		//addContributorNames();
	}

	/** make this work on the loaded maps page */

	public void addAuthorNames() {
		for (Author author : this.authors) {
			String name = MojangUtils.getNameByUUID(author.getUUID());
			this.names.put(name,
					author.hasContribution() ? author.getContribution() : null);
		}
	}

	public void clearContributorNames() {
		if(this.contributorNames.size() > 0){
			this.contributorNames.clear();
		}
	}

	public void clearAuthorNames() {
		this.names.clear();
	}

	public boolean hasContributors() {
		return this.getContributors().size() > 0
				|| this.getContributors() != null;
	}

	public HashMap<String, String> getAuthorNames() {
		return this.names;
	}


	public Version getVersion() {
		return this.version;
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

	public boolean isAuthor(Player player) {
		for (Author author : this.getAuthors()) {
			if (author.getUUID().equals(player.getUniqueId())) {
				return true;
			}
		}
		return false;
	}

	public boolean isContributor(Player player) {
		for (Contributor contrib : this.getContributors()) {
			if (contrib.getUUID().equals(player.getUniqueId())) {
				return true;
			}
		}
		return false;
	}

	public Version getProto() {
		return this.proto;
	}

	public List<Rule> getRules() {
		return this.rules;
	}

	@Override
	public Class<? extends Builder> builder() {
		return InfoBuilder.class;
	}

	public String getFormattedMapTitle() {
		return BukkitUtils.dashedChatMessage(ChatColor.DARK_AQUA + " "
				+ this.name + ChatColor.GRAY + " " + this.version, "-",
				ChatColor.RED + "" + ChatColor.STRIKETHROUGH);
	}

	public void getMapInformation(Player p) {
		p.sendMessage(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString()
				+ "Objective: " + ChatColor.GOLD + this.objective);

		if (this.authors.size() == 1) {
			p.sendMessage(new String[] { ChatColor.DARK_PURPLE + ""
					+ ChatColor.BOLD + "Author: " + ChatColor.RED
					+ this.names.keySet().toArray()[0] });
		} else {
			p.sendMessage(new String[] { ChatColor.DARK_PURPLE + ""
					+ ChatColor.BOLD + "Authors: " });

			for (Entry<String, String> authorNames : this.names.entrySet()) {
				String string = new String[] { "* " + ChatColor.RED
						+ authorNames.getKey() }[0];

				if (authorNames.getValue() != null) {
					string += new String[] { ChatColor.GREEN + " ("
							+ authorNames.getValue() + ")" }[0];

				}
				p.sendMessage(string);
			}
		}

		if (hasContributors()) {
			p.sendMessage(new String[] { ChatColor.DARK_PURPLE + ""
					+ ChatColor.BOLD + "Contributors: " });
			for (Entry<String, String> contributors : this.contributorNames
					.entrySet()) {
				String key = "* " + ChatColor.RED + contributors.getKey();

				if (contributors.getValue() != null) {
					key += ChatColor.GREEN + " (" + contributors.getValue()
							+ ")";
				}

				p.sendMessage(key);
			}
		}
	}
}
