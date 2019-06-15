package vcoty.vainglory.go.bean.item;

import vcoty.vainglory.go.enums.Region;

public class RegionPickerItem {
	private Region region;
	private int regionNameId;
	private boolean isPicked;

	public void setRegionNameId(int regionNameId) {
		this.regionNameId = regionNameId;
	}

	public int getRegionNameId() {
		return regionNameId;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Region getRegion() {
		return region;
	}


	public void setIsPicked(boolean isPicked) {
		this.isPicked = isPicked;
	}

	public boolean isPicked() {
		return isPicked;
	}

	@Override
	public String toString() {
		return "[RegionPickerItem]\nregion = " + region.value() + "\nnameId = " + regionNameId + "isPicked = " + isPicked ;
	}
}
