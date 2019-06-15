package vcoty.vainglory.go.model.player;

import moe.banana.jsonapi2.JsonApi;
import moe.banana.jsonapi2.*;

@JsonApi(type = "player")
public class Player extends Resource {
	private String name;
	private double patchVersion;

	private Stats stats;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPatchVersion(double patchVersion) {
		this.patchVersion = patchVersion;
	}

	public double getPatchVersion() {
		return patchVersion;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}

	public Stats getStats() {
		return stats;
	}
	
	
	public static class Stats{
		private String guildTag;
		private int karmaLevel;
		private int level;
		private int lifetimeGold;
		private int lossStreak;
		private int winStreak;
		private int skillTier;
		private int wins;
		private int xp;

		
		private GamesPlayed gamesPlayed;

		private RankPoints rankPoints;


		public void setGuildTag(String guildTag) {
			this.guildTag = guildTag;
		}

		public String getGuildTag() {
			return guildTag;
		}

		public void setKarmaLevel(int karmaLevel) {
			this.karmaLevel = karmaLevel;
		}

		public int getKarmaLevel() {
			return karmaLevel;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public int getLevel() {
			return level;
		}

		public void setLifetimeGold(int lifetimeGold) {
			this.lifetimeGold = lifetimeGold;
		}

		public int getLifetimeGold() {
			return lifetimeGold;
		}

		public void setLossStreak(int lossStreak) {
			this.lossStreak = lossStreak;
		}

		public int getLossStreak() {
			return lossStreak;
		}

		public void setWinStreak(int winStreak) {
			this.winStreak = winStreak;
		}

		public int getWinStreak() {
			return winStreak;
		}

		public void setSkillTier(int skillTier) {
			this.skillTier = skillTier;
		}

		public int getSkillTier() {
			return skillTier;
		}

		public void setWins(int wins) {
			this.wins = wins;
		}

		public int getWins() {
			return wins;
		}

		public void setXp(int xp) {
			this.xp = xp;
		}

		public int getXp() {
			return xp;
		}

		public void setGamesPlayed(GamesPlayed gamesPlayed) {
			this.gamesPlayed = gamesPlayed;
		}

		public GamesPlayed getGamesPlayed() {
			return gamesPlayed;
		}

		public void setRankPoints(RankPoints rankPoints) {
			this.rankPoints = rankPoints;
		}

		public RankPoints getRankPoints() {
			return rankPoints;
		}
	}
	
}
