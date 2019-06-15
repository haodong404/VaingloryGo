package vcoty.vainglory.go.ui.profile;

import android.animation.*;
import android.graphics.*;
import android.support.v4.widget.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.widget.*;
import com.androidnetworking.error.*;
import com.jakewharton.rxbinding2.view.*;
import com.squareup.picasso.*;
import com.trello.rxlifecycle2.android.*;
import de.hdodenhof.circleimageview.*;
import io.reactivex.functions.*;
import java.util.*;
import java.util.concurrent.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.adapter.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.bean.*;
import vcoty.vainglory.go.bean.event.*;
import vcoty.vainglory.go.bean.item.*;
import vcoty.vainglory.go.mvp.presenter.impl.*;
import vcoty.vainglory.go.mvp.view.*;
import vcoty.vainglory.go.utils.*;
import vcoty.vainglory.go.widget.*;

import de.hdodenhof.circleimageview.CircleImageView;
import vcoty.vainglory.go.R;

public class ProfileFragment extends BaseFragment implements ProfileView, OnTouchListener {

	//ui
	private RelativeLayout mRlRootLayout;
	private SwipeRefreshLayout mSrlRefresh;
	
	private RecyclerView mRvSkillTires;
	private RecyclerView mRvRoles;
	private RecyclerView mRvPlayedHeroes;
	private Button mBtnPlayedHeroesMore;
	
	private TextView mTvRegion;
	private TextView mTvPlayerName;
	private CircleImageView mCircleTextIvHeadImage;
	
	private TextView mTvAvgKills;
	private TextView mTvAvgDeaths;
	private TextView mTvAvgAssists;
	private TextView mTvKda;
	
	private NumberProgressBar mNumberPbWinrate;
	private TextView mTvFields;
	private NumberProgressBar mNumberPbKp;
	
	private TextView mTvFengshui;
	
	private CardView mCvOverview;
	private CardView mCvPosition;
	private CardView mCvHeroes;
	
	
	//adapter
	private ProfileSkillTireAdapter mAdapaterSkillTires;
	private ProfileRoleAdapter mAdapterRoles;
	private ProfilePlayedHeroesAdapter mAdapterPlayedHeros;
	
	private SearchPlayerPresenter presenter;
	
	private String playerName;
	
	@Override
	protected void initial(View view) {
		mRlRootLayout = view.findViewById(R.id.rl_fragment_profile_root);
		mSrlRefresh = view.findViewById(R.id.srl_profile_fragment);
		
		mTvRegion = view.findViewById(R.id.tv_fragment_profile_region);
		mRvSkillTires = view.findViewById(R.id.rv_profile_skill_tires);
		mRvRoles = view.findViewById(R.id.rv_profile_roles);
		mRvPlayedHeroes = view.findViewById(R.id.rv_profile_played_hero);
		mBtnPlayedHeroesMore = view.findViewById(R.id.btn_include_profile_played_hero_card_more);
		mTvPlayerName = view.findViewById(R.id.tv_fragment_profile_player_name);
		mCircleTextIvHeadImage = view.findViewById(R.id.circle_text_iv_profile_hero);
		
		mTvAvgKills = view.findViewById(R.id.tv_include_profile_overview_card_kills);
		mTvAvgDeaths = view.findViewById(R.id.tv_include_profile_overview_card_deaths);
		mTvAvgAssists = view.findViewById(R.id.tv_include_profile_overview_card_assists);
		mTvKda = view.findViewById(R.id.tv_include_profile_overview_card_kda);
		
		mNumberPbWinrate = view.findViewById(R.id.pb_include_profile_overview_card_winrate_progress);
		mNumberPbKp = view.findViewById(R.id.pb_include_profile_overview_card_kp_progress);
		mTvFields = view.findViewById(R.id.tv_include_profile_overview_card_win_loss);
		
		mTvFengshui = view.findViewById(R.id.tv_include_profile_overview_card_fengshui);
		
		mCvHeroes = view.findViewById(R.id.cv_include_profile_played_hero_card);
		mCvOverview = view.findViewById(R.id.cv_include_profile_overview_card_overview);
		mCvPosition = view.findViewById(R.id.cv_include_profile_roles_card);
		
		mCvHeroes.setOnTouchListener(this);
		mCvOverview.setOnTouchListener(this);
		mCvPosition.setOnTouchListener(this);
		
		presenter = new SearchPlayerPresenter();
		presenter.attachView(this);
		
		if(isAdded()){
			playerName = getArguments().getString(ExtraKey.EXTRA_KEY_PLAYER_NAME);
			presenter.bindLifecycle(this).queryPlayer(playerName);
		}
		
		initView();
	}

