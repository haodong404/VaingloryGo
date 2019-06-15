package vcoty.vainglory.go.bean.item;
import com.chad.library.adapter.base.entity.*;

public class RosterOverviewSection extends SectionEntity{
	
	private RosterOverviewItemHeader header;
	private RosterOverviewItem item;
	
	public RosterOverviewSection(boolean isHeader,String header) {
        super(isHeader,header);
    }

	public RosterOverviewSection setHeader(RosterOverviewItemHeader header) {
		this.header = header;
		return this;
	}

	public RosterOverviewItemHeader getHeader() {
		return header;
	}

	public RosterOverviewSection setItem(RosterOverviewItem item) {
		this.item = item;
		return this;
	}

	public RosterOverviewItem getItem() {
		return item;
	}
}
