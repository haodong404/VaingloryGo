package vcoty.vainglory.go.bean.db;

import org.litepal.crud.*;
import org.litepal.annotation.*;

public class Hero extends LitePalSupport {
	
	private int id;
	
	@Column(unique = true, defaultValue = "unknown")
	private String nameCN;
	
	private String nameEN;
	private String nameTitle;
	private String url;
	private String fullName;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setNameCN(String nameCN) {
		this.nameCN = nameCN;
	}

	public String getNameCN() {
		return nameCN;
	}

	public void setNameEN(String nameEN) {
		this.nameEN = nameEN;
	}

	public String getNameEN() {
		return nameEN;
	}

	public void setNameTitle(String nameTitle) {
		this.nameTitle = nameTitle;
	}

	public String getNameTitle() {
		return nameTitle;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return fullName;
	}
}
