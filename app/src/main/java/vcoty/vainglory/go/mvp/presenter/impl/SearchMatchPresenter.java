package vcoty.vainglory.go.mvp.presenter.impl;

import android.content.*;
import java.util.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.bean.item.*;
import vcoty.vainglory.go.enums.*;
import vcoty.vainglory.go.interfaces.*;
import vcoty.vainglory.go.mvp.model.*;
import vcoty.vainglory.go.mvp.presenter.*;
import vcoty.vainglory.go.mvp.view.*;
import vcoty.vainglory.go.utils.*;

public class SearchMatchPresenter extends BasePresenter<MatchView> implements IBindLifecycle<SearchMatchPresenter> {
	
	private BaseActivity activity;
	private BaseFragment fragment;
	
	private String name;
	private Region region;
	private GameMode gameMode;
	private int page;
	
	public void queryMatch(String name,Region region,GameMode gameMode,int page){
		if(!isViewAttached() || fragment == null){
			return;
		}
		
		if(isViewAttached()){
			getView().showLoading();
		}
		
		this.name = name;
		this.region = region;
		this.gameMode = gameMode;
		this.page = page;
		
		SearchModel.get()
				.bindLifecycle(fragment)
				.queryMatch(name, region, gameMode, page, new Callback<List<MatchsListItem>>(){

				@Override
				public void onSuccee(List<MatchsListItem> data) {
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
				public void onComplete(){
					if(isViewAttached()){
						getView().hideLoading();
					}
				}
			});
	}
	
	/**
	 * 刷新
	 */
	public void refreshMatch(){
		if(name != null){
			this.page = 0;
			queryMatch(name,region,gameMode,page);
		}else{
			if(isViewAttached()){
				getView().showError(new NullPointerException("The player name is Null (SearchPresenter.java : 70)"));
			}
		}
	}
	
	/**
	 * 加载更多
	 */
	public void loadMore(int page){
		if(name != null){
			SearchModel.get()
				.bindLifecycle(fragment)
				.queryMatch(name, region, gameMode, page, new Callback<List<MatchsListItem>>(){

					@Override
					public void onSuccee(List<MatchsListItem> data) {
						if(isViewAttached()){
							getView().loadMoreData(data);
						}
					}

					@Override
					public void onError(Throwable error) {
						if(isViewAttached()){
							getView().showError(error);
						}
					}

					@Override
					public void onComplete(){
						if(isViewAttached()){
							getView().hideLoading();
						}
					}
				});
		}else{
			if(isViewAttached()){
				getView().showError(new NullPointerException("The player name is Null (SearchPresenter.java : 70)"));
			}
		}
	}
	
	public SearchMatchPresenter refreshData(Region region){
		this.region = region;
		return this;
	}
	
	public SearchMatchPresenter refreshMode(GameMode mode){
		this.gameMode = mode;
		return this;
	}
	
	@Override
	public SearchMatchPresenter bindLifecycle(BaseActivity activity) {
		this.activity = activity;
		return this;
	}

	@Override
	public SearchMatchPresenter bindLifecycle(BaseFragment fragment) {
		this.fragment = fragment;
		return this;
	}
}
