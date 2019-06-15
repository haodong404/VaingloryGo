package vcoty.vainglory.go.bean.bmob;

public class OutfitImg {
	private String createdAt;
	private String updatedAt;

	private String apiName;
	private int outfitID;
	private BmobFile outfitIcon;

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

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getApiName() {
		return apiName;
	}

	public void setOutfitID(int outfitID) {
		this.outfitID = outfitID;
	}

	public int getOutfitID() {
		return outfitID;
	}

	public void setOutfitIcon(BmobFile outfitIcon) {
		this.outfitIcon = outfitIcon;
	}

	public BmobFile getOutfitIcon() {
		return outfitIcon;
	}
}
