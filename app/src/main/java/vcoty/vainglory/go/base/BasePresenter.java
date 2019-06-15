package vcoty.vainglory.go.base;

import vcoty.vainglory.go.mvp.presenter.Callback;

public class BasePresenter<V extends BaseView> {
	
	private V mView;
	
	/**
	 * 绑定View
	 */
	public void attachView(V view){
		this.mView = view;
	}
	
	/**
	 * 解绑View
	 */
	public void detachView(){
		this.mView = null;
	}
	
	/**
	 * 判断view是否被绑定
	 */
	public boolean isViewAttached(){
		return mView != null;
	}
	
	/**
	 * 获取view
	 */
	public V getView(){
		return mView;
	}
}
