package vcoty.vainglory.go.model.vgpro.telemetry;
import com.google.gson.annotations.*;
import java.util.*;

public class DeathsTimeline {
	
	@SerializedName("Actor")
	private String actor;
	
	@SerializedName("Time")
	private String time;
	
	@SerializedName("Gold")
	private int gold;
	
	@SerializedName("Position")
	private List<Float> position;

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getActor() {
		return actor;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime() {
		return time;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getGold() {
		return gold;
	}

	public void setPosition(List<Float> position) {
		this.position = position;
	}

	public List<Float> getPosition() {
		return position;
	}
}
