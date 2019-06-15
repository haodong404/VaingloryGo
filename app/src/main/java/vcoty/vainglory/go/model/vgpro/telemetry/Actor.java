package vcoty.vainglory.go.model.vgpro.telemetry;
import java.util.*;

public class Actor {
	private int healed;
	private Map<String,Integer> totalHealed;
	private Map<String,Integer> totalDamage;
	private Map<String,Integer> totalDealt;
	private List<String> skill;
	
	private int objectiveDamage;
	private int damage;
	private int dealt;
	private int taken;
	private List<DeathsTimeline> deaths;

	public void setHealed(int healed) {
		this.healed = healed;
	}

	public int getHealed() {
		return healed;
	}

	public void setTotalHealed(Map<String, Integer> totalHealed) {
		this.totalHealed = totalHealed;
	}

	public Map<String, Integer> getTotalHealed() {
		return totalHealed;
	}

	public void setTotalDamage(Map<String, Integer> totalDamage) {
		this.totalDamage = totalDamage;
	}

	public Map<String, Integer> getTotalDamage() {
		return totalDamage;
	}

	public void setTotalDealt(Map<String, Integer> totalDealt) {
		this.totalDealt = totalDealt;
	}

	public Map<String, Integer> getTotalDealt() {
		return totalDealt;
	}

	public void setSkill(List<String> skill) {
		this.skill = skill;
	}

	public List<String> getSkill() {
		return skill;
	}

	public void setObjectiveDamage(int objectiveDamage) {
		this.objectiveDamage = objectiveDamage;
	}

	public int getObjectiveDamage() {
		return objectiveDamage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}

	public void setDealt(int dealt) {
		this.dealt = dealt;
	}

	public int getDealt() {
		return dealt;
	}

	public void setTaken(int taken) {
		this.taken = taken;
	}

	public int getTaken() {
		return taken;
	}

	public void setDeaths(List<DeathsTimeline> deaths) {
		this.deaths = deaths;
	}

	public List<DeathsTimeline> getDeaths() {
		return deaths;
	}
}
