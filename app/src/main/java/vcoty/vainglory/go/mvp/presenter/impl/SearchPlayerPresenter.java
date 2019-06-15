package vcoty.vainglory.go.mvp.presenter.impl;

import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.bean.*;
import vcoty.vainglory.go.interfaces.*;
import vcoty.vainglory.go.mvp.model.*;
import vcoty.vainglory.go.mvp.presenter.*;
import vcoty.vainglory.go.mvp.view.*;

public class SearchPlayerPresenter extends BasePresenter<ProfileView> implements IBindLifecycle<SearchPlayerPresenter> {

	private BaseFragment fm;
	private String name;
	
	public void queryPlayer(String name){
		if(fm == null){
			if(isViewAttached()){
				getView().showError(new NullPointerException("BaseFragment is null (SearchPlayerPresenter : 17)"));
			}
			return;
		}
		
		this.name = name;
		
		if(isViewAttached()){
			getView().showLoading();
		}
		
		SearchModel.get().bindLifecycle(fm)
			.queryPlayerFromVgpro(name, new Callback<ProfileInformationEntity>(){

				@Override
				public void onSuccee(ProfileInformationEntity data) {
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
	
	public void refreshPlayer(){
		if(name != null){
			queryPlayer(name);
		}else{
			if(isViewAttached()){
				getView().showError(new NullPointerException("Name is null (SearchPlayerPresenter.java : 60)"));
			}
		}
	}
	
	@Override
	public SearchPlayerPresenter bindLifecycle(BaseActivity activity) {
		return null;
	}

	@Override
	public SearchPlayerPresenter bindLifecycle(BaseFragment fragment) {
		this.fm = fragment;
		return this;
	}

	
}
