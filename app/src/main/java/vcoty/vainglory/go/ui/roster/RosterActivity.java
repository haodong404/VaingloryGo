package vcoty.vainglory.go.ui.roster;

import android.support.design.widget.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.trello.rxlifecycle2.android.*;
import io.reactivex.android.schedulers.*;
import io.reactivex.functions.*;
import io.reactivex.schedulers.*;
import java.util.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.adapter.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.bean.*;
import vcoty.vainglory.go.bean.event.*;
import vcoty.vainglory.go.bean.item.*;
import vcoty.vainglory.go.enums.*;
import vcoty.vainglory.go.mvp.presenter.impl.*;
import vcoty.vainglory.go.mvp.view.*;
import vcoty.vainglory.go.utils.*;
import vcoty.vainglory.go.widget.*;

import android.support.v7.widget.Toolbar;

public class RosterActivity extends BaseActivity implements RosterView {

	private Toolbar mToolbar;
	private TextView mTvDate;
	private TextView mTvDuration;
	private TabLayout mTabLayout;
	private LazyViewPager mViewPager;

	private RostersLoaderPresenter presenter;
	
	@Override
	protected void initial() {
		mToolbar = (Toolbar) findViewById(R.id.tb_roster_activity);
		mTvDate = (TextView) findViewById(R.id.tv_roster_activity_date);
		mTvDuration = (TextView) findViewById(R.id.tv_roster_activity_time);
		mTabLayout = (TabLayout) findViewById(R.id.tl_roster_tablayout);
		mViewPager = (LazyViewPager) findViewById(R.id.lazy_vp_roster_view_pager);
		
		presenter = new RostersLoaderPresenter();
		presenter.attachView(this);
		
		RxBus.getDefault().toObservableSticky(RosterInformationEntity.class)
			.take(1)
			.compose(this.<RosterInformationEntity>bindUntilEvent(ActivityEvent.DESTROY))
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<RosterInformationEntity>(){

				@Override
				public void accept(RosterInformationEntity data) throws Exception {
					if(data != null){
						configView(data.getMatchId(),data.getMyself());
						mToolbar.setTitle(getIsWinString(data.isWin()));
						mToolbar.setSubtitle(data.getGameMode().getStringResId());

						mTvDate.setText(data.getDate());
						mTvDuration.setText(data.getDirection());

						presenter.bindLifecycle(RosterActivity.this).loadOverviews(data);

					}
				}

			});
		
	}
	

	@Override
	protected void onResume() {
		super.onResume();
		
		VaingloryGo vgo = (VaingloryGo) getApplication();
		try {
			if(vgo.getActivitys() != null && vgo.getActivitys().size() == 1){
				finish();
			}
		} catch (Exception e) {}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.detachView();
	}
	
	private void configView(String matchId,Map<Boolean,String> myself) {
		mToolbar.setNavigationOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1) {
					finish();
				}
			});
		
		List<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(new OverviewFragment());
		fragments.add(new StatsFragment(matchId,myself,Region.parse(getPreference().getString(PreferenceKey.SETTING_REGIONS,"cn"))));
		
		String[] titles = {getResString(R.string.roster_tablayout_title_details),getResString(R.string.roster_tablayout_title_stats)};
		RosterTablayoutAdapter adapter = new RosterTablayoutAdapter(getSupportFragmentManager(),titles,fragments);
		mViewPager.setAdapter(adapter);
		mTabLayout.setupWithViewPager(mViewPager);
		
	}

	@Override
	public void showLoading() {
		
	}

	@Override
	public void loadSuccess(List<RosterOverviewSection> data) {
		RxBus.getDefault().post(new RosterEvent(data));
	}

	@Override
	public void showError(Throwable error) {
		shortToast(error + "");
	}

	@Override
	public void hideLoading() {
		
	}
	
	private int getIsWinString(boolean isWin){
		if(isWin){
			return R.string.match_item_victory;
		}else{
			return R.string.match_item_defeat;
		}
	}
	
	@Override
	protected int getLayoutResId() {
		return R.layout.roster_activity;
	}
	
}
