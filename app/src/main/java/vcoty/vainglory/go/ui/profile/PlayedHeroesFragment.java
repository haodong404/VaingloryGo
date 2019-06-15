package vcoty.vainglory.go.ui.profile;

import android.support.v7.widget.*;
import android.view.*;
import com.trello.rxlifecycle2.android.*;
import io.reactivex.android.schedulers.*;
import io.reactivex.functions.*;
import io.reactivex.schedulers.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.adapter.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.bean.event.*;
import vcoty.vainglory.go.utils.*;
import vcoty.vainglory.go.widget.*;

public class PlayedHeroesFragment extends BaseFragment implements FullScreenDialogContent {

	@Override
	public boolean onExtraActionClick(MenuItem actionItem, FullScreenDialogController dialogController) {
		// TODO: Implement this method
		return false;
	}


	private RecyclerView mRecyclerView;
	private ProfilePlayedHeroesAdapter adapter;
	
	@Override
	protected void initial(View view) {
		mRecyclerView = view.findViewById(R.id.rv_view_played_heroes_full_screen_dialog);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		
		mRecyclerView.addItemDecoration(new NormalItemDecoration.Builder(getActivity())
										.colorResId(AttrResourceUtil.getAttrResId(getActivity(), R.attr.colorDivider))
										.sizeResId(R.dimen.divider_size)
										.marginResId(R.dimen.divider_margin_left, R.dimen.divider_margin_right)
										.build());
		mRecyclerView.setLayoutManager(layoutManager);
		adapter = new ProfilePlayedHeroesAdapter(getActivity(),null);
		
		mRecyclerView.setAdapter(adapter);
		
	}

	@Override
	public void onDialogCreated(FullScreenDialogController p1) {
		
		RxBus.getDefault().toObservableSticky(PlayedHeroesEvent.class)
			.subscribeOn(Schedulers.newThread())
			.compose(this.<PlayedHeroesEvent>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Consumer<PlayedHeroesEvent>(){

				@Override
				public void accept(PlayedHeroesEvent p1) throws Exception {
					adapter.replaceData(p1.getEvent());
				}
			});
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
		return R.layout.view_played_heroes_full_screen_dialog;
	}
	
}
