package vcoty.vainglory.go.model.vgpro;

public class VgproRole {
    private String name;
    private double kda;
    private int games;
    private double winRate;
    private double avgKills;
    private double avgDeaths;
    private double avgAssists;


	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setKda(double kda) {
		this.kda = kda;
	}

	public double getKda() {
		return kda;
	}

	public void setGames(int games) {
		this.games = games;
	}

	public int getGames() {
		return games;
	}

	public void setWinRate(double winRate) {
		this.winRate = winRate;
	}

	public double getWinRate() {
		return winRate;
	}

	public void setAvgKills(double avgKills) {
		this.avgKills = avgKills;
	}

	public double getAvgKills() {
		return avgKills;
	}

	public void setAvgDeaths(double avgDeaths) {
		this.avgDeaths = avgDeaths;
	}

	public double getAvgDeaths() {
		return avgDeaths;
	}

	public void setAvgAssists(double avgAssists) {
		this.avgAssists = avgAssists;
	}

	public double getAvgAssists() {
		return avgAssists;
	}
}
