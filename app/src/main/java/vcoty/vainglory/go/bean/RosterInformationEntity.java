package vcoty.vainglory.go.bean;

import java.util.*;
import vcoty.vainglory.go.enums.*;
import vcoty.vainglory.go.model.player.*;
import vcoty.vainglory.go.model.roster.*;

public class RosterInformationEntity {
	private List<Roster> rosters;
	private boolean win;
	private GameMode gameMode;
	private String direction;
	private String date;
	private String myselfName;
	private int skillTier;
	private String matchId;
	private Player mvpPlayer;
	private Map<Boolean,String> myself;

	public RosterInformationEntity(List<Roster> rosters, GameMode gameMode, String direction, String date,boolean win,String myselfName,int skillTier,String matchId,Map<Boolean,String> myself,Player mvpPlayer) {
		this.rosters = rosters;
		this.gameMode = gameMode;
		this.direction = direction;
		this.date = date;
		this.win = win;
		this.myselfName = myselfName;
		this.skillTier = skillTier;
		this.matchId = matchId;
		this.myself = myself;
		this.mvpPlayer = mvpPlayer;
	}
	
	public RosterInformationEntity() {}

	public void setMvpPlayer(Player mvpPlayer) {
		this.mvpPlayer = mvpPlayer;
	}

	public Player getMvpPlayer() {
		return mvpPlayer;
	}

	public void setMyself(Map<Boolean, String> myself) {
		this.myself = myself;
	}

	public Map<Boolean, String> getMyself() {
		return myself;
	}
	
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setSkillTier(int skillTier) {
		this.skillTier = skillTier;
	}

	public int getSkillTier() {
		return skillTier;
	}


	public void setMyselfName(String myselfName) {
		this.myselfName = myselfName;
	}

	public String getMyselfName() {
		return myselfName;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public boolean isWin() {
		return win;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getDirection() {
		return direction;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	public GameMode getGameMode() {
		return gameMode;
	}


	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public void setRosters(List<Roster> rosters) {
		this.rosters = rosters;
	}

	public List<Roster> getRosters() {
		return rosters;
	}
} 
