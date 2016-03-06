package overcast.pgm.event;

import java.util.Set;

import overcast.pgm.match.Match;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.module.modules.team.TeamModule;

public class MatchLoadEvent extends PGMEvent {

	private Match match; 

	public MatchLoadEvent(Match match) {
		this.match = match; 
	}

	
	public Match getMatch(){
		return this.match;
	}
	
	
	public String getDescription(){
		return this.match.getMap().getShortDescription();
	}
	
	
	
	public boolean hasEnoughMembers(){
		 TeamModule teamMod = getMatch().getModules().getModule(TeamModule.class);
		 
		 Set<Team> teams = teamMod.getTeams();
		 int min = 1;
		 
		 for(Team team : teams){
			  if(team.getMembers().size() != min && team.getMembers().size() == (min - 1)){
				  //try
			  }
		 }
		 
		 
		 
		 return false;
	}
}
