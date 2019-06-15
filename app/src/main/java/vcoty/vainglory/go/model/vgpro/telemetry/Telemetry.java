package vcoty.vainglory.go.model.vgpro.telemetry;

public class Telemetry {
	private String id;
	private Facts facts;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setFacts(Facts facts) {
		this.facts = facts;
	}

	public Facts getFacts() {
		return facts;
	}
}
