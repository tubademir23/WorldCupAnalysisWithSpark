package myspark.model;

import java.io.Serializable;

public class WCPlayersModel implements Serializable {
	String roundID, matchID, team, coachName, lineUp, shirtNumber, playerName, position, event;

	public WCPlayersModel(String roundID, String matchID, String team, String coachName, String lineUp, String shirtNumber,
			String playerName, String position, String event) {
		super();
		this.roundID = roundID;
		this.matchID = matchID;
		this.team = team;
		this.coachName = coachName;
		this.lineUp = lineUp;
		this.shirtNumber = shirtNumber;
		this.playerName = playerName;
		this.position = position;
		this.event = event;
	}

	public String toString() {
		return "Round: " + roundID + "\t Match: " + matchID + "\t Team: " + team + "\t Coach: " + coachName
				+ "\tLineUp: " + lineUp + "\tShirt NO:" + shirtNumber + "\t Player: " + playerName + "\t Position:"
				+ position + "\t Event:" + event;

	}

	public String getRoundID() {
		return roundID;
	}

	public void setRoundID(String roundID) {
		this.roundID = roundID;
	}

	public String getMatchID() {
		return matchID;
	}

	public void setMatchID(String matchID) {
		this.matchID = matchID;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	public String getLineUp() {
		return lineUp;
	}

	public void setLineUp(String lineUp) {
		this.lineUp = lineUp;
	}

	public String getShirtNumber() {
		return shirtNumber;
	}

	public void setShirtNumber(String shirtNumber) {
		this.shirtNumber = shirtNumber;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}
