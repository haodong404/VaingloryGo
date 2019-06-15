package vcoty.vainglory.go.base;

import android.content.*;
import android.graphics.drawable.*;
import android.os.*;
import android.preference.*;
import android.widget.*;
import com.trello.rxlifecycle2.components.support.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.enums.*;
import vcoty.vainglory.go.interfaces.*;
import vcoty.vainglory.go.utils.*;

public abstract class BaseActivity extends RxAppCompatActivity implements IToasts, IResources {

	private VaingloryGo application;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Theme.setThemeFromPreference(this);
		super.onCreate(savedInstanceState);
		//初始化语言
		LanguageUtil.get().changeLanguage(this, PreferenceManager.getDefaultSharedPreferences(this).getString(PreferenceKey.SETTING_LANGUAGE, LanguageUtil.AUTO));
		
		setContentView(getLayoutResId());
		
		// 将当前activity添加到Application中的集合中去 //
		application = (VaingloryGo) getApplication();
		application.addActivity(this);
		
		initial();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//初始化状态栏
		if(!StatusBarCompat.get().initStatusBar(this)){
			shortToast("Status bar initialization failed");
		}
	}

	@Override
	public String getResString(int resId) {
		return this.getResources().getString(resId);
	}

	@Override
	public int getResColor(int resId) {
		return this.getResources().getColor(resId);
	}

	@Override
	public Drawable getResDrawable(int resId) {
		return this.getResources().getDrawable(resId);
	}

	@Override
	public float getResDimension(int resId) {
		return this.getResources().getDimension(resId);
	}

	@Override
	public void shortToast(String content) {
		Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
	}

	@Override
	public void longToast(String content) {
		Toast.makeText(this,content,Toast.LENGTH_LONG).show();
	}
	
	// 移出所有activity //
	public void removeAllActivitys(){
		application.removeAllActivitys();
	}
	
	// 获取设置的preference //
	public SharedPreferences getPreference(){
		return PreferenceManager.getDefaultSharedPreferences(this);
	}
	
	// 获取沉浸式的状态栏颜色 //
	public int getAttrPrimaryColorId() {
		return AttrResourceUtil.getAttrResId(this, R.attr.colorPrimary);
	}
	
	// 获取Material Design的状态栏颜色 //
	public int getAttrPrimaryDarkColorId() {
		return AttrResourceUtil.getAttrResId(this, R.attr.colorPrimaryDark);
	}
	
	
	/**
	 * 初始化(onCreat)
	 */
	protected abstract void initial()
	
	/**
	 * 获取当前Activity的布局ID
	 */
	protected abstract int getLayoutResId()
	
}
