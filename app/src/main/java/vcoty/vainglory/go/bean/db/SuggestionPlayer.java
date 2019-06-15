package vcoty.vainglory.go.bean.db;

import org.jetbrains.annotations.*;
import org.litepal.annotation.*;
import org.litepal.crud.*;

public class SuggestionPlayer extends LitePalSupport {

	@Column(unique = true, defaultValue = "unknown")
	private String name; 

	private String region;

	@Override
	public SuggestionPlayer clone() {
		return this;
	}
	
	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegion() {
		return region;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
