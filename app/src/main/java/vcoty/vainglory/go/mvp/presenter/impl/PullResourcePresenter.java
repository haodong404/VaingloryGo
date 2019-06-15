package vcoty.vainglory.go.mvp.presenter.impl;

import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.mvp.model.*;
import vcoty.vainglory.go.mvp.presenter.*;
import vcoty.vainglory.go.mvp.view.*;

public class PullResourcePresenter extends BasePresenter<PullResourceView> implements UpdataResourceCallBack {

	
	public PullResourcePresenter checkResource(){
		if(isViewAttached()){
			getView().showLoading();
		}
		PullResourceModel.get()
			.updataLocalResource(this);
		return this;
	}
	
	@Override
	public void onSuccee(Boolean data) {
		if(data && isViewAttached()){
			getView().showUpdataSuccess();
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

	@Override
	public void noNewResource() {
		if(isViewAttached()){
			getView().showNoMore();
		}
	}
	
	public void dispose(){
		PullResourceModel.get().despose();
	}
	
}
