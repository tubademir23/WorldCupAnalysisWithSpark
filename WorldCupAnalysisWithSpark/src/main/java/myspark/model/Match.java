package myspark.model;

public class Match {
	private Long matchId;
	private Team team1, team2;

	public Match(Long matchId) {
		this.matchId = matchId;
	}

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public Team getTeam1() {
		return team1;
	}

	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	public Team getTeam2() {
		return team2;
	}

	public void setTeam2(Team team2) {
		this.team2 = team2;
	}

	public Match(Long matchId, Team team1, Team team2) {
		super();
		this.matchId = matchId;
		this.team1 = team1;
		this.team2 = team2;
	}

	private Team whichTeam(String name) {
		if (team1 == null) {
			team1 = new Team(name);
			return team1;
		} else if (team2 == null) {
			team2 = new Team(name);
			return team2;
		} else {
			if (team1.getTeamName().equals(name))
				return team1;
			else
				return team2;
		}
	}

	public void matchModel(WCPlayersModel model) {
		Team team = whichTeam(model.getCountryName());
		team.teamModel(model);

	}

}
