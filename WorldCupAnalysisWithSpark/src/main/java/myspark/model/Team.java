package myspark.model;

import java.util.HashMap;
import java.util.Map;

public class Team {
	private String teamName, coachName;
	private Map<String, Player> players;

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	public Map<String, Player> getPlayers() {
		return players;
	}

	public void setPlayers(Map<String, Player> players) {
		this.players = players;
	}

	public Team(String teamName, String coachName, Map<String, Player> players) {
		super();
		this.teamName = teamName;
		this.coachName = coachName;
		this.players = players;
	}

	public Team(String teamName) {
		this.teamName = teamName;
		this.players = new HashMap<String, Player>();
	}

	public String getDetails() {
		return "N: " + teamName + " MR:" + coachName + "\tPC: " + players.size();
	}

	public void teamModel(WCPlayersModel model) {
		coachName = model.getCoachName();

		Player player = null;
		// Using HashMap prevented one player from registering more than one in a match
		player = new Player(model.getLineUp(), model.getShirtNumber(), model.getPlayerName(), model.getPosition(),
				new Event(model.getEvent()));
		players.put(model.getPlayerName(), player);

	}
}
