package vcoty.vainglory.go.bean.db;

import org.litepal.annotation.*;
import org.litepal.crud.*;

public class Outfit extends LitePalSupport {
	
	@Column(nullable = false)
	private int id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	
	private String url;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
