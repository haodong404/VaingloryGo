package vcoty.vainglory.go.bean.item;

public class RosterItemsItem {
	
	private int id;
	private String url;

	public RosterItemsItem(){}
	
	public RosterItemsItem(int id, String url) {
		this.id = id;
		this.url = url;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
