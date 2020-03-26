package vcoty.vainglory.go.ui.match;

import android.content.*;
import android.support.v4.widget.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import com.androidnetworking.error.*;
import com.chad.library.adapter.base.*;
import com.jakewharton.rxbinding2.view.*;
import com.tencent.bugly.crashreport.*;
import com.trello.rxlifecycle2.android.*;
import io.reactivex.android.schedulers.*;
import io.reactivex.functions.*;
import java.util.*;
import java.util.concurrent.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.adapter.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.bean.*;
import vcoty.vainglory.go.bean.item.*;
import vcoty.vainglory.go.enums.*;
import vcoty.vainglory.go.mvp.presenter.impl.*;
import vcoty.vainglory.go.mvp.view.*;
import vcoty.vainglory.go.ui.roster.*;
import vcoty.vainglory.go.utils.*;
import vcoty.vainglory.go.widget.*;

import android.support.v7.widget.PopupMenu;
import vcoty.vainglory.go.R;

public class MatchFragment extends BaseFragment implements MatchView {

    // UI
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mTvModeSelector;
    private LinearLayout mLlModeSelector;

    private View mEmptyView;
    private TextView mTvErrorMessage;
    private Button mBtnReload;

    private SearchMatchPresenter presenter;

    private MatchsRecyclerAdapter adapter;

    private String playerName;
    private Region region;
    private int page;
    private GameMode gameMode;
    private boolean loadAnimationFlag = true;

    private boolean selectFlag = false;

    @Override
    protected void initial(View view) {

	mRecyclerView = view.findViewById(R.id.rv_match_fragment);
	mSwipeRefreshLayout = view.findViewById(R.id.srl_match_fragment);
	mTvModeSelector = view.findViewById(R.id.tv_view_head_match_list);
	mLlModeSelector = view.findViewById(R.id.ll_view_head_match_list);

	LinearLayout rootLayout = view.findViewById(R.id.ll_match_fragment_root);

	mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.view_multi_state, rootLayout, false);
	mTvErrorMessage = mEmptyView.findViewById(R.id.tv_view_multi_state_error);
	mBtnReload = mEmptyView.findViewById(R.id.btn_view_multi_state_reload);

	// 初始化presenter //
	presenter = new SearchMatchPresenter();
	presenter.attachView(this);

