package overcast.pgm.module.modules.team;

import java.util.Set;

import org.bukkit.ChatColor;

import overcast.pgm.builder.Builder;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleInfo;

@ModuleInfo(name = "team")
public class TeamModule extends Module {

	Set<Team> teams;

	Team observers = new Team("obs", "Observers", ChatColor.AQUA, 100, false);

	public TeamModule(Set<Team> teams) {
		this.teams = teams;
	}

	@Override
	public Class<? extends Builder> builder() {
		return TeamBuilder.class;
	}

	public Set<Team> getTeams() {
		return this.teams;
	}

	public Team getObservers() {
		return this.observers;
	}

	public boolean hasEnoughPlayers() {
		for (Team team : this.teams) {
			if (team != null) {
				int size = team.getMembers().size();
				if (size == 1) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasTeams() {
		return this.getTeams().size() > 0;
	}
}
