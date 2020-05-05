package myspark.model;

public class Player {
	String lineUp, playerName, position;
	Long shirtNumber;
	Event event;

	public Player(String playerName) {
		this.playerName = playerName;
	}

	public Player(String lineUp, Long shirtNumber, String playerName, String position, Event event) {
		super();
		this.lineUp = lineUp;
		this.shirtNumber = shirtNumber;
		this.playerName = playerName;
		this.position = position;
		this.event = event;
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

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}
