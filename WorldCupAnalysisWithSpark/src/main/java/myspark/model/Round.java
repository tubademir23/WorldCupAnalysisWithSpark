package myspark.model;

import java.util.HashMap;
import java.util.Map;

public class Round {

	private String roundID;
	private Map<String, Match> matches;

	public Round(String roundId) {
		this.roundID = roundId;
		this.matches = new HashMap<String, Match>();
	}

	public String getRoundID() {
		return roundID;
	}

	public void setRoundID(String roundID) {
		this.roundID = roundID;
	}

	public Map<String, Match> getMatches() {
		return matches;
	}

	public void setMatches(Map<String, Match> matches) {
		this.matches = matches;
	}

	public String getMatchDetails() {
		StringBuilder sb = new StringBuilder();
		matches.forEach((key, match) -> {
			sb.append(String.format("Match: %s, Team 1: %s, Team 2: %s%n ", key, match.getTeam1().getDetails(),
					match.getTeam2().getDetails()));
		});
		return sb.toString();
	}

	public void roundModel(WCPlayersModel model) {
		Match match = null;

		if (!matches.containsKey(model.matchID)) {
			match = new Match(model.matchID);
			matches.put(model.matchID, match);

		} else
			match = matches.get(model.matchID);
		match.matchModel(model);

	}
}
