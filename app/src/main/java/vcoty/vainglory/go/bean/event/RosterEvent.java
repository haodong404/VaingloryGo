package vcoty.vainglory.go.bean.event;

import java.util.*;
import vcoty.vainglory.go.bean.item.*;

public class RosterEvent {
	
	
	private List<RosterOverviewSection> data;

	public RosterEvent(){}
	
	public RosterEvent(List<RosterOverviewSection> data) {
		this.data = data;
	}

	public void setData(List<RosterOverviewSection> data) {
		this.data = data;
	}

	public List<RosterOverviewSection> getData() {
		return data;
	}
}
