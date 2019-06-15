package vcoty.vainglory.go.bean.bmob;

import com.google.gson.annotations.SerializedName;

public class BmobFile {
	@SerializedName("__type")
	private String type;
	private String cdn;
	
	@SerializedName("filename")
	private String fileName;
	private String url;


	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setCdn(String cdn) {
		this.cdn = cdn;
	}

	public String getCdn() {
		return cdn;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "{__type:" + type + 
				"\ncdn:" + cdn + 
				"\nfilename:" + fileName + 
				"\nurl:" + url + "}";
	}
}
