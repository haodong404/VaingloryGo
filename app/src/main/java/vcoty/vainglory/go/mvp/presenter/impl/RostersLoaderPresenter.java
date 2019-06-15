package vcoty.vainglory.go.mvp.presenter.impl;
import java.util.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.bean.*;
import vcoty.vainglory.go.bean.item.*;
import vcoty.vainglory.go.interfaces.*;
import vcoty.vainglory.go.mvp.model.*;
import vcoty.vainglory.go.mvp.presenter.*;
import vcoty.vainglory.go.mvp.view.*;

public class RostersLoaderPresenter extends BasePresenter<RosterView> implements IBindLifecycle<RostersLoaderPresenter> {

	private BaseFragment fragment;
	private BaseActivity activity;
	
	public void loadOverviews(RosterInformationEntity entity){
		if(isViewAttached()){
			getView().showLoading();
		}
		
		if(activity == null){
			if(isViewAttached()){
				getView().showError(new NullPointerException("Please bind lifecycle first (RostersLoaderPresenter.class (#24))"));
			}
			return;
		}
		
		RostersLoaderModel.get().bindLifecycle(activity)
			.loadOverviews(entity, new Callback<List<RosterOverviewSection>>(){

				@Override
				public void onSuccee(List<RosterOverviewSection> data) {
					if(isViewAttached()){
						getView().loadSuccess(data);
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
	
	@Override
	public RostersLoaderPresenter bindLifecycle(BaseActivity activity) {
		this.activity = activity;
		return this;
	}

	@Override
	public RostersLoaderPresenter bindLifecycle(BaseFragment fragment) {
		this.fragment = fragment;
		return this;
	}
}
