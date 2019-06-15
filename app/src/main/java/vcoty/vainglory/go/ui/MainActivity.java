package vcoty.vainglory.go.ui;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import com.jakewharton.rxbinding2.view.*;
import com.trello.rxlifecycle2.android.*;
import java.util.*;
import java.util.concurrent.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.adapter.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.ui.match.*;
import vcoty.vainglory.go.ui.profile.*;
import vcoty.vainglory.go.ui.setting.*;
import vcoty.vainglory.go.widget.*;

import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import vcoty.vainglory.go.utils.*;

public class MainActivity extends BaseActivity {

	//UI
	private TabLayout mTabLayout;
	private LazyViewPager mViewPager;
	private ImageView mMenuIcon;
	private CardView mSearchBarCard;
	private TextView mSearchBarText;
	private ImageView mSearchBarIcon;

	private int duration = 350;
	private int durationIn = 200;
	private long exitTime = 0;
	
	private Typeface mTfNerisBlack;
	
	@Override
	protected void initial() {
		
		mTabLayout = (TabLayout) findViewById(R.id.tl_main_tablayout);
		mViewPager = (LazyViewPager) findViewById(R.id.lazy_vp_main_view_pager);
		mMenuIcon = (ImageView) findViewById(R.id.iv_main_activity_menu);
		mSearchBarCard = (CardView) findViewById(R.id.cv_main_activity_search_bar);
		mSearchBarText = (TextView) findViewById(R.id.tv_main_activity_bar);
		mSearchBarIcon = (ImageView) findViewById(R.id.iv_main_activity_search);
		
		mTfNerisBlack = Typeface.createFromAsset(this.getAssets(), "Neris-Black.otf");
		
		config();
		
		if(!getIntent().getBooleanExtra(ExtraKey.EXTRA_KEY_BOOLEAN_1,false)){
			clearActivitys();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		animationIn();
	}

	/**
	 * 配置View
	 */
	private void config() {
		//SearchBar上的字体
		mSearchBarText.setTypeface(mTfNerisBlack);
		
		//SearchBar点击动画
		searchBarAin();
		
		RxView.clicks(mSearchBarCard)
			.compose(this.<Object>bindUntilEvent(ActivityEvent.DESTROY))
			.throttleFirst(600, TimeUnit.MILLISECONDS)
			.subscribe(new io.reactivex.functions.Consumer<Object>(){

				@Override
				public void accept(Object p1) throws Exception {
					startActivity(new Intent(MainActivity.this, SearchBarActivity.class)
								  , ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, mSearchBarCard, "sharedView").toBundle());

					animationOut();
				}
			});

		RxView.clicks(mMenuIcon)
			.compose(this.<Object>bindUntilEvent(ActivityEvent.DESTROY))
			.throttleFirst(600, TimeUnit.MILLISECONDS)
			.subscribe(new io.reactivex.functions.Consumer<Object>(){
				@Override
				public void accept(Object p1) throws Exception {
					PopupMenu mPopupMenu = new PopupMenu(MainActivity.this, mSearchBarIcon);

					mPopupMenu.getMenuInflater().inflate(R.menu.menu_main_toolbar, mPopupMenu.getMenu());

					mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

							@Override
							public boolean onMenuItemClick(MenuItem p1) {
								switch (p1.getItemId()) {
									case R.id.item_setting:
										startActivity(new Intent(MainActivity.this, SettingActivity.class));
										break;
								}
								return false;
							}
						});

					mPopupMenu.show();

				}
			});

		configTab();
	}
	
	private void configTab() {
		List<Fragment> fragment = new ArrayList<Fragment>();
		//获取需要传的值
		Bundle mBundle = new Bundle();
		mBundle.putString(ExtraKey.EXTRA_KEY_PLAYER_NAME, getIntent().getStringExtra(ExtraKey.EXTRA_KEY_PLAYER_NAME));
		mBundle.putString(ExtraKey.EXTRA_KEY_REGION, getIntent().getStringExtra(ExtraKey.EXTRA_KEY_REGION));
		//绑定上去
		MatchFragment mMatchFragment = new MatchFragment();
		mMatchFragment.setArguments(mBundle);

		ProfileFragment mProfileFragment = new ProfileFragment();
		mProfileFragment.setArguments(mBundle);

		fragment.add(mMatchFragment);
		fragment.add(mProfileFragment);
		MainTablayoutAdapter adapter = new MainTablayoutAdapter(getSupportFragmentManager(), fragment);
		mViewPager.setAdapter(adapter);

		//与viewpager关联
		mTabLayout.setupWithViewPager(mViewPager);
		mTabLayout.getTabAt(0).setIcon(R.drawable.ic_sword_cross);
		mTabLayout.getTabAt(1).setIcon(R.drawable.ic_account_unselect);

		//tab选择监听
		mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

				@Override
				public void onTabSelected(TabLayout.Tab p1) {
					if (p1 == mTabLayout.getTabAt(0)) {
						mTabLayout.getTabAt(0).setIcon(R.drawable.ic_sword_cross);
						mTabLayout.getTabAt(1).setIcon(R.drawable.ic_account_unselect);

					} else if (p1 == mTabLayout.getTabAt(1)) {
						mTabLayout.getTabAt(0).setIcon(R.drawable.ic_sword_cross_unselect);
						mTabLayout.getTabAt(1).setIcon(R.drawable.ic_account);	

					}
				}
				@Override
				public void onTabUnselected(TabLayout.Tab p1) {}

				@Override
				public void onTabReselected(TabLayout.Tab p1) {}

			});
	}
	
	private void clearActivitys(){
		//清空之前的activity
		VaingloryGo vgo = (VaingloryGo) this.getApplication();
		try {
			for (Activity act : vgo.getActivitys()) {
				if (act instanceof SearchBarActivity) {
					act.finish();
				} else if (act instanceof SearchActivity) {
					act.finish();
				}
			}
		} catch (Exception e) {}
		
	}
	
	private void animationOut() {
		ObjectAnimator.ofFloat(mSearchBarText, "alpha", 0.54f, 0f)
			.setDuration(duration)
			.start();

		ObjectAnimator.ofFloat(mSearchBarIcon, "alpha", 1f, 0f)
			.setDuration(duration)
			.start();

		ObjectAnimator.ofFloat(mMenuIcon, "alpha", 1f, 0f)
			.setDuration(duration)
			.start();
	}

	private void animationIn() {
		ObjectAnimator oa = ObjectAnimator.ofFloat(mSearchBarText, "alpha", 0f, 0.54f);
		oa.setDuration(durationIn);
		oa.start();

		ObjectAnimator.ofFloat(mSearchBarIcon, "alpha", 0f, 1f)
			.setDuration(durationIn)
			.start();

		ObjectAnimator.ofFloat(mMenuIcon, "alpha", 0f, 1f)
			.setDuration(durationIn)
			.start();
	}
	
	// 双击退出 //
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && getIntent().getBooleanExtra("isLast", true)) {
			Fragment fm = getSupportFragmentManager().findFragmentByTag("PlayedHeroesDialog");
			if(fm != null || getIntent().getBooleanExtra(ExtraKey.EXTRA_KEY_BOOLEAN_1,false)){
				return super.onKeyDown(keyCode, event);
			}
			
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), R.string.main_exit_protect, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
				this.removeAllActivitys();
				System.exit(0);     
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

	/**
	 * SearchBar点击的动画
	 */
	private void searchBarAin() {
		mSearchBarCard.setOnTouchListener(new View.OnTouchListener(){

				@Override
				public boolean onTouch(View v, MotionEvent motionEvent) {
					switch (motionEvent.getAction()) {
						case MotionEvent.ACTION_DOWN:
							ObjectAnimator upAnim = ObjectAnimator.ofFloat(v, "translationZ", 16);
							upAnim.setDuration(150);
							upAnim.setInterpolator(new DecelerateInterpolator());
							upAnim.start();
							break;

						case MotionEvent.ACTION_UP:
						case MotionEvent.ACTION_CANCEL:
							ObjectAnimator downAnim = ObjectAnimator.ofFloat(v, "translationZ", 0);
							downAnim.setDuration(150);
							downAnim.setInterpolator(new AccelerateInterpolator());
							downAnim.start();
							break;
					}
					return false;
				}
			});
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.main_activity;
	}
}
