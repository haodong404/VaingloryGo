package vcoty.vainglory.go.mvp.presenter.impl;
import java.util.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.bean.item.*;
import vcoty.vainglory.go.enums.*;
import vcoty.vainglory.go.interfaces.*;
import vcoty.vainglory.go.mvp.model.*;
import vcoty.vainglory.go.mvp.presenter.*;
import vcoty.vainglory.go.mvp.view.*;

public class RosterStatsTelematryPresenter extends BasePresenter<RosterStatsView> implements IBindLifecycle<RosterStatsTelematryPresenter> {

	private BaseActivity activity;
	private BaseFragment fragment;
	
	private String matchId;
	private Region region;
	private Map<Boolean,String> myself;
	
	public void startAnalyzing(String matchId,Region region,Map<Boolean,String> myself){
		if(isViewAttached()){
			getView().showLoading();
		}
		
		if(fragment == null){
			if(isViewAttached()){
				getView().showError(new NullPointerException("Please bind lifecycle first (RosterStatsTelematryPresenter.class (#15))"));
			}
			return;
		}
		
		this.matchId = matchId;
		this.region = region;
		this.myself = myself;
		
		RosterStatsTelematryModel.get().bindLifecycle(fragment)
			.getStats(matchId, myself, region, new Callback<List<RosterStatsRootItem>>(){

				@Override
				public void onSuccee(List<RosterStatsRootItem> data) {
					if(isViewAttached()){
						getView().loadData(data);
					}
				}

				@Override
				public void onError(Throwable error) {
					if(isViewAttached()){
						getView().showError(error);
					}
				}

				@Override
				public void onComplete() {
					if(isViewAttached()){
						getView().hideLoading();
					}
				}
			});
	}
	
	public void refresh(){
		if(matchId != null && region != null && myself != null){
			startAnalyzing(matchId,region,myself);
		}
	}
	
	@Override
	public RosterStatsTelematryPresenter bindLifecycle(BaseActivity activity) {
		this.activity = activity;
		return this;
	}

	@Override
	public RosterStatsTelematryPresenter bindLifecycle(BaseFragment fragment) {
		this.fragment = fragment;
		return this;
	}
	
}
