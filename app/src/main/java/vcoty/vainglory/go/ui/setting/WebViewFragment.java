package vcoty.vainglory.go.ui.setting;

import android.view.*;
import android.webkit.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.widget.*;

public class WebViewFragment extends BaseFragment implements FullScreenDialogContent {

	@Override
	public boolean onExtraActionClick(MenuItem actionItem, FullScreenDialogController dialogController) {
		// TODO: Implement this method
		return false;
	}
	
	private WebView mWebView;
	
	@Override
	protected void initial(View view) {
		mWebView = (WebView) view.findViewById(R.id.wv_view_web);
		mWebView.loadUrl("file:///android_asset/open_source.html");
	}
	
	@Override
	public void onDialogCreated(FullScreenDialogController p1) {
		
	}

	@Override
	public boolean onConfirmClick(FullScreenDialogController p1) {	
		return false;
	}

	@Override
	public boolean onDiscardClick(FullScreenDialogController p1) {
		return false;
	}
	
	@Override
	protected int getLayoutResId() {
		return R.layout.view_web_view;
	}

}
