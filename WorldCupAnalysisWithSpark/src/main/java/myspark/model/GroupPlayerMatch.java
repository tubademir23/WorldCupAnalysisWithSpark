package myspark.model;

import java.io.Serializable;

public class GroupPlayerMatch implements Serializable {
	private String playerName;
	private int matchCount;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getMatchCount() {
		return matchCount;
	}

	public void setMatchCount(int matchCount) {
		this.matchCount = matchCount;
	}

	public GroupPlayerMatch(String playerName, int matchCount) {
		super();
		this.playerName = playerName;
		this.matchCount = matchCount;
	}
}
