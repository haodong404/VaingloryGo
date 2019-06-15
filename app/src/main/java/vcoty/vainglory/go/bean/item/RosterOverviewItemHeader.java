package vcoty.vainglory.go.bean.item;

public class RosterOverviewItemHeader {
	private int isWinRes;
	private String kills;
	private String turret;
	private String gold;
	private String karen;
	private boolean redSide;

	public void setKaren(String karen) {
		this.karen = karen;
	}

	public String getKaren() {
		return karen;
	}

	public void setIsWinRes(int isWinRes) {
		this.isWinRes = isWinRes;
	}

	public int getIsWinRes() {
		return isWinRes;
	}

	public void setKills(String kills) {
		this.kills = kills;
	}

	public String getKills() {
		return kills;
	}

	public void setTurret(String turret) {
		this.turret = turret;
	}

	public String getTurret() {
		return turret;
	}

	public void setGold(String gold) {
		this.gold = gold;
	}

	public String getGold() {
		return gold;
	}

	public void setRedSide(boolean redSide) {
		this.redSide = redSide;
	}

	public boolean isRedSide() {
		return redSide;
	}
}
