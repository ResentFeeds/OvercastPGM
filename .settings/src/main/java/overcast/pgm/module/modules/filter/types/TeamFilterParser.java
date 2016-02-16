package overcast.pgm.module.modules.filter.types;

import org.w3c.dom.Element;

import overcast.pgm.module.modules.team.Team;
import overcast.pgm.util.TeamUtil;

public class TeamFilterParser  {

	String id;
	Team team;

	public TeamFilterParser(Element element) {
	//	super(element);
		this.id = element.hasAttribute("id") ? element.getAttribute("id") : null;
		this.team = TeamUtil.getTeamByID(element.getTextContent());
	}

	public String getID() {
		return this.id;
	}

	public Team getTeam() {
		return this.team;
	}
}
