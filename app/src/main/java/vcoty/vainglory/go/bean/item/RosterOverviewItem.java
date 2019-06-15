package vcoty.vainglory.go.bean.item;

import java.text.*;
import java.util.*;

public class RosterOverviewItem {
	private String url;
	private String heroName;
	private String playerName;
	private String guild;
	private String kda;
	private String kdaNum;
	private String gold;
	private String farm;
	private boolean isMvp;
	private int skillTier;
	private int rankPoints;
	
	private boolean isRedSide;
	private boolean isMe;
	
	private List<RosterItemsItem> items;

	public void setHeroName(String heroName) {
		this.heroName = heroName;
	}

	public String getHeroName() {
		return heroName;
	}

	public void setIsMvp(boolean isMvp) {
		this.isMvp = isMvp;
	}

	public boolean isMvp() {
		return isMvp;
	}

	public void setGuild(String guild) {
		this.guild = guild;
	}

	public String getGuild() {
		return guild;
	}

	public void setRankPoints(int rankPoints) {
		this.rankPoints = rankPoints;
	}

	public int getRankPoints() {
		return rankPoints;
	}

	public void setSkillTier(int skillTier) {
		this.skillTier = skillTier;
	}

	public int getSkillTier() {
		return skillTier;
	}


	public void setIsMe(boolean isMe) {
		this.isMe = isMe;
	}

	public boolean isMe() {
		return isMe;
	}

	public void setIsRedSide(boolean isRedSide) {
		this.isRedSide = isRedSide;
	}

	public boolean isRedSide() {
		return isRedSide;
	}

	public void setFarm(String farm) {
		this.farm = farm;
	}

	public String getFarm() {
		return farm;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setKda(float kda) {
		DecimalFormat df = new DecimalFormat("######0.00");
		this.kda = df.format(kda);
	}

	public String getKda() {
		return kda + " KDA";
	}

	public void setKdaNum(String kdaNum) {
		this.kdaNum = kdaNum;
	}

	public String getKdaNum() {
		return kdaNum;
	}

	public void setGold(double gold) {
		DecimalFormat df = new DecimalFormat("######0.00");
		this.gold = df.format(gold / 1000) + "k";
	}

	public String getGold() {
		return gold;
	}

	public void setItems(List<RosterItemsItem> items) {
		this.items = items;
	}

	public List<RosterItemsItem> getItems() {
		return items;
	}
}
