package vcoty.vainglory.go.bean.bmob;

public class UpdateRequest {
	private BmobFile apk;

    private String objectId;
    private String createdAt;
	private String updatedAt;
    private int versionCode;
    private String versionName;
	private BmobFile versionDescription ;
	


	public void setApk(BmobFile apk) {
		this.apk = apk;
	}

	public BmobFile getApk() {
		return apk;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionDescription(BmobFile versionDescription) {
		this.versionDescription = versionDescription;
	}

	public BmobFile getVersionDescription() {
		return versionDescription;
	}
}