	@Override
	public void onResume() {
		if(presenter == null){
			presenter = new SearchPlayerPresenter();
			presenter.attachView(this);
		}
		super.onResume();
	}

	@Override
	public void onDestroy() {
		presenter.detachView();
		super.onDestroy();
	}
	

	private void initView() {
		Typeface mTfSkillTire = Typeface.createFromAsset(getActivity().getAssets(), "Kollektif-Bold.ttf");
		mTvPlayerName.setTypeface(mTfSkillTire);
		
		mRlRootLayout.setVisibility(View.GONE);
		
		
		LinearLayoutManager skillTirelayoutManager = new LinearLayoutManager(getActivity());
		skillTirelayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		mRvSkillTires.setLayoutManager(skillTirelayoutManager);
		mRvSkillTires.setNestedScrollingEnabled(false);
		mAdapaterSkillTires = new ProfileSkillTireAdapter(this,null);
		mRvSkillTires.setAdapter(mAdapaterSkillTires);
		
		LinearLayoutManager rolesLayoutManager = new LinearLayoutManager(getActivity());
		rolesLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);	
		mRvRoles.setLayoutManager(rolesLayoutManager);
		mRvRoles.setNestedScrollingEnabled(false);
		mAdapterRoles = new ProfileRoleAdapter(this,null);
		mRvRoles.setNestedScrollingEnabled(false);
		mRvRoles.setAdapter(mAdapterRoles);
		
