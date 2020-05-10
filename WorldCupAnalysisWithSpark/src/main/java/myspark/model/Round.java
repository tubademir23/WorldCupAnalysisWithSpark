package myspark.model;

import java.util.HashMap;
import java.util.Map;

public class Round {

	private Long roundID;
	private Map<Long, Match> matches;

	public Round(Long roundId) {
		this.roundID = roundId;
		this.matches = new HashMap<Long, Match>();
	}

	public Long getRoundID() {
		return roundID;
	}

	public void setRoundID(Long roundID) {
		this.roundID = roundID;
	}

	public Map<Long, Match> getMatches() {
		return matches;
	}

	public void setMatches(Map<Long, Match> matches) {
		this.matches = matches;
	}

	public String getMatchDetails() {
		StringBuilder sb = new StringBuilder();
		matches.forEach((key, match) -> {
			sb.append(String.format("Match: %s, Team 1: %s, Team 2: %s%n ", key, match.getTeam1().getDetails(), match.getTeam2().getDetails()));
		});
		return sb.toString();
	}

	public Match roundModel(WCPlayersModel model) {
		Match match = null;

		if (!matches.containsKey(model.getMatchID())) {
			match = new Match(model.getMatchID());
			matches.put(model.getMatchID(), match);

		} else
			match = matches.get(model.getMatchID());
		match.matchModel(model);
		return match;

	}
}
