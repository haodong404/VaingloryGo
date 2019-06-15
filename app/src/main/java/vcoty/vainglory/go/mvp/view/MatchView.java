package vcoty.vainglory.go.mvp.view;

import vcoty.vainglory.go.base.BaseView;

import java.util.List;
import vcoty.vainglory.go.bean.item.MatchsListItem;

public interface MatchView extends BaseView {

	/**
	 * 装载数据
	 */
	void loadData(List<MatchsListItem> result)
	
	/**
	 * 加载更多完成
	 */
	void loadMoreData(List<MatchsListItem> result)
}
