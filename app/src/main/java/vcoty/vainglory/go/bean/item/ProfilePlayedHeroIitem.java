package vcoty.vainglory.go.bean.item;

public class ProfilePlayedHeroIitem {
	private String url;
	private String name;
	private int wins;
	private int playeds;
	private double kills;
	private double deaths;
	private double assists;
	private String kda;
	private double winrate;

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getWins() {
		return wins;
	}

	public void setPlayeds(int playeds) {
		this.playeds = playeds;
	}

	public int getPlayeds() {
		return playeds;
	}

	public void setKills(double kills) {
		this.kills = kills;
	}

	public double getKills() {
		return kills;
	}

	public void setDeaths(double deaths) {
		this.deaths = deaths;
	}

	public double getDeaths() {
		return deaths;
	}

	public void setAssists(double assists) {
		this.assists = assists;
	}

	public double getAssists() {
		return assists;
	}

	public void setKda(String kda) {
		this.kda = kda;
	}

	public String getKda() {
		return kda;
	}

	public void setWinrate(double winrate) {
		this.winrate = winrate;
	}

	public double getWinrate() {
		return winrate;
	}
}
