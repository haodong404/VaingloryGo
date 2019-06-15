package vcoty.vainglory.go.bean.item;

import vcoty.vainglory.go.enums.Role;

public class ProfileRoleItem {
	
	private Role role;
	private String kills;
	private String deaths;
	private String assists;
	private String kda;
	private double winrate;
	private String fields;

	public void setKills(String kills) {
		this.kills = kills;
	}

	public String getKills() {
		return kills;
	}

	public void setDeaths(String deaths) {
		this.deaths = deaths;
	}

	public String getDeaths() {
		return deaths;
	}

	public void setAssists(String assists) {
		this.assists = assists;
	}

	public String getAssists() {
		return assists;
	}

	public void setKda(String kda) {
		this.kda = kda;
	}

	public String getKda() {
		return kda;
	}

	public void setWinrate(double winrate) {
		this.winrate = winrate;
	}

	public double getWinrate() {
		return winrate;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getFields() {
		return fields;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Role getRole() {
		return role;
	}
}
