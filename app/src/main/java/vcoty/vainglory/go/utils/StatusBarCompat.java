package vcoty.vainglory.go.utils;

import vcoty.vainglory.go.R;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import vcoty.vainglory.go.base.BaseActivity;

public class StatusBarCompat {
	
	public static StatusBarCompat get(){
		return StatusBarCompatHolder.mStatusBarCompat;
	}
	
	public static class StatusBarCompatHolder{
		private static final StatusBarCompat mStatusBarCompat = new StatusBarCompat();
	}
	
	/**
	 * 设置状态栏颜色
	 */
	public boolean setStatusBarColor(BaseActivity activity,int color){
		
		boolean isSuccess = false;
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
			Window window = activity.getWindow();

			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(color);
			window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

			ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
			View mChildView = mContentView.getChildAt(0);
			if (mChildView != null) {
				ViewCompat.setFitsSystemWindows(mChildView, false);
				ViewCompat.requestApplyInsets(mChildView);
			}
			
			isSuccess = true;
		}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
			return false ;
		}
		
		if(color == activity.getResources().getColor(R.color.colorPrimaryDefault)){
			if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
				return false;
			}else{
				activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			}
		}
		
		return isSuccess;
	}
	
	/**
	 * 从preference中初始化StatusBar
	 */
	public boolean initStatusBar(BaseActivity baseAct){
		boolean isSuccess = false;
		if (baseAct.getPreference().getBoolean(PreferenceKey.SETTING_STATUS_UNITE, false)) {
			isSuccess =  setStatusBarColor(baseAct,baseAct.getResColor(baseAct.getAttrPrimaryColorId()));	
		} else {
			isSuccess = setStatusBarColor(baseAct,baseAct.getResColor(baseAct.getAttrPrimaryDarkColorId()));
		}
		return isSuccess;
	}
}
