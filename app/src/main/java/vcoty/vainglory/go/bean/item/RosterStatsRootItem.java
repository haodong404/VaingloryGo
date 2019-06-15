package vcoty.vainglory.go.bean.item;

import com.razerdp.widget.animatedpieview.data.*;
import java.text.*;
import java.util.*;

public class RosterStatsRootItem {
	
	private int titleRes;
	private String total;
	private List<SimplePieInfo> pieInfos;
	private List<RosterStatsPlayerItem> items;

	public void setTotal(int total) {
		DecimalFormat df = new DecimalFormat("######0.00");
		this.total = df.format((float) total / 1000);
	}

	public String getTotal() {
		return total;
	}

	public void setPieInfos(List<SimplePieInfo> pieInfos) {
		this.pieInfos = pieInfos;
	}

	public List<SimplePieInfo> getPieInfos() {
		return pieInfos;
	}

	public void setTitleRes(int titleRes) {
		this.titleRes = titleRes;
	}

	public int getTitleRes() {
		return titleRes;
	}

	public void setItems(List<RosterStatsPlayerItem> items) {
		this.items = items;
	}

	public List<RosterStatsPlayerItem> getItems() {
		return items;
	}
}