		LinearLayoutManager playedHerosLayoutManager = new LinearLayoutManager(getActivity());
		playedHerosLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);	
		mRvPlayedHeroes.setLayoutManager(playedHerosLayoutManager);
		mRvPlayedHeroes.setNestedScrollingEnabled(false);
		//分割线
		mRvPlayedHeroes.addItemDecoration(new NormalItemDecoration.Builder(getActivity())
										.colorResId(AttrResourceUtil.getAttrResId(getActivity(), R.attr.colorDivider))
										.sizeResId(R.dimen.divider_size)
										.marginResId(R.dimen.divider_margin_left, R.dimen.divider_margin_right)
										.build());
		mAdapterPlayedHeros = new ProfilePlayedHeroesAdapter((BaseActivity)this.getActivity(),null);
		mRvPlayedHeroes.setNestedScrollingEnabled(false);
		mRvPlayedHeroes.setAdapter(mAdapterPlayedHeros);
		
		configRefresh();
	}

	private void configRefresh() {
		mSrlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

				@Override
				public void onRefresh() {
					if(presenter != null ){
						presenter.bindLifecycle(ProfileFragment.this).refreshPlayer();
					}
				}
			});
	}

	@Override
	public void showLoading() {
		if(!mSrlRefresh.isRefreshing()){
			mSrlRefresh.setRefreshing(true);
		}
	}

	@Override
	public void loadData(final ProfileInformationEntity data) {
		
		final StringBuilder playerName = new StringBuilder();
		if(!data.getGuild().equals("")){
			playerName.append(data.getGuild() + "_");
		}
		playerName.append(data.getName());
		mTvPlayerName.setText(playerName);
		mTvRegion.setText(data.getRegion().getResId());
		Picasso.get().load(data.getHeadImageUrl()).resize(DensityUtil.dp2px(this.getActivity(),40),DensityUtil.dp2px(this.getActivity(),40)).centerCrop().into(mCircleTextIvHeadImage,
			new com.squareup.picasso.Callback(){

				@Override
				public void onSuccess() {
					mCircleTextIvHeadImage.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.abc_fade_in));
				}

				@Override
				public void onError(Exception p1) {
					mCircleTextIvHeadImage.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.abc_fade_in));
				}
			});
		mTvAvgKills.setText(data.getAvgKills());
		mTvAvgDeaths.setText(data.getAvgDeaths());
		mTvAvgAssists.setText(data.getAvgAssists());
		mTvKda.setText("KDA:" + data.getKda());
		mNumberPbWinrate.setProgress((int)data.getWinrate());
		mTvFields.setText(data.getWins() + "V-" + (data.getFields() - data.getWins()) + "D");
		mNumberPbKp.setProgress((int) data.getKp());
		
		StringBuilder fengShui = new StringBuilder("你的" + data.getFields() + "场比赛中，");
		if(data.getRedWinRate() >= data.getBlueWinRate()){
			fengShui.append("有" + data.getRedGames() + "场在红队，在红队的概率为：" + (int) (((float)data.getRedGames() / (float)data.getFields()) * 100) + "％，" + "并且获得了" + data.getRedWins() + "场胜利，其胜率为：" + data.getRedWinRate() + "％");
		}else{
			fengShui.append("有" + data.getBlueGames() + "场在蓝队，在蓝队的概率为：" + (int) (((float)data.getBlueGames() / (float)data.getFields()) * 100) + "％，" + "并且获得了" + data.getBlueWins() + "场胜利，其胜率为：" + data.getBlueWinRate() + "％");
		}
		
		mTvFengshui.setText(fengShui.toString());
		
		mAdapaterSkillTires.replaceData(data.getSkillTireItems());
		mAdapterRoles.replaceData(data.getRoleItems());
		List<ProfilePlayedHeroIitem> playedHeroItems = new ArrayList<ProfilePlayedHeroIitem>(3);
		for(int i = 0; i < (data.getPlayedHeroItems().size() <= 3 ? data.getPlayedHeroItems().size() : 3 ); i++){
			playedHeroItems.add(data.getPlayedHeroItems().get(i));
		}
		mAdapterPlayedHeros.replaceData(playedHeroItems);
		
		RxView.clicks(mBtnPlayedHeroesMore)
			.throttleFirst(600,TimeUnit.MILLISECONDS)
			.compose(this.<Object>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
			.subscribe(new Consumer<Object>(){

				@Override
				public void accept(Object p1) throws Exception {
					RxBus.getDefault().postSticky(new PlayedHeroesEvent(data.getPlayedHeroItems()));
					new FullScreenDialogFragment.Builder(getActivity())
						.setTitle(getResString(R.string.profile_hero_title))
						.setContent(PlayedHeroesFragment.class,new android.os.Bundle())
						.build().show(getActivity().getSupportFragmentManager(),"PlayedHeroesDialog");
					
				}
			});
	}

	@Override
	public void hideLoading() {
		if(!mRlRootLayout.isShown()){
			mRlRootLayout.setAnimation(fadeIn());
			mRlRootLayout.setVisibility(View.VISIBLE);
		}
		
		if(mSrlRefresh.isRefreshing()){
			mSrlRefresh.setRefreshing(false);
		}
	}

	@Override
	public void showError(Throwable error) {
		if(mSrlRefresh.isRefreshing()){
			mSrlRefresh.setRefreshing(false);
		}
		
		if(error instanceof ANError){
			try {
				String errorString = ANErrorConverter.get().convertNormal(this.getActivity(), (ANError) error);
				shortToast(errorString);
			} catch (Exception e) {
				shortToast("error: " + e);
			}
		}else{
			shortToast("error:" + error.toString());
		}

		if(mRlRootLayout.isShown()){
			mRlRootLayout.setAnimation(fadeOut());
			mRlRootLayout.setVisibility(View.GONE);
		}
		
	}
	
	private Animation fadeIn(){
		return AnimationUtils.loadAnimation(getActivity(), R.anim.abc_fade_in);
	}
	
	private Animation fadeOut(){
		return AnimationUtils.loadAnimation(getActivity(), R.anim.abc_fade_out);
	}
	
	@Override
	public boolean onTouch(View p1, MotionEvent motionEvent) {
		switch (motionEvent.getAction()) {
			case MotionEvent.ACTION_DOWN:
				ObjectAnimator upAnim = ObjectAnimator.ofFloat(p1, "translationZ", 16);
				upAnim.setDuration(150);
				upAnim.setInterpolator(new DecelerateInterpolator());
				upAnim.start();
				break;

			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				ObjectAnimator downAnim = ObjectAnimator.ofFloat(p1, "translationZ", 0);
				downAnim.setDuration(150);
				downAnim.setInterpolator(new AccelerateInterpolator());
				downAnim.start();
				break;
		}
		return false;
	}
	
	@Override
	protected int getLayoutResId() {
		return R.layout.fragment_profile;
	}
}
