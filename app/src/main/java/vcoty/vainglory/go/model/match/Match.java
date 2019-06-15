package vcoty.vainglory.go.model.match;

import moe.banana.jsonapi2.*;
import vcoty.vainglory.go.model.roster.*;
import vcoty.vainglory.go.model.asset.*;
import java.sql.*;

@JsonApi(type="match")
public class Match extends Resource {

	private String createdAt;
	private int duration;
	private String gameMode;
	private double patchVersion;
	private String shardId;
	private String tags;
	
	private HasMany<Asset> assets;
	private HasMany<Roster> rosters;

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getDuration() {
		return duration;
	}

	public void setPatchVersion(double patchVersion) {
		this.patchVersion = patchVersion;
	}

	public double getPatchVersion() {
		return patchVersion;
	}

	public void setShardId(String shardId) {
		this.shardId = shardId;
	}

	public String getShardId() {
		return shardId;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getTags() {
		return tags;
	}

	public void setAssets(HasMany<Asset> assets) {
		this.assets = assets;
	}

	public HasMany<Asset> getAssets() {
		return assets;
	}

	public void setGameMode(String gameMode) {
		this.gameMode = gameMode;
	}

	public String getGameMode() {
		return gameMode;
	}

	public void setRosters(HasMany<Roster> rosters) {
		this.rosters = rosters;
	}

	public HasMany<Roster> getRosters() {
		return rosters;
	}
}
