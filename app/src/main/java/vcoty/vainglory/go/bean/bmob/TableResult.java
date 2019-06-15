package vcoty.vainglory.go.bean.bmob;

public class TableResult {
	private String objectId;
    private String createdAt;
	private String updatedAt;

	private int tableSize;
	private String tableName;

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

	public void setTableSize(int tableSize) {
		this.tableSize = tableSize;
	}

	public int getTableSize() {
		return tableSize;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}
}
