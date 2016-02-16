package overcast.pgm.module.modules.filter.types;

import overcast.pgm.module.modules.filter.FilterState;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.player.OvercastPlayer;

public class TeamFilter extends AbstractSingleFilter {

	Team team;

	String id;

	public TeamFilter(String id, Team team) {
		this.id = id;
		this.team = team;
	}

	public TeamFilter(TeamFilterParser parser) {
		this(parser.getID(), parser.getTeam());
	}

	public Team getTeam() {
		return this.team;
	}

	public String getID() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TeamFilter [" + this.getID() + " " + this.getTeam().getName()
				+ "]";
	}
	
	
	
	public boolean matches(Team team){
		return this.team.equals(team);
	}
	
	
	public boolean matches(OvercastPlayer player){
		return matches(player.getTeam());
	}

	@Override
	public FilterState query(Object obj) {
		if(obj instanceof OvercastPlayer){
			return FilterState.fromBoolean(matches((OvercastPlayer) obj));
		}else if(obj instanceof Team){
			 return FilterState.fromBoolean(matches((Team) obj));
		}else{
			return FilterState.ABSTAIN;
		}
	}
}
