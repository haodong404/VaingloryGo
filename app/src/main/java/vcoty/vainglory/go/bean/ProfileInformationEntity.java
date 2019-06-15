package vcoty.vainglory.go.bean;
import java.util.*;
import vcoty.vainglory.go.bean.item.*;
import vcoty.vainglory.go.enums.*;

public class ProfileInformationEntity {
	
	private String name;
	private String guild;
	private String headImageUrl;
	private Region region;
	
	private List<ProfileSkillTireCardItem> skillTireItems; 
	
	private String avgKills;
	private String avgDeaths;
	private String avgAssists;
	
	private int fields;
	private int wins;
	private double winrate;
	
	private double kp;
	private double kda;
	
	private int blueGames;
	private int blueWins;
	private double blueWinRate;
	private int redGames;
	private int redWins;
	private double redWinRate;
	
	private List<ProfileRoleItem> roleItems;
	
	private List<ProfilePlayedHeroIitem> playedHeroItems;

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

	public void setKda(double kda)
		{
	this.kda = kda;
	}

		public double getKda()
	{
	return kda;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setGuild(String guild) {
		this.guild = guild;
	}

	public String getGuild() {
		return guild;
	}

	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}

	public String getHeadImageUrl() {
		return headImageUrl;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Region getRegion() {
		return region;
	}

	public void setSkillTireItems(List<ProfileSkillTireCardItem> skillTireItems) {
		this.skillTireItems = skillTireItems;
	}

	public List<ProfileSkillTireCardItem> getSkillTireItems() {
		return skillTireItems;
	}

	public void setAvgKills(String avgKills) {
		this.avgKills = avgKills;
	}

	public String getAvgKills() {
		return avgKills;
	}

	public void setAvgDeaths(String avgDeaths) {
		this.avgDeaths = avgDeaths;
	}

	public String getAvgDeaths() {
		return avgDeaths;
	}

	public void setAvgAssists(String avgAssists) {
		this.avgAssists = avgAssists;
	}

	public String getAvgAssists() {
		return avgAssists;
	}

	public void setFields(int fields) {
		this.fields = fields;
	}

	public int getFields() {
		return fields;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getWins() {
		return wins;
	}

	public void setWinrate(double winrate) {
		this.winrate = winrate;
	}

	public double getWinrate() {
		return winrate;
	}

	public void setKp(double kp) {
		this.kp = kp;
	}

	public double getKp() {
		return kp;
	}

	public void setRoleItems(List<ProfileRoleItem> roleItems) {
		this.roleItems = roleItems;
	}

	public List<ProfileRoleItem> getRoleItems() {
		return roleItems;
	}

	public void setPlayedHeroItems(List<ProfilePlayedHeroIitem> playedHeroItems) {
		this.playedHeroItems = playedHeroItems;
	}

	public List<ProfilePlayedHeroIitem> getPlayedHeroItems() {
		return playedHeroItems;
	}
}
