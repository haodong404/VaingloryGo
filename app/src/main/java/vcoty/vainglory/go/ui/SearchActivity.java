package vcoty.vainglory.go.ui;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import com.afollestad.materialdialogs.*;
import com.jakewharton.rxbinding2.view.*;
import com.trello.rxlifecycle2.android.*;
import java.util.concurrent.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.enums.*;
import vcoty.vainglory.go.ui.setting.*;
import vcoty.vainglory.go.utils.*;
import vcoty.vainglory.go.widget.*;

import vcoty.vainglory.go.R;
import vcoty.vainglory.go.enums.Region;

public class SearchActivity extends BaseActivity {

	//ui
	private CardView mSearchBoxCv;
	private Button mToSettingBtn;
	private CircleTextImageView mLogoCircleTextIv;
	private ImageView mSearchIconIv;
	private TextView mSearchBoxTv;
	
	private Typeface mTfNerisBlack;
	
	private int duration = 350;
	
	@Override
	protected void initial() {
		//转跳
		String name = getPreference().getString(PreferenceKey.SETTING_JUMP,"");
		if(!name.equals("")){
			Intent mIntent = new Intent(this,MainActivity.class);
			mIntent.putExtra(ExtraKey.EXTRA_KEY_PLAYER_NAME, name);
			mIntent.putExtra(ExtraKey.EXTRA_KEY_REGION, getPreference().getString(PreferenceKey.SETTING_REGIONS, Region.CHINA.value()));
			mIntent.putExtra(ExtraKey.EXTRA_KEY_BOOLEAN_1, false);
			startActivity(mIntent);
		}
		
		mTfNerisBlack = Typeface.createFromAsset(this.getAssets(), "Neris-Black.otf");
		
		initView();
		
		//初始化地区
		initRegion();
		
		//权限申请
		if((Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)){
			new MaterialDialog.Builder(this)
				.title(R.string.search_activity_version_dialog_title)
				.content(R.string.search_activity_version_dialog_message)
				.positiveText(R.string.search_activity_version_dialog_positive)
				.onPositive(new MaterialDialog.SingleButtonCallback(){

					@Override
					public void onClick(MaterialDialog dialog, DialogAction p2) {
						finish();
						System.exit(0);
					}
				})
				.cancelable(false)
				.show();
		}
	}

	private void initRegion() {
		boolean isFirstTime = getSharedPreferences(PreferenceKey.IS_FIRST_TIME_PREFERENCE,Context.MODE_PRIVATE).getBoolean(PreferenceKey.IS_FIRST_TIME,true);
		if(isFirstTime){
			new RegionSelectDialog(getPreference())
				.creat(this)
				.setTitle(R.string.search_activity_initial_region)
				.loadView()
				.setOnRegionSelectedListener(new RegionSelectDialog.OnRegionSelectedListener(){

					@Override
					public void onRegionSelected(Region region) {
						Snackbar.make(mSearchBoxCv,getResString(R.string.search_activity_have_set) + getResString(region.getResId()),Snackbar.LENGTH_SHORT).show();
					}
				})
				.setOnDismissListener(new DialogInterface.OnDismissListener(){

					@Override
					public void onDismiss(DialogInterface p1) {
						Snackbar.make(mSearchBoxCv,getResString(R.string.search_activity_have_set) + getResString(Region.CHINA.getResId()),Snackbar.LENGTH_SHORT).show();
					}
				})
				.show();
			getSharedPreferences(PreferenceKey.IS_FIRST_TIME_PREFERENCE,Context.MODE_PRIVATE).edit().putBoolean(PreferenceKey.IS_FIRST_TIME,false).commit();
		}
	}

	// 初始化控件 //
	private void initView() {
		mSearchBoxCv = (CardView) findViewById(R.id.cv_search_start_box);
		
		RxView.clicks(mSearchBoxCv)
			.compose(this.<Object>bindUntilEvent(ActivityEvent.DESTROY))
			.throttleFirst(600,TimeUnit.MILLISECONDS)
			.subscribe(new io.reactivex.functions.Consumer<Object>(){

				@Override
				public void accept(Object p1) throws Exception {
					startActivity(new Intent(SearchActivity.this, SearchBarActivity.class)
								  , ActivityOptions.makeSceneTransitionAnimation(SearchActivity.this, mSearchBoxCv, "sharedView").toBundle());
					animationOut();
					
				}
			});
			
		mSearchBoxCv.setOnTouchListener(new View.OnTouchListener(){

				@Override
				public boolean onTouch(View p1, MotionEvent motionEvent) {
					switch (motionEvent.getAction()) {
						case MotionEvent.ACTION_DOWN:
							ObjectAnimator upAnim = ObjectAnimator.ofFloat(mSearchBoxCv, "translationZ", 16);
							upAnim.setDuration(150);
							upAnim.setInterpolator(new DecelerateInterpolator());
							upAnim.start();
							break;

						case MotionEvent.ACTION_UP:
						case MotionEvent.ACTION_CANCEL:
							ObjectAnimator downAnim = ObjectAnimator.ofFloat(mSearchBoxCv, "translationZ", 0);
							downAnim.setDuration(150);
							downAnim.setInterpolator(new AccelerateInterpolator());
							downAnim.start();
							break;
					}
					return false;
				}
			});
		
		//logo的初始化
		mLogoCircleTextIv = (CircleTextImageView) findViewById(R.id.circle_text_iv_search_icon);
		mLogoCircleTextIv.setTextColor(Color.WHITE);
		mLogoCircleTextIv.setTypeface(mTfNerisBlack);
		
		mToSettingBtn = (Button) findViewById(R.id.btn_search_setting);
		RxView.clicks(mToSettingBtn)
			.compose(this.<Object>bindUntilEvent(ActivityEvent.DESTROY))
			.throttleFirst(600,TimeUnit.MILLISECONDS)
			.subscribe(new io.reactivex.functions.Consumer<Object>(){

				@Override
				public void accept(Object p1) throws Exception {
					startActivity(new android.content.Intent(SearchActivity.this,SettingActivity.class));
				}
			});
			
		mSearchIconIv = (ImageView) findViewById(R.id.iv_search_start_box_icon);
		mSearchBoxTv = (TextView) findViewById(R.id.tv_search_start_box_text);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		animationIn();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	//进入时动画
	private void animationIn() {
		if (true) {
			ObjectAnimator.ofFloat(mSearchIconIv, "alpha", 0f, 1f)
				.setDuration(duration)
				.start();

			ObjectAnimator.ofFloat(mSearchBoxTv, "alpha", 0f, 0.54f)
				.setDuration(duration)
				.start();
		}
	}

	//退出时动画
	private void animationOut() {
		ObjectAnimator.ofFloat(mSearchIconIv, "alpha", 1f, 0f)
			.setDuration(duration)
			.start();

		ObjectAnimator.ofFloat(mSearchBoxTv, "alpha", 0.54f, 0f)
			.setDuration(duration)
			.start();
	}
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		VaingloryGo vg = (VaingloryGo) getApplication();
		vg.removeAllActivitys();
		System.exit(0);
	}
	
	@Override
	protected int getLayoutResId() {
		return R.layout.search_activity;
	}

}
