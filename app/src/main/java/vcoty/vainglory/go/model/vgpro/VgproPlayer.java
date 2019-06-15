package vcoty.vainglory.go.model.vgpro;
import java.util.*;
import com.google.gson.annotations.*;

public class VgproPlayer {
	private String name;
	private String region;
	private int tier;
	
	private float rankVst;
	private float blitzVst;
	private float rank5v5Vst;
	
	private VgproPlayer.Stats stats;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegion() {
		return region;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public int getTier() {
		return tier;
	}

	public void setRankVst(float rankVst) {
		this.rankVst = rankVst;
	}

	public float getRankVst() {
		return rankVst;
	}

	public void setBlitzVst(float blitzVst) {
		this.blitzVst = blitzVst;
	}

	public float getBlitzVst() {
		return blitzVst;
	}

	public void setRank5v5Vst(float rank5v5Vst) {
		this.rank5v5Vst = rank5v5Vst;
	}

	public float getRank5v5Vst() {
		return rank5v5Vst;
	}

	public void setStats(VgproPlayer.Stats stats) {
		this.stats = stats;
	}

	public VgproPlayer.Stats getStats() {
		return stats;
	}
	
	public static class Stats{
		private double kda;
		private int games;
		private int wins;
		private int loss;
		private double winRate;
		private double kp;
		
		private double avgKills;
		private double avgDeaths;
		private double avgAssists;
		
		private int blueGames;
		private int blueWins;
		private double blueWinRate;
		private int redGames;
		private int redWins;
		private double redWinRate;
		
		@SerializedName("Heroes")
		private List<VgproHero> heroes;
		
		@SerializedName("Roles")
		private List<VgproRole> roles;


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

		public void setWins(int wins) {
			this.wins = wins;
		}

		public int getWins() {
			return wins;
		}

		public void setLoss(int loss) {
			this.loss = loss;
		}

		public int getLoss() {
			return loss;
		}

		public void setWinRate(double winRate) {
			this.winRate = winRate;
		}

		public double getWinRate() {
			return winRate;
		}

		public void setKp(double kp) {
			this.kp = kp;
		}

		public double getKp() {
			return kp;
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

		public void setBlueGames(int blueGames) {
			this.blueGames = blueGames;
		}

		public int getBlueGames() {
			return blueGames;
		}

		public void setBlueWins(int blueWins) {
			this.blueWins = blueWins;
		}

		public int getBlueWins() {
			return blueWins;
		}

		public void setBlueWinRate(double blueWinRate) {
			this.blueWinRate = blueWinRate;
		}

		public double getBlueWinRate() {
			return blueWinRate;
		}

		public void setRedGames(int redGames) {
			this.redGames = redGames;
		}

		public int getRedGames() {
			return redGames;
		}

		public void setRedWins(int redWins) {
			this.redWins = redWins;
		}

		public int getRedWins() {
			return redWins;
		}

		public void setRedWinRate(double redWinRate) {
			this.redWinRate = redWinRate;
		}

		public double getRedWinRate() {
			return redWinRate;
		}

		public void setHeroes(List<VgproHero> heroes) {
			this.heroes = heroes;
		}

		public List<VgproHero> getHeroes() {
			return heroes;
		}

		public void setRoles(List<VgproRole> roles) {
			this.roles = roles;
		}

		public List<VgproRole> getRoles() {
			return roles;
		}
	}
}
