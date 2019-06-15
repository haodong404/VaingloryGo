package vcoty.vainglory.go;

import android.app.*;
import android.content.*;
import android.text.*;
import android.widget.*;
import com.androidnetworking.*;
import com.androidnetworking.interceptors.*;
import com.tencent.bugly.crashreport.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import okhttp3.*;
import org.litepal.*;
import vcoty.vainglory.go.bean.bmob.*;
import vcoty.vainglory.go.enums.*;
import vcoty.vainglory.go.service.*;
import vcoty.vainglory.go.utils.*;

public class VaingloryGo extends Application {

	private List<Activity> activitys;
	
	private Intent mServiceIntent;
	
	private InitialCallback<UpdateRequest> callback;
	
	public interface InitialCallback<T>{
		void updataVersion(T data)
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		Theme.setThemeFromPreference(this);
		
		//初始化网络框架
		final int TIME_OUT = 10;
		
		OkHttpClient.Builder client = new OkHttpClient.Builder()
			.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
			.readTimeout(TIME_OUT, TimeUnit.SECONDS)
			.writeTimeout(TIME_OUT, TimeUnit.SECONDS)
			.addInterceptor(new GzipRequestInterceptor());

		AndroidNetworking.initialize(this, client.build());
		AndroidNetworking.enableLogging();
	
		//数据库
		LitePal.initialize(this);
		
		//更新资源
		mServiceIntent = new Intent(this,PullResourceService.class);
		this.startService(mServiceIntent);
		
		
		// 初始化全局的activity集合 //
		activitys = new ArrayList<Activity>(5);
		
		//权限
		checkUpdata();
		// 获取当前包名
		String packageName = getApplicationContext().getPackageName();
		// 获取当前进程名
		String processName = getProcessName(android.os.Process.myPid());
		// 设置是否为上报进程
		CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
		strategy.setUploadProcess(processName == null || processName.equals(packageName));
		// 初始化Bugly
		CrashReport.initCrashReport(getApplicationContext(), HttpApi.BUGLY_APP_ID.value(), false,strategy);
		
	}

	private void checkUpdata() {
		VersionUtil.get().isShowToast(false).checkUpdate(this, new VaingloryGo.InitialCallback<UpdateRequest>(){

				@Override
				public void updataVersion(UpdateRequest data) {
					try {
						VersionUtil.get().startUpdata(data, getActivitys().get(getActivitys().size() -1));

					} catch (Exception e) {
						Toast.makeText(VaingloryGo.this,e + "",Toast.LENGTH_SHORT).show();
					}}
			});
	}

	// 添加axtivity //
	public void addActivity(Activity activity) {
		if (!activitys.contains(activity)) {
			activitys.add(activity);
		}
	}

	// 销毁单个activity //
	public void removeSinglActivity(Activity activity) {
		if (!activitys.contains(activity)) {
			activitys.remove(activity);
			activity.finish();
		}
	}

	// 获取一个activity //
	public List<Activity> getActivitys() throws Exception {
		return activitys;
	}

	// 销毁全部Activity //
	public void removeAllActivitys() {
		for (int i = 0 ; i < activitys.size() ; i++) {
			activitys.get(i).finish();
		}
	}

	/**
	 * 获取进程号对应的进程名
	 * 
	 * @param pid 进程号
	 * @return 进程名
	 */
	private static String getProcessName(int pid) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
			String processName = reader.readLine();
			if (!TextUtils.isEmpty(processName)) {
				processName = processName.trim();
			}
			return processName;
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		return null;
	}
	
	public void setUpdataVersionListener(InitialCallback<UpdateRequest> callback){
		this.callback = callback;
	}
}
