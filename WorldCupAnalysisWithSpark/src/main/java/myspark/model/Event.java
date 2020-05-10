package myspark.model;

public class Event {
	Integer minute;
	String eventType;
	String event;

	public Event(String event) {
		this.event = event;
		String[] ee = event.replace('\'', ' ').trim().split("(?<=\\D)(?=\\d)");
		if (ee.length == 2) {
			this.eventType = ee[0];
			this.minute = Integer.parseInt(ee[1]);
		}

	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}
/*
 * (G)oal, (I)n to the match (IH) In to half time (MP)Miss Penalty (O)ut from match (P)Kick Penalty (R)ed card (RSY) two yellow after red card (W) own goal (Y)ellow card
 */