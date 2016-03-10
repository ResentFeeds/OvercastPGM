package overcast.pgm.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import overcast.pgm.event.ScoreboardUpdateEvent;
import overcast.pgm.match.Match;
import overcast.pgm.module.modules.team.Team;

public class ScoreboardListener implements Listener {

	int score = 0;
	
	@EventHandler
	public void onScoreboardUpdate(ScoreboardUpdateEvent event) {
		Match match = event.getMatch();
		Team team = event.getTeam();
		Scoreboard board = match.getScoreboardHandler().getScoreboard();

		if (board.getObjective(DisplaySlot.SIDEBAR) != null) {
			Objective obj = board.getObjective(DisplaySlot.SIDEBAR);

			if(obj.getScore(team.getColor() +team.getName()) != null){
			   Score score = obj.getScore(team.getColor() + team.getName());
               this.score = score.getScore(); 
			   board.resetScores(team.getColor() + team.getName());
			   Score newscore = obj.getScore(team.getColor() + event.getNewName());
			   newscore.setScore(this.score);
			   match.getScoreboardHandler().updateBoard();
			}
		}
	}
}
