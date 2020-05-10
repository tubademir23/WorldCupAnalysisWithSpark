package myspark.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
	String lineUp, playerName, position;
	Long shirtNumber;
	List<Event> events;

	public Player(String playerName) {
		this.playerName = playerName;
	}

	public Player(String lineUp, Long shirtNumber, String playerName, String position, String event) {
		super();
		this.lineUp = lineUp;
		this.shirtNumber = shirtNumber;
		this.playerName = playerName;
		this.position = position;
		if (event != null && !event.isEmpty() && !event.isBlank()) {
			this.events = new ArrayList<Event>();
			getEventModel(event);
		}
	}

	private void getEventModel(String strEvent) {
		String[] split = strEvent.split(" ");
		for (String str : split) {

			Event event = new Event(str);
			this.events.add(event);

		}
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

	public List<Event> getEvents() {
		return events;
	}

	public void setEvent(List<Event> events) {
		this.events = events;
	}

}
