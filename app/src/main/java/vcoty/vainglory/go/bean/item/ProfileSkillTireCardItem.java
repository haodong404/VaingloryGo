package vcoty.vainglory.go.bean.item;

import vcoty.vainglory.go.enums.*;

public class ProfileSkillTireCardItem {
	
	private GameMode gameMode;
	private int rankPoints;
	private int skillTier;
	private int tierColor;

	public ProfileSkillTireCardItem() {}

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

	public void setTierColor(int tierColor) {
		this.tierColor = tierColor;
	}

	public int getTierColor() {
		return tierColor;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	public GameMode getGameMode() {
		return gameMode;
	}
}
