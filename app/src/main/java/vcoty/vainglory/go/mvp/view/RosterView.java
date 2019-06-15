package vcoty.vainglory.go.mvp.view;

import java.util.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.bean.item.*;

public interface RosterView extends BaseView {
	/**
	 * 装载完成
	 */
	void loadSuccess(List<RosterOverviewSection> data)
}
