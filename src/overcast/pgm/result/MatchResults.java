package overcast.pgm.result;

import org.bukkit.ChatColor;

import overcast.pgm.match.Match;
import overcast.pgm.module.modules.team.Team;
import overcast.pgm.util.TeamUtil;

public class MatchResults { 
	
	    public static MatchResult parse(String raw) {
	        return parseResult(null, raw);
	    }

	    public static MatchResult parse(Match match, String raw) {
	        return parseResult(match, raw);
	    }

	    public static MatchResult parseResult(Match match, String raw) {
	        if(raw.equalsIgnoreCase("objectives")) {
	            return new MatchObjectivesResult();
	        } else if (raw.equalsIgnoreCase("tie")) {
	            return new MatchTieResult();
	        } else if(match != null) {
	            Team winner = TeamUtil.getTeamByID(raw);
	            if(winner == null) {
	                throw new IllegalArgumentException("Invalid result");
	            }
	            return new MatchTeamResult(winner);
	        } else {
	            Team winner = TeamUtil.getTeamByID(raw);
	            if(winner == null) {
	                throw new IllegalArgumentException("Invalid result");
	            }
	            return new MatchTeamResult(winner);
	        }
	    }

	    public static String describe(MatchResult result, ChatColor color) {
	        if(result instanceof MatchTeamResult) {
	            return ((MatchTeamResult) result).getWinner().getColoredName() + color + " wins"; 
	        } else if(result instanceof MatchObjectivesResult) {
	            return color + "most objectives";
	        } else if(result instanceof MatchTieResult) {
	            return color + "tie";
	        } else {
	            return color + "unknown";
	        }
	    }
	}