package vcoty.vainglory.go.widget;

import android.content.*;
import android.view.*;
import android.widget.*;
import com.chad.library.adapter.base.loadmore.*;
import vcoty.vainglory.go.*;

public class MatchLoadMoreView extends LoadMoreView {


	private final int layoutResId = R.layout.view_match_load_view;
	
	public MatchLoadMoreView(Context ctx){
		
	}
	
	@Override
	public int getLayoutId() {
		// TODO: Implement this method
		return layoutResId;
	}

	@Override
	protected int getLoadingViewId() {
		// TODO: Implement this method
		return R.id.pb_view_load_more;
	}

	@Override
	protected int getLoadFailViewId() {
		// TODO: Implement this method
		return R.id.view_match_load_view_error;
	}
	
	@Override
	protected int getLoadEndViewId() {
		// TODO: Implement this method
		return R.id.view_match_load_viewTextView;
	}
}
