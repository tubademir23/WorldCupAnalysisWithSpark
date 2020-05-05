package myspark.model;

import java.io.Serializable;

public class WCPlayersModel implements Serializable {
	private String countryName, coachName, lineUp, playerName, position, event;
	private Long roundID, matchID, shirtNumber;

	public WCPlayersModel() {

	}

	public WCPlayersModel(Long roundID, Long matchID, String countryName, String coachName, String lineUp,
			Long shirtNumber, String playerName, String position, String event) {

		this.roundID = roundID;
		this.matchID = matchID;
		this.countryName = countryName;
		this.coachName = coachName;
		this.lineUp = lineUp;
		this.shirtNumber = shirtNumber;
		this.playerName = playerName;
		this.position = position;
		this.event = event;

	}

	public String printStr() {
		String str = "Round: " + roundID + "\t Match: " + matchID + "\t Team: " + countryName + "\t Coach: " + coachName
				+ "\tLineUp: " + lineUp + "\tShirt NO:" + shirtNumber + "\t Player: " + playerName + "\t Position:"
				+ position + "\t Event:" + event;
		System.out.println(str);
		return str;

	}

	public Long getRoundID() {

		return roundID;
	}

	public void setRoundID(Long roundID) {
		this.roundID = roundID;
	}

	public Long getMatchID() {
		return matchID;
	}

	public void setMatchID(Long matchID) {
		this.matchID = matchID;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
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

	public Long getShirtNumber() {
		return shirtNumber;
	}

	public void setShirtNumber(Long shirtNumber) {
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
