package vcoty.vainglory.go.ui.roster;

import android.support.v4.widget.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import com.androidnetworking.error.*;
import com.jakewharton.rxbinding2.view.*;
import com.trello.rxlifecycle2.android.*;
import io.reactivex.android.schedulers.*;
import io.reactivex.functions.*;
import java.util.*;
import java.util.concurrent.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.adapter.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.bean.item.*;
import vcoty.vainglory.go.enums.*;
import vcoty.vainglory.go.mvp.presenter.impl.*;
import vcoty.vainglory.go.mvp.view.*;
import vcoty.vainglory.go.utils.*;

public class StatsFragment extends BaseFragment implements RosterStatsView {

	private String matchId = "";
	private Region region;
	private Map<Boolean,String> myself;
	
	private RecyclerView mRecyclerView;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	
	private View mEmptyView;
	private TextView mTvErrorMessage;
	private Button mBtnReload;
	
	private RosterStatsRootRecyclerAdapter adapter;
	private RosterStatsTelematryPresenter presenter;
	
	public StatsFragment() {

	}

	public StatsFragment(String matchId,Map<Boolean,String> myself,Region region) {
		if (matchId == null || region == null) {
			matchId = "";
			region = null;
		}
		this.matchId = matchId;
		this.region = region;
		this.myself = myself;
	}

	@Override
	protected void initial(View view) {
		mRecyclerView = view.findViewById(R.id.rv_fragment_roster_stats_root);
		mSwipeRefreshLayout = view.findViewById(R.id.srl_roster_fragment);
		
		mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.view_multi_state, mSwipeRefreshLayout, false);
		mTvErrorMessage = mEmptyView.findViewById(R.id.tv_view_multi_state_error);
		mBtnReload = mEmptyView.findViewById(R.id.btn_view_multi_state_reload);
		
		configView();
		
		presenter = new RosterStatsTelematryPresenter();
		presenter.attachView(this);
		if(!matchId.equals("")){
			presenter.bindLifecycle(this).startAnalyzing(matchId,region,myself);
		}
	}


	private void configView() {
		//初始化状态
		mRecyclerView.setVisibility(View.INVISIBLE);
		
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		mRecyclerView.setLayoutManager(layoutManager);
		adapter = new RosterStatsRootRecyclerAdapter(getActivity(), null);
		mRecyclerView.setAdapter(adapter);
		adapter.setEmptyView(mEmptyView);
		
		//刷新
		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

				@Override
				public void onRefresh() {
					if(presenter != null){
						presenter.refresh();
						
					}
				}
			});
	}

	@Override
	public void onResume() {
		if (matchId.equals("")) {
			getActivity().finish();
		}
		super.onResume();
	}

	@Override
	public void onDestroy() {
		presenter.detachView();
		super.onDestroy();
	}
	
	@Override
	public void showLoading() {
		if(!mSwipeRefreshLayout.isRefreshing()){
			mSwipeRefreshLayout.setRefreshing(true);
		}
	}

	@Override
	public void loadData(List<RosterStatsRootItem> data) {
		//如果第一个为敌方的话就反向排序
		if(data.get(0).getTitleRes() == R.string.roster_stats_enemy_side){
			Collections.reverse(data);
		}
		adapter.replaceData(data);
		
	}

	@Override
	public void showError(Throwable error) {
		if(error == null){
			mTvErrorMessage.setText(getResString(R.string.error_unknow) + "(" + getResString(region.getResId()) + ")");
		}else if(error instanceof ANError){
			ANError anError = (ANError) error;
			try {
				mTvErrorMessage.setText(ANErrorConverter.get().convertNormal(getActivity(), anError));
			} catch (Exception e) {
				mTvErrorMessage.setText(getResString(R.string.error_unknow));
			}
			mBtnReload.setVisibility(View.VISIBLE);
		}else{
			mTvErrorMessage.setText(getResString(R.string.error_unknow) + error+ "(" + getResString(region.getResId()) + ")");
		}
		
		//重新加载
		RxView.clicks(mBtnReload)
			.compose(this.<Object>bindUntilEvent(FragmentEvent.DESTROY))
			.throttleFirst(600, TimeUnit.MILLISECONDS)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<Object>(){

				@Override
				public void accept(Object p1) throws Exception {
					presenter.bindLifecycle(StatsFragment.this)
						.refresh();
				}
			});
		mRecyclerView.setAnimation(animationOut());
	}

	@Override
	public void hideLoading() {
		if(mSwipeRefreshLayout.isRefreshing()){
			mSwipeRefreshLayout.setRefreshing(false);
		}
		
		mRecyclerView.setAnimation(animationIn());
		mRecyclerView.setVisibility(View.VISIBLE);
		
	}
	
	private Animation animationIn(){
		return AnimationUtils.loadAnimation(getActivity(), R.anim.abc_fade_in);
	}
	
	private Animation animationOut(){
		return AnimationUtils.loadAnimation(getActivity(), R.anim.abc_fade_out);
	}
	
	@Override
	protected int getLayoutResId() {
		return R.layout.fragment_roster_stats;
	}

}
