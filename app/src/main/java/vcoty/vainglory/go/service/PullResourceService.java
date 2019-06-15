package vcoty.vainglory.go.service;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.preference.*;
import com.androidnetworking.error.*;
import io.reactivex.*;
import io.reactivex.disposables.*;
import io.reactivex.functions.*;
import io.reactivex.schedulers.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.mvp.presenter.impl.*;
import vcoty.vainglory.go.mvp.view.*;
import vcoty.vainglory.go.utils.*;

import android.app.Notification;
import android.widget.*;

public class PullResourceService extends Service implements PullResourceView {
	private PullResourcePresenter presenter;

	@Override
	public IBinder onBind(Intent p1) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		presenter = new PullResourcePresenter();
		presenter.attachView(this);

		presenter.checkResource();
		
		//加速
		if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PreferenceKey.SETTING_SEARCH_ACCELERATE,true)){
			Observable.zip(NetTaskUtil.get().testApi(), NetTaskUtil.get().testVgo(), new BiFunction<String, String ,Object>(){

					@Override
					public Object apply(String p1, String p2) throws Exception {
						return new Object();
					}
				})
				.subscribeOn(Schedulers.io())
				.subscribe(new io.reactivex.Observer<Object>(){

					@Override
					public void onSubscribe(Disposable p1) {
						// TODO: Implement this method
					}

					@Override
					public void onNext(Object p1) {
						// TODO: Implement this method
					}

					@Override
					public void onError(Throwable p1) {
						// TODO: Implement this method
					}

					@Override
					public void onComplete() {
						// TODO: Implement this method
					}
				});
		}
	}

	@Override
	public void showLoading() {
		
	}

	@Override
	public void hideLoading() {
		
	}

	@Override
	public void showError(Throwable error) {
		
	}

	@Override
	public void showUpdataSuccess() {
		stopSelf();
	}

	@Override
	public void showNoMore() {
		stopSelf();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		presenter.detachView();
		presenter.dispose();
	}
}
