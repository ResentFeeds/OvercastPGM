package overcast.pgm.event;

import overcast.pgm.module.modules.team.Team;

public class TeamNameChangeEvent extends TeamEvent {

	private Team team;

	public TeamNameChangeEvent(Team team) {
		this.team = team;
	}

	public Team getTeam() {
		return this.team;
	}
	
	
	//TODO
}
