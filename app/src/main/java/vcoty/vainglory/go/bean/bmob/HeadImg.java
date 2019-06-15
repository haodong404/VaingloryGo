package vcoty.vainglory.go.bean.bmob;

public class HeadImg {
	private String createdAt;
    private BmobFile headImg;
    private int heroID;
    private String heroNameCN;
    private String heroNameEN;
    private String heroTitle;
    private String objectId;
    private String updatedAt;

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setHeadImg(BmobFile headImg) {
		this.headImg = headImg;
	}

	public BmobFile getHeadImg() {
		return headImg;
	}

	public void setHeroID(int heroID) {
		this.heroID = heroID;
	}

	public int getHeroID() {
		return heroID;
	}

	public void setHeroNameCN(String heroNameCN) {
		this.heroNameCN = heroNameCN;
	}

	public String getHeroNameCN() {
		return heroNameCN;
	}

	public void setHeroNameEN(String heroNameEN) {
		this.heroNameEN = heroNameEN;
	}

	public String getHeroNameEN() {
		return heroNameEN;
	}

	public void setHeroTitle(String heroTitle) {
		this.heroTitle = heroTitle;
	}

	public String getHeroTitle() {
		return heroTitle;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}
}
