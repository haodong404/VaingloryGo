package vcoty.vainglory.go.utils;

import vcoty.vainglory.go.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import vcoty.vainglory.go.base.BasePreference;
import vcoty.vainglory.go.base.BaseActivity;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;
import com.trello.rxlifecycle2.android.FragmentEvent;
import io.reactivex.disposables.Disposable;
import vcoty.vainglory.go.bean.bmob.UpdateRequestRoot;
import com.google.gson.Gson;
import java.util.Collections;
import java.util.Comparator;
import vcoty.vainglory.go.bean.bmob.UpdateRequest;
import android.widget.Toast;
import com.trello.rxlifecycle2.android.ActivityEvent;
import android.webkit.WebView;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interfaces.DownloadProgressListener;
import java.io.File;
import com.androidnetworking.error.ANError;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.net.Uri;
import vcoty.vainglory.go.*;

public class VersionUtil {

	public static VersionUtil get() {
		return VersionUtilHolder.mVersionUtil;
	}

	public static class VersionUtilHolder {
		private static final VersionUtil mVersionUtil = new VersionUtil();
	}


	private boolean isShowToast = false;
	

	/**
	 * 获取本地软件版本号
	 */
	public static int getLocalVersion(Context ctx) {
		int localVersion = 0;
		try {
			PackageInfo packageInfo = ctx.getApplicationContext()
				.getPackageManager()
				.getPackageInfo(ctx.getPackageName(), 0);
			localVersion = packageInfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return localVersion;
	}

	/**
	 * 获取本地软件版本号名称
	 */
	public static String getLocalVersionName(Context ctx) {
		String localVersion = "";
		try {
			PackageInfo packageInfo = ctx.getApplicationContext()
				.getPackageManager()
				.getPackageInfo(ctx.getPackageName(), 0);
			localVersion = packageInfo.versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return localVersion;
	}

	public void checkUpdate(final BasePreference frm) {
		NetTaskUtil.get().getUpdata(VersionUtil.getLocalVersion(frm.getActivity()))
			.subscribeOn(Schedulers.io())
			.compose(frm.<String>bindUntilEvent(FragmentEvent.DESTROY))
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new io.reactivex.Observer<String>(){

				@Override
				public void onSubscribe(Disposable p1) {
					// TODO: Implement this method
				}

				@Override
				public void onNext(String response) {
					UpdateRequestRoot entity = new Gson().fromJson(response, UpdateRequestRoot.class);
					if (entity.getResults().size() >= 2) {//如果有多个版本，则对其进行排序
						Collections.sort(entity.getResults(), new Comparator<UpdateRequest>(){

								@Override
								public int compare(UpdateRequest p1, UpdateRequest p2) {
									if (p1.getVersionCode() > p2.getVersionCode()) {
										return -1;
									} else if (p1.getVersionCode() == p2.getVersionCode()) {
										return 0;
									} else {
										return 1;
									}
								}
							});
					}

					if (entity.getResults().isEmpty()) {
						if(isShowToast){
							Toast.makeText(frm.getActivity(), R.string.setting_category_about_version_toast, Toast.LENGTH_SHORT).show();
						}
					} else {
						//开始更新
						startUpdata(entity.getResults().get(0), frm.getActivity());
					}

				}

				@Override
				public void onError(Throwable e) {
					if(isShowToast){
						Toast.makeText(frm.getActivity(), R.string.setting_category_about_version_toast, Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onComplete() {
					// TODO: Implement this method
				}
			});
	}
	public void checkUpdate(final Context act, final VaingloryGo.InitialCallback<UpdateRequest> callback) {
		NetTaskUtil.get().getUpdata(VersionUtil.getLocalVersion(act))
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new io.reactivex.Observer<String>(){

				@Override
				public void onSubscribe(Disposable p1) {
					// TODO: Implement this method
				}

				@Override
				public void onNext(String response) {
					UpdateRequestRoot entity = new Gson().fromJson(response, UpdateRequestRoot.class);
					if (entity.getResults().size() >= 2) {//如果有多个版本，则对其进行排序
						Collections.sort(entity.getResults(), new Comparator<UpdateRequest>(){

								@Override
								public int compare(UpdateRequest p1, UpdateRequest p2) {
									if (p1.getVersionCode() > p2.getVersionCode()) {
										return -1;
									} else if (p1.getVersionCode() == p2.getVersionCode()) {
										return 0;
									} else {
										return 1;
									}
								}
							});
					}

					if (entity.getResults().isEmpty()) {
						if(isShowToast){
							Toast.makeText(act, R.string.setting_category_about_version_toast, Toast.LENGTH_SHORT).show();
						}
					} else {
						//开始更新
						callback.updataVersion(entity.getResults().get(0));
					}

				}

				@Override
				public void onError(Throwable e) {
					if(isShowToast){
						Toast.makeText(act, R.string.setting_category_about_version_toast, Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onComplete() {
					// TODO: Implement this method
				}
			});
	}

	public void startUpdata(final UpdateRequest results, final Context ctx) {
		WebView webView = new WebView(ctx);
		webView.loadUrl(results.getVersionDescription().getUrl());
		new AlertDialog.Builder(ctx)
			.setTitle(results.getVersionName())
			.setView(webView)
			.setPositiveButton(R.string.setting_category_about_version_download, new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2) {
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_VIEW);
					intent.addCategory(Intent.CATEGORY_BROWSABLE);
					intent.setData(Uri.parse(results.getApk().getUrl()));
					ctx.startActivity(intent);
				}
			}).show();
	}

	
	public VersionUtil isShowToast(boolean b){
		this.isShowToast = b;
		return this;
	}
}
