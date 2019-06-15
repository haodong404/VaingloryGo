package vcoty.vainglory.go.model.vgpro.telemetry;
import java.util.*;

public class Facts {
	private Map<String,Actor> red;
	
	private Map<String,Actor> blue;

	public void setRed(Map<String, Actor> red) {
		this.red = red;
	}

	public Map<String, Actor> getRed() {
		return red;
	}

	public void setBlue(Map<String, Actor> blue) {
		this.blue = blue;
	}

	public Map<String, Actor> getBlue() {
		return blue;
	}
}
