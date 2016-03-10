package overcast.pgm.scoreboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import overcast.pgm.match.Match;
import overcast.pgm.module.Module;
import overcast.pgm.module.ModuleCollection;
import overcast.pgm.module.modules.objective.wool.WoolObjective;
import overcast.pgm.module.modules.team.TeamModule;
import overcast.pgm.player.OvercastPlayer;
import overcast.pgm.util.ScoreboardUtils;
import overcast.pgm.util.TeamUtil;

public class ScoreboardHandler {

	// fix 
	public static final int MAX_ROWS = 16; // Max rows on the scoreboard
	public HashMap<Integer, Score> scores = new HashMap<>();

	ScoreboardManager sbManager = Bukkit.getScoreboardManager();

	// the current board of the match
	Scoreboard board = sbManager.getNewScoreboard();
	Scoreboard invBoard = sbManager.getNewScoreboard();
	protected Team[] sbTeams = new Team[MAX_ROWS];

	private Match match;

	private List<String> objectives;

	// objectives

	private Objective SBobj;
	private Objective invObj;

	private static String IDENTIFIER = "OvercastPGM";

	public ScoreboardHandler(Match match) {
		this.objectives = new ArrayList<>();
		this.match = match;

		viewBoard(OvercastPlayer.getPlayers(), null, false);
 
		// try this
		if (this.objectives.size() != 0) {
			this.objectives.clear();
		}
	}

	// TODO make the scoreboard work
	public void viewBoard(List<OvercastPlayer> players, OvercastPlayer player, boolean updating) {
		if (updating) {
			ScoreboardUtils.removeTeams(this.board);
			ScoreboardUtils.removeObjectives(this.board);
		}

		this.SBobj = this.board.registerNewObjective(IDENTIFIER, IDENTIFIER);
		this.SBobj.setDisplaySlot(DisplaySlot.SIDEBAR);

		ModuleCollection<Module> modules = this.match.getModules();

		boolean teamsLoaded = modules.isModuleLoaded(TeamModule.class);

		if (teamsLoaded) {
			Set<overcast.pgm.module.modules.team.Team> teams = TeamUtil.getTeamModule().getTeams();
			this.sbTeams = renderTeams(this.board, teams);
		}

		if (modules.isModuleLoaded(WoolObjective.class)) {
			this.SBobj.setDisplayName(ChatColor.AQUA + "Objectives");

			loadContents(this.SBobj, sbTeams);
		}

		if (players != null) {
			if (players.size() > 0) {
				for (OvercastPlayer op : players) {
					op.setScoreboard(this.board);
				}
			}
		} else {
			player.setScoreboard(this.board);
		}
	}

	public Team[] renderTeams(Scoreboard board, Set<overcast.pgm.module.modules.team.Team> teams) {
		Team[] all = new Team[MAX_ROWS];
		for (int i = 0; i < MAX_ROWS; i++) {
			if (teams.size() >= MAX_ROWS) {
				overcast.pgm.module.modules.team.Team team = TeamUtil.getTeam(i);
				all[i] = ScoreboardUtils.registerTeam(board, team);
			}
		}
		return all;
	}

	public List<String> loadContents(Objective obj, Team[] teams) {
		List<overcast.pgm.module.modules.team.Team> teamList = new ArrayList<>();
		if (teams.length > 1) {
			for (int i = 0; i < teams.length; i++) {
				overcast.pgm.module.modules.team.Team PGMTeam = TeamUtil.getTeam(i);
				if (PGMTeam != null) {
					teamList.add(PGMTeam);
				}
			}
		}

		int i = 0;

		for (overcast.pgm.module.modules.team.Team team : teamList) {
			Score score = obj.getScore(team.getColor() + team.getName());
			score.setScore(i);
			this.scores.put(i, score);
			i++;

			for (WoolObjective wool : WoolObjective.getObjectives(team)) {
				if (wool != null) {
					this.objectives.add(wool.getWoolName());
				}
			}
		}

		return this.objectives;
	}

	// old one
	public static void organizeScoreboard(List<String> scoreboardInfo, Scoreboard board) {
		Collections.reverse(scoreboardInfo);
		for (int amount = scoreboardInfo.size(); amount > 0; amount--) {

			String name = scoreboardInfo.get(amount - 1);
			Score score = board.getObjective(DisplaySlot.SIDEBAR).getScore(name);
			score.setScore(amount - 1);
		}
	}

	// update the board :)
	public void updateBoard() {
        boolean updating = true;
		for (OvercastPlayer players : OvercastPlayer.getPlayers()) {
			if (players.getScoreboard() != null) {
				if (players.getScoreboard() == this.board) {
					removeScoreboard(players);
					viewBoard(null, players, updating);
				} else {
					removeScoreboard(players);
					viewBoard(null, players, updating);
				}
			} else {
				viewBoard(null, players, updating);
			}
		}
	}

	private void removeScoreboard(OvercastPlayer players) {
		this.invObj = this.invBoard.registerNewObjective("INVISIBLE", "INVISIBLE");
		this.invObj.setDisplaySlot(DisplaySlot.SIDEBAR);
        
		players.setScoreboard(invBoard);
	}

	public Scoreboard getScoreboard() {
		return this.board;
	}
}
