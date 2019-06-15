package vcoty.vainglory.go.model.roster;
import moe.banana.jsonapi2.*;
import vcoty.vainglory.go.model.participant.*;

@JsonApi(type="roster")
public class Roster extends Resource {
	
	private String shardId;

	private String won;
	
	private Roster.Stats stats;

	private HasMany<Participant> participants;

	public void setWon(boolean won) {
		this.won = won + "";
	}

	public boolean isWon() {
		return Boolean.parseBoolean(won);
	}

	public void setStats(Roster.Stats stats) {
		this.stats = stats;
	}

	public Roster.Stats getStats() {
		return stats;
	}

	public void setShardId(String shardId) {
		this.shardId = shardId;
	}

	public String getShardId() {
		return shardId;
	}

	public void setParticipants(HasMany<Participant> participants) {
		this.participants = participants;
	}

	public HasMany<Participant> getParticipants() {
		return participants;
	}
	
	public static class Stats {

		private int gold;//金币总数
		private int heroKills;//击杀总数

		private int krakenCaptures;//克拉肯捕捉

		private String side;//队伍方位
		private int turretKills;//炮塔杀死数量
		private int turretsRemaining;//炮塔剩余 


		public void setGold(int gold) {
			this.gold = gold;
		}

		public int getGold() {
			return gold;
		}

		public void setHeroKills(int heroKills) {
			this.heroKills = heroKills;
		}

		public int getHeroKills() {
			return heroKills;
		}

		public void setKrakenCaptures(int krakenCaptures) {
			this.krakenCaptures = krakenCaptures;
		}

		public int getKrakenCaptures() {
			return krakenCaptures;
		}

		public void setSide(String side) {
			this.side = side;
		}

		public String getSide() {
			return side;
		}

		public void setTurretKills(int turretKills) {
			this.turretKills = turretKills;
		}

		public int getTurretKills() {
			return turretKills;
		}

		public void setTurretsRemaining(int turretsRemaining) {
			this.turretsRemaining = turretsRemaining;
		}

		public int getTurretsRemaining() {
			return turretsRemaining;
		}
	}
}
