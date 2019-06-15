package vcoty.vainglory.go.model.participant;

import java.util.*;
import moe.banana.jsonapi2.*;
import vcoty.vainglory.go.model.player.*;

@JsonApi(type = "participant")
public class Participant extends Resource {
	
	private String actor;
	
	private Participant.Stats stats;
	
	private HasOne<Player> player;

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getActor() {
		
		String befor = actor.replace("*","");
		
		return befor;
	}

	public void setStats(Participant.Stats stats) {
		this.stats = stats;
	}

	public Participant.Stats getStats() {
		return stats;
	}
	
	public void setPlayer(HasOne<Player> player) {
		this.player = player;
	}

	public HasOne<Player> getPlayer() {
		return player;
	}
	
	public static class Stats{
		
		private int skillTier;

		private int assists;//助攻
		private int deaths;//死亡
		private int kills;//杀敌

		private double gold;//金币
		private int level;//等级

		private List<String> items;

		private int farm;
		private String skinKey;

		private int nonJungleMinionKills;
		private int minionKills;

		private int crystalMineCaptures;//水晶矿工捕捉
		private int goldMineCaptures;//金矿捕捉
		private int jungleKills;//打野数量
		private int krakenCaptures;//克拉肯捕捉

		private boolean winner;

		public void setSkillTier(int skillTier) {
			this.skillTier = skillTier;
		}
		
		public float getKda(){
			return (kills + assists) / (deaths + 1.0f);
		}

		public int getSkillTier() {
			return skillTier;
		}

		public void setAssists(int assists) {
			this.assists = assists;
		}

		public int getAssists() {
			return assists;
		}

		public void setDeaths(int deaths) {
			this.deaths = deaths;
		}

		public int getDeaths() {
			return deaths;
		}

		public void setKills(int kills) {
			this.kills = kills;
		}

		public int getKills() {
			return kills;
		}

		public void setGold(double gold) {
			this.gold = gold;
		}

		public double getGold() {
			return gold;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public int getLevel() {
			return level;
		}

		public void setItems(List<String> items) {
			this.items = items;
		}

		public List<String> getItems() {
			return items;
		}

		public void setFarm(int farm) {
			this.farm = farm;
		}

		public int getFarm() {
			return farm;
		}

		public void setSkinKey(String skinKey) {
			this.skinKey = skinKey;
		}

		public String getSkinKey() {
			return skinKey;
		}

		public void setNonJungleMinionKills(int nonJungleMinionKills) {
			this.nonJungleMinionKills = nonJungleMinionKills;
		}

		public int getNonJungleMinionKills() {
			return nonJungleMinionKills;
		}

		public void setMinionKills(int minionKills) {
			this.minionKills = minionKills;
		}

		public int getMinionKills() {
			return minionKills;
		}

		public void setCrystalMineCaptures(int crystalMineCaptures) {
			this.crystalMineCaptures = crystalMineCaptures;
		}

		public int getCrystalMineCaptures() {
			return crystalMineCaptures;
		}

		public void setGoldMineCaptures(int goldMineCaptures) {
			this.goldMineCaptures = goldMineCaptures;
		}

		public int getGoldMineCaptures() {
			return goldMineCaptures;
		}

		public void setJungleKills(int jungleKills) {
			this.jungleKills = jungleKills;
		}

		public int getJungleKills() {
			return jungleKills;
		}

		public void setKrakenCaptures(int krakenCaptures) {
			this.krakenCaptures = krakenCaptures;
		}

		public int getKrakenCaptures() {
			return krakenCaptures;
		}

		public void setWinner(boolean winner) {
			this.winner = winner;
		}

		public boolean isWinner() {
			return winner;
		}
	}
}
