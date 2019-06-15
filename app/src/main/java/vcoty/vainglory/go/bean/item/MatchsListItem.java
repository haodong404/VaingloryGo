package vcoty.vainglory.go.bean.item;

import java.util.*;
import vcoty.vainglory.go.enums.*;
import vcoty.vainglory.go.model.player.*;
import vcoty.vainglory.go.model.roster.*;

public class MatchsListItem {
	private String id;
	
	private List<Roster> rosters;
	
	private String headImgUrl;
	private GameMode gameMode;
	private String direction;
	private int skillTier;
	
	private String kda;
	private String date;

	private boolean isMvp;
	private Player mvpPlayer;
	private boolean isWon;
	private Map<Boolean,String> myself;

	public void setMvpPlayer(Player mvpPlayer)
		{
	this.mvpPlayer = mvpPlayer;
	}

		public Player getMvpPlayer()
	{
	return mvpPlayer;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public void setMyself(Map<Boolean, String> myself) {
		this.myself = myself;
	}

	public Map<Boolean, String> getMyself() {
		return myself;
	}


	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setSkillTier(int skillTier) {
		this.skillTier = skillTier;
	}

	public int getSkillTier() {
		return skillTier;
	}

	
	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getDirection() {
		return direction;
	}
	
	public void setRosters(List<Roster> rosters) {
		this.rosters = rosters;
	}

	public List<Roster> getRosters() {
		return rosters;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setKda(String kda) {
		this.kda = kda;
	}

	public String getKda() {
		return kda;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public void setIsMvp(boolean isMvp) {
		this.isMvp = isMvp;
	}

	public boolean isMvp() {
		return isMvp;
	}

	public void setIsWon(boolean isWon) {
		this.isWon = isWon;
	}

	public boolean isWon() {
		return isWon;
	}
}