	//接受数据
	if (isAdded()) {
	    playerName = getArguments().getString(ExtraKey.EXTRA_KEY_PLAYER_NAME);
	    region = Region.parse(getArguments().getString(ExtraKey.EXTRA_KEY_REGION));
	    page = 0;
	    gameMode = GameMode.parse(getModesPreference().getString(PreferenceKey.MODE_KEY, ""));
	    initView();

	    presenter.bindLifecycle(this)
		.queryMatch(playerName, region, gameMode, page);
	}
    }

    @Override
    public void onResume() {
	super.onResume();
	region = Region.parse(getPreference().getString(PreferenceKey.SETTING_REGIONS,Region.CHINA.value()));
	if (presenter == null) {
	    presenter = new SearchMatchPresenter();
	    presenter.attachView(this);
	}
    }

    @Override
    public void onDestroy() {
	super.onDestroy();
	presenter.detachView();
    }

    private void initView() {
	LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

	mRecyclerView.setLayoutManager(layoutManager);
	//分割线
	mRecyclerView.addItemDecoration(new NormalItemDecoration.Builder(getActivity())
		.colorResId(AttrResourceUtil.getAttrResId(getActivity(), R.attr.colorDivider))
		.sizeResId(R.dimen.divider_size)
		.marginResId(R.dimen.divider_margin_left, R.dimen.divider_margin_right)
		.build());
	adapter = new MatchsRecyclerAdapter(getActivity(), new ArrayList<MatchsListItem>());
	mRecyclerView.setAdapter(adapter);
	mRecyclerView.setVisibility(View.INVISIBLE);
	adapter.setEmptyView(mEmptyView);
	adapter.setLoadMoreView(new MatchLoadMoreView(getActivity()));
	adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){

	    @Override
	    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
		if(adapter.getData().get(position) instanceof MatchsListItem){
		    MatchsListItem t = (MatchsListItem) adapter.getData().get(position);
		    startActivity(new Intent(getActivity(), RosterActivity.class));
		    RxBus.getDefault().postSticky(new RosterInformationEntity(t.getRosters(), t.getGameMode(), t.getDirection(), t.getDate(), t.isWon(), playerName, t.getSkillTier(),t.getId(),t.getMyself(),t.getMvpPlayer()));

		}
	    }
	});

	// 下拉刷新 //
	configRefresh();

	//初始化MODE Selector
	mTvModeSelector.setText(GameMode.parse(getModesPreference().getString(PreferenceKey.MODE_KEY, "")).getStringResId());
	RxView.clicks(mLlModeSelector)
	    .compose(MatchFragment.this.<Object>bindUntilEvent(FragmentEvent.DESTROY))
	    .throttleFirst(600, TimeUnit.MILLISECONDS)
	    .observeOn(AndroidSchedulers.mainThread())
	    .subscribe(new Consumer<Object>(){

		@Override
		public void accept(Object p1) throws Exception {
		    PopupMenu mPopupMenu = new PopupMenu(MatchFragment.this.getActivity(), mTvModeSelector);
		    mPopupMenu.getMenuInflater().inflate(R.menu.menu_match_mode_selector, mPopupMenu.getMenu());

		    mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem p1) {
			    mLlModeSelector.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.abc_fade_out));
			    mTvModeSelector.setText(GameMode.convertById(getActivity(), p1.getItemId()));
			    mLlModeSelector.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.abc_fade_in));
			    gameMode = GameMode.parseById(p1.getItemId());
			    selectFlag = true;
			    page = 0;
			    MatchFragment.this.getActivity().getSharedPreferences(PreferenceKey.MODES_PREFERENCE, Context.MODE_PRIVATE).edit().putString(PreferenceKey.MODE_KEY,gameMode.getContent());
			    presenter.bindLifecycle(MatchFragment.this)
				.refreshData(region)
				.refreshMode(gameMode)
				.refreshMatch();
			    return false;
			}
		    });

		    mPopupMenu.show();
		}
	    });

	adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener(){

	    @Override
	    public void onLoadMoreRequested() {
		page += 10;
		selectFlag = false;
		presenter.loadMore(page);

	    }
	});
    }

    private void configRefresh() {
	mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

	    @Override
	    public void onRefresh() {
		//错误信息动画
		mBtnReload.setVisibility(View.VISIBLE);
		page = 0;
		presenter.bindLifecycle(MatchFragment.this)
		    .refreshData(region)
		    .refreshMatch();
	    }
	});
    }

    @Override
    public void loadData(List<MatchsListItem> result) {

	loadAnimationFlag = true;
	adapter.setNewData(result);
    }

    @Override
    public void loadMoreData(List<MatchsListItem> result) {
	if (result == null || result.isEmpty()) {
	    adapter.loadMoreEnd();
	} else if (result.size() == 10) {
	    adapter.addData(result);
	    adapter.loadMoreComplete();
	} else {
	    adapter.addData(result);
	    adapter.loadMoreEnd();
	}
    }

    @Override
    public void showLoading() {

	//初始化多状态
	mBtnReload.setVisibility(View.GONE);
	mTvErrorMessage.setText(getResString(R.string.error_404_text));

	if (!mSwipeRefreshLayout.isRefreshing()) {
	    mSwipeRefreshLayout.setRefreshing(true);
	}

	//注册用户名至Bugly
	CrashReport.setUserId(playerName);
    }

    @Override
    public void hideLoading() {

	if (mSwipeRefreshLayout.isRefreshing()) {
	    mSwipeRefreshLayout.setRefreshing(false);
	}

	showRootLayoutInAnimation();
    }

    @Override
    public void showError(Throwable error) {
	showRootLayoutInAnimation();

	if(selectFlag && adapter.getData().size() != 0){
	    adapter.replaceData(new ArrayList<MatchsListItem>());
	}

	if (error == null) {
	    mTvErrorMessage.setText(getResString(R.string.error_unknow) + "(" + getResString(region.getResId()) + ")");
	} else {
	    if (error instanceof ANError) {
		ANError anError = (ANError) error;

		Animation alphaAni = AnimationUtils.loadAnimation(getActivity(), R.anim.abc_fade_in);

		mBtnReload.setVisibility(View.VISIBLE);
		mBtnReload.startAnimation(alphaAni);
		try {
		    mTvErrorMessage.setText(ANErrorConverter.get().convertNormal(getActivity(), anError) + "(" + getResString(region.getResId()) + ")");
		} catch (Exception e) {
		    mTvErrorMessage.setText(getResString(R.string.error_unknow));
		}
		mTvErrorMessage.startAnimation(alphaAni);

		if (mRecyclerView.isAttachedToWindow() && adapter != null) {
		    adapter.loadMoreEnd();
		}
	    } else {
		mTvErrorMessage.setText(getResString(R.string.error_unknow) + error + "(" + getResString(region.getResId()) + ")");
	    }
	}
	//重新加载
	RxView.clicks(mBtnReload)
	    .compose(MatchFragment.this.<Object>bindUntilEvent(FragmentEvent.DESTROY))
	    .throttleFirst(600, TimeUnit.MILLISECONDS)
	    .observeOn(AndroidSchedulers.mainThread())
	    .subscribe(new Consumer<Object>(){

		@Override
		public void accept(Object p1) throws Exception {
		    presenter.bindLifecycle(MatchFragment.this)
			.refreshData(region)
			.refreshMatch();
		}
	    });

	//停止刷新动画
	if (mSwipeRefreshLayout.isRefreshing()) {
	    mSwipeRefreshLayout.setRefreshing(false);
	}
    }

    private void showRootLayoutInAnimation(){
	if(loadAnimationFlag){
	    mRecyclerView.setVisibility(View.VISIBLE);
	    mRecyclerView.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.abc_fade_in));
	    loadAnimationFlag = false;
	}
    }

    private SharedPreferences getModesPreference() {
	return getActivity().getSharedPreferences(PreferenceKey.MODES_PREFERENCE, Context.MODE_PRIVATE);
    }

    @Override
    protected int getLayoutResId() {
	return R.layout.fragment_match;
    }
}
