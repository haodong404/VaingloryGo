package vcoty.vainglory.go.ui.roster;

import android.content.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.animation.*;
import com.chad.library.adapter.base.*;
import com.trello.rxlifecycle2.android.*;
import io.reactivex.android.schedulers.*;
import io.reactivex.functions.*;
import io.reactivex.schedulers.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.adapter.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.bean.event.*;
import vcoty.vainglory.go.bean.item.*;
import vcoty.vainglory.go.enums.*;
import vcoty.vainglory.go.ui.*;
import vcoty.vainglory.go.utils.*;
import vcoty.vainglory.go.widget.*;

public class OverviewFragment extends BaseFragment {
	
	private RecyclerView mRecyclerView;
	
	private RosterRecyclerAdapter adapter;
	
	
	@Override
	protected void initial(View view) {
		mRecyclerView = view.findViewById(R.id.rv_roster_overview_fragment);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		mRecyclerView.setVisibility(View.INVISIBLE);
		
		//分割线
		mRecyclerView.addItemDecoration(new NormalItemDecoration.Builder(getActivity())
										.colorResId(AttrResourceUtil.getAttrResId(getActivity(), R.attr.colorDivider))
										.sizeResId(R.dimen.divider_size)
										.marginResId(R.dimen.divider_margin_left, R.dimen.divider_margin_right)
										.build());
		adapter = new RosterRecyclerAdapter(getActivity(),null);
		mRecyclerView.setAdapter(adapter);
		adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){

				@Override
				public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
					if(adapter.getData().get(position) instanceof RosterOverviewSection){
						RosterOverviewItem t = ((RosterOverviewSection) adapter.getData().get(position)).getItem();
						Intent mIntent = new Intent(getActivity(),MainActivity.class);
						if(t != null){
							mIntent.putExtra(ExtraKey.EXTRA_KEY_PLAYER_NAME,t.getPlayerName());
							mIntent.putExtra(ExtraKey.EXTRA_KEY_REGION,getPreference().getString(PreferenceKey.SETTING_REGIONS,Region.CHINA.value()));
							mIntent.putExtra(ExtraKey.EXTRA_KEY_BOOLEAN_1,true);

							new RosterPlayerInfoBottomDialog.DataBuilder()
								.setHeroName(t.getHeroName())
								.setHeroUrl(t.getUrl())
								.setPlayerName(t.getPlayerName())
								.setSkillTier(t.getSkillTier())
								.setPlayerGuild(t.getGuild())
								.setIntent(mIntent)
								.build(getActivity())
								.show(getActivity().getSupportFragmentManager());
						}
					}
				}
			});
		
		RxBus.getDefault().toObservableSticky(RosterEvent.class)
			.subscribeOn(Schedulers.newThread())
			.take(1)
			.compose(this.<RosterEvent>bindUntilEvent(FragmentEvent.DESTROY))
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<RosterEvent>(){

				@Override
				public void accept(RosterEvent p1) throws Exception {
					adapter.replaceData(p1.getData());
					mRecyclerView.setVisibility(View.VISIBLE);
					mRecyclerView.setAnimation(fadeIn());
				}
			});
	}
	
	private Animation fadeIn(){
		return AnimationUtils.loadAnimation(getActivity(), R.anim.abc_fade_in);
	}
	

	@Override
	protected int getLayoutResId(){
		return R.layout.fragment_roster_overview;
	}
	
}
