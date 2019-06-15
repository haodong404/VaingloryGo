package vcoty.vainglory.go.ui.setting;

import vcoty.vainglory.go.R;
import vcoty.vainglory.go.base.BaseActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.app.FragmentTransaction;

public class SettingActivity extends BaseActivity {

	private Toolbar mToolBar;
	
	
	@Override
	protected void initial() {
		mToolBar = (Toolbar) findViewById(R.id.tb_setting);
		mToolBar.setNavigationOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1) {
					finish();
				}
			});
			
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.add(R.id.fl_setting_content,new SettingPreference());
		transaction.commit();
		
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.setting_activity;
	}
}
