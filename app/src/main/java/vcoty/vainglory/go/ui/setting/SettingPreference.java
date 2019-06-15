package vcoty.vainglory.go.ui.setting;

import android.app.*;
import android.content.*;
import android.net.*;
import android.preference.*;
import android.support.design.widget.*;
import android.support.v7.app.*;
import android.support.v7.view.menu.*;
import android.support.v7.widget.*;
import android.text.*;
import android.view.*;
import android.widget.*;
import com.afollestad.materialdialogs.*;
import io.reactivex.*;
import io.reactivex.android.schedulers.*;
import io.reactivex.disposables.*;
import io.reactivex.schedulers.*;
import java.io.*;
import java.lang.reflect.*;
import java.math.*;
import org.litepal.crud.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.bean.db.*;
import vcoty.vainglory.go.enums.*;
import vcoty.vainglory.go.mvp.presenter.impl.*;
import vcoty.vainglory.go.mvp.view.*;
import vcoty.vainglory.go.utils.*;
import vcoty.vainglory.go.widget.*;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.text.ClipboardManager;
import vcoty.vainglory.go.R;
import vcoty.vainglory.go.enums.Theme;

public class SettingPreference extends BasePreference
implements Preference.OnPreferenceChangeListener,Preference.OnPreferenceClickListener {


	private ExpandPreference themePre;					
	private Preference regionPreference;							
	private ListPreference languagePreference;
	private ListPreference displayRulePreference;
	private Preference jumpPreference;

	private SwitchPreference searchAcceleratePre;
	private SwitchPreference statusUnitePreference;
	private Preference clearCache;
	private Preference updataResPre;

	private Preference versionPre;
	private Preference buyCoffeePre;
	private Preference contactPre;
	private Preference sourcePre;

	private String region;


	private final static String cacheUrl = "/data/data/vcoty.vainglory.go/cache/picasso-cache/";
	private File cacheFile;

	@Override
	protected void initial() {
		cacheFile = new File(cacheUrl);

		themePre = (ExpandPreference) findPreference(PreferenceKey.SETTING_THEME);

		regionPreference = findPreference(PreferenceKey.SETTING_REGIONS);
		languagePreference = (ListPreference) findPreference(PreferenceKey.SETTING_LANGUAGE);
		displayRulePreference = (ListPreference) findPreference(PreferenceKey.SETTING_RANKING_POINTS_SHOW);
		jumpPreference = findPreference(PreferenceKey.SETTING_JUMP);

		statusUnitePreference = (SwitchPreference) findPreference(PreferenceKey.SETTING_STATUS_UNITE);
		searchAcceleratePre = (SwitchPreference) findPreference(PreferenceKey.SETTING_SEARCH_ACCELERATE);
		clearCache = findPreference(PreferenceKey.SETTING_CLEAR_CACHE);
		updataResPre = findPreference(PreferenceKey.SETTING_UPDATA_RESOURCE);

		versionPre = findPreference(PreferenceKey.SETTING_ABOUT_VERSION);
		buyCoffeePre = findPreference(PreferenceKey.SETTING_ABOUT_BUY_COFFEE);
		contactPre = findPreference(PreferenceKey.SETTING_ABOUT_CONTACT);
		sourcePre = findPreference(PreferenceKey.SETTING_ABOUT_SOURCE);

		config();
	}

	private void config() {
		//主题
		themePre.setOnPreferenceClickListener(this);
		int themeId = themePre.getSharedPreferences().getInt(PreferenceKey.SETTING_THEME , Theme.WHITE.getId());
		themePre.setSummary(Theme.parse(themeId).getNameResId());

		//地区
		region = regionPreference.getSharedPreferences().getString(PreferenceKey.SETTING_REGIONS, null);
		if (region != null) {
			region = regionPreference.getSharedPreferences().getString(PreferenceKey.SETTING_REGIONS, null);
		} else {
			region = Region.CHINA.value();
		}
		regionPreference.setSummary(Region.getStringResID(Region.parse(region)));
		regionPreference.setOnPreferenceClickListener(this);

		//语言
		entry2Summary(languagePreference);
		languagePreference.setOnPreferenceChangeListener(this);

		//分段展示规则
		entry2Summary(displayRulePreference);
		displayRulePreference.setOnPreferenceChangeListener(this);
		
		jumpPreference.setOnPreferenceClickListener(this);
		setJumpSummary();

		//展示更多
		searchAcceleratePre.setOnPreferenceChangeListener(this);

		//更新资源文件
		updataResPre.setOnPreferenceClickListener(this);

		//清除图片缓存
		clearCache.setOnPreferenceClickListener(this);
		setCachePreSummary();

		//沉浸式状态栏
		statusUnitePreference.setOnPreferenceChangeListener(this);

		//检查更新
		versionPre.setOnPreferenceClickListener(this);
		setVersionSummary();

		//买咖啡
		buyCoffeePre.setOnPreferenceClickListener(this);

		//联系
		contactPre.setOnPreferenceClickListener(this);
		
		//开源相关
		sourcePre.setOnPreferenceClickListener(this);
	}

	/**
	 * 配置玩家id
	 */
	private void configPlayerName(final Preference pre) {
		final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
		dialog.setTitle(pre.getTitle());

		View v = LayoutInflater.from(getActivity()).inflate(R.layout.view_setting_bind_player, null);
		final EditText mEdtPlayerName = v.findViewById(R.id.et_view_setting_bind_player_name);
		mEdtPlayerName.setText(pre.getSharedPreferences().getString(pre.getKey(), ""));
		final Button mBtnApply = v.findViewById(R.id.btn_view_setting_bind_player_apply);
		mBtnApply.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1) {

					if (mEdtPlayerName.getText().toString().equals("")) {
						shortToast(getResString(R.string.setting_category_routine_jump_dialog_toast_success));
						SharedPreferences.Editor editor = pre.getEditor();
						editor.putString(pre.getKey(), mEdtPlayerName.getText().toString());
						editor.commit();
						pre.setSummary(getResString(R.string.setting_category_routine_jump_summary));
						dialog.dismiss();
						return;
					}

					NetTaskUtil.get().getPlayerByName(mEdtPlayerName.getText().toString(), Region.parse(regionPreference.getSharedPreferences().getString(regionPreference.getKey(), "cn")))
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new io.reactivex.Observer<String>(){

							@Override
							public void onSubscribe(Disposable p1) {
								shortToast(getResString(R.string.setting_category_routine_jump_dialog_toast_start));
								if (mBtnApply.isClickable()) {
									mBtnApply.setClickable(false);
								}
							}

							@Override
							public void onNext(String p1) {
								shortToast(getResString(R.string.setting_category_routine_jump_dialog_toast_success));
								SharedPreferences.Editor editor = pre.getEditor();
								editor.putString(pre.getKey(), mEdtPlayerName.getText().toString());
								editor.putString(pre.getKey() + "_region", regionPreference.getSharedPreferences().getString(regionPreference.getKey(), "cn"));
								editor.commit();
								pre.setSummary(mEdtPlayerName.getText().toString() + "[" + getResString(Region.parse(region).getResId()) + "]");
								new SuggestionPlayerSaveThread(mEdtPlayerName.getText().toString()).start();
								dialog.dismiss();
							}

							@Override
							public void onError(Throwable p1) {
								shortToast(getResString(R.string.setting_category_routine_jump_dialog_toast_error));
							}

							@Override
							public void onComplete() {
								if (!mBtnApply.isClickable()) {
									mBtnApply.setClickable(true);
								}
							}
						});
				}
			});

		dialog.setView(v);
		dialog.show();
	}

	public boolean onPreferenceClick(Preference p1) {

		if (p1 == themePre) {//设置主题
			setTheme((ExpandPreference) p1);
		} else if (p1 == regionPreference) {//选择地区
			pickRegion(p1);
		} else if (p1 == clearCache) {//清除缓存
			destroyCache(p1);
		} else if (p1 == updataResPre) {
			updataRes(p1);             
		} else if (p1 == versionPre) {//检查更新
			checkVersion(p1);
		} else if (p1 == buyCoffeePre) {//买咖啡
			BuyCoffee(p1);
		} else if (p1 == contactPre) {//反馈
			contactDeveloper(p1);
		} else if (p1 == sourcePre) {//开源
			openSource(p1);
		} else if (p1 == jumpPreference) {
			configPlayerName(p1);
		}

		return false;
	}
	
	// 设置主题 //
	private void setTheme(final ExpandPreference pre) {
		PopupMenu mPopupMenu = new PopupMenu(getActivity(), pre.getItemView());

		mPopupMenu.getMenuInflater().inflate(R.menu.menu_theme, mPopupMenu.getMenu());

		mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

				@Override
				public boolean onMenuItemClick(MenuItem p1) {
					SharedPreferences.Editor editor = pre.getEditor();

					if (p1.getTitle().equals(getResString(Theme.parse(pre.getSharedPreferences().getInt(PreferenceKey.SETTING_THEME, 0)).getNameResId()))) {
						return false;
					}

					switch (p1.getItemId()) {
						case R.id.item_white : 
							editor.putInt(PreferenceKey.SETTING_THEME, Theme.WHITE.getId());
							break;
						case R.id.item_purple : 
							editor.putInt(PreferenceKey.SETTING_THEME, Theme.INDIGO.getId());
							break;
						case R.id.item_green : 
							editor.putInt(PreferenceKey.SETTING_THEME, Theme.GREEN.getId());
							break;
						case R.id.item_black : 
							editor.putInt(PreferenceKey.SETTING_THEME, Theme.BLACK.getId());
							break;
						case R.id.item_pink : 
							editor.putInt(PreferenceKey.SETTING_THEME, Theme.PINK.getId());
							break;
						case R.id.item_blue:
							editor.putInt(PreferenceKey.SETTING_THEME, Theme.BLUE.getId());
							break;
						case R.id.item_red:
							editor.putInt(PreferenceKey.SETTING_THEME, Theme.RED.getId());
					}
					editor.commit();
					

					VaingloryGo vg = (VaingloryGo) getActivity().getApplication();
					try {
						for (Activity act : vg.getActivitys()) {
							if (act instanceof SettingActivity) {
								act.finish();
								startActivity(new Intent(getActivity(), SettingActivity.class));
								getActivity().overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
							} else {
								Theme.setThemeFromPreference(vg);
								act.recreate();
							}
						}
					} catch (Exception e) {
						Snackbar.make(getPreferenceView(), R.string.setting_category_routine_theme_change_failed, Snackbar.LENGTH_SHORT).show();
					}

					return false;
				}
			});
		mPopupMenu.setGravity(pre.getGravity());

		mPopupMenu.show();
		enableIcon(mPopupMenu);
	}

	// 选择地区 //
	private void pickRegion(final Preference p1) {
		new RegionSelectDialog(p1.getSharedPreferences())
			.creat(getActivity())
			.setTitle(p1.getTitle().toString())
			.loadView(p1)
			.show();

	}

	// 清除缓存 //
	private void destroyCache(Preference p1) {
		new MaterialDialog.Builder(getActivity())
			.title(R.string.setting_category_advance_destroy_cache_dialog_title)
			.content(R.string.setting_category_advance_destroy_cache_dialog_message)
			.positiveText(R.string.setting_category_advance_destroy_cache_dialog_confirm)
			.onPositive( new MaterialDialog.SingleButtonCallback(){

				@Override
				public void onClick(MaterialDialog dialog,DialogAction action) {
					//清除
					Observable.just(deletePicassoFile(cacheFile))
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new io.reactivex.Observer<Boolean>(){

							@Override
							public void onSubscribe(Disposable p1) {

							}

							@Override
							public void onNext(Boolean p1) {
								shortToast(getResString(R.string.setting_category_advance_destroy_cache_success));
								setCachePreSummary();
							}

							@Override
							public void onError(Throwable p1) {
								shortToast(getResString(R.string.setting_category_advance_destroy_cache_field) + " : " + p1.toString());
							}

							@Override
							public void onComplete() {


							}
						});
				}
			})
			.negativeText(R.string.setting_category_advance_destroy_cache_dialog_cancel).show();
	}

	// 更新资源文件 //
	private void updataRes(Preference p1) {
		if (!NetTaskUtil.isNetworkConnected(getActivity())) {
			Snackbar.make(getPreferenceView(), R.string.search_activity_error_no_network, Snackbar.LENGTH_SHORT).show();
			return;                                                          
		}

		//清除数据库
		DataSupport.deleteAll(Hero.class);
		DataSupport.deleteAll(Outfit.class);

		final ProgressDialog dialog = new ProgressDialog(getActivity());
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setMessage(getResString(R.string.search_activity_get_resource));

		final PullResourcePresenter presenter = new PullResourcePresenter();
		presenter.attachView(new PullResourceView(){

				@Override
				public void showLoading() {
					dialog.show();
				}

				@Override
				public void hideLoading() {
					dialog.dismiss();
					presenter.dispose();
					presenter.detachView();
				}

				@Override
				public void showError(Throwable error) {
					Toast.makeText(getActivity(), error + "", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void showUpdataSuccess() {

				}

				@Override
				public void showNoMore() {

				}
			});
		presenter.checkResource();
	}

	// 检查更新 //
	private void checkVersion(Preference p1) {
		VersionUtil.get().isShowToast(true).checkUpdate(this);
	}


	// 买咖啡 //
	private void BuyCoffee(Preference p1) {
		new DonationDialog(getActivity(), new View.OnClickListener(){

				@Override
				public void onClick(View p1) {
					if (AlipayUtil.get().hasInstalledAlipayClient(getActivity())) {
						AlipayUtil.get().startAlipayClient(getActivity(), HttpApi.ALIPAY_MY_CODE.value());
					} else {
						shortToast(getResString(R.string.setting_category_about_buy_a_coffee_toast));
					}
				}
			})
			.show();
	}

	// 联系 //
	private void contactDeveloper(Preference p1) {
		ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(android.content.Context.CLIPBOARD_SERVICE);
		cm.setText("vcoty233@163.com");
		shortToast(getResString(R.string.setting_category_about_contact_toast));
	}

	
	// 开源相关 //
	private void openSource(Preference p1) {
		new FullScreenDialogFragment.Builder(getActivity())
			.setTitle(p1.getTitle().toString())
			.setContent(WebViewFragment.class, new android.os.Bundle())
			.build().show(((BaseActivity) getActivity()).getSupportFragmentManager(), "");


	}

	@Override
	public boolean onPreferenceChange(final Preference preference, Object obj) {
		String value  = obj.toString();
		if (preference == languagePreference) {
			ListPreference mListPreference = (ListPreference) preference;

			int index = mListPreference.findIndexOfValue(value);
			preference.setSummary(index >= 0 ? mListPreference.getEntries()[index] : null);

			// 切换语言
			VaingloryGo vg = (VaingloryGo) getActivity().getApplication();
			LanguageUtil.get().changeLanguageByIndex(vg, index);
			try {                                  
				if (index == 0) {
					Snackbar.make(getPreferenceView(), R.string.setting_category_routine_language_snack_bar, Snackbar.LENGTH_SHORT).show();
				} else {                                                                       
					for (Activity act : vg.getActivitys()) {
						if (act instanceof SettingActivity) {
							act.finish();
							startActivity(new Intent(getActivity(), SettingActivity.class));
							getActivity().overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
						} else {
							act.recreate();
						}
					}
				}

			} catch (Exception e) {}


		} else if (preference == statusUnitePreference) {
			BaseActivity baseAct = (BaseActivity) getActivity();
			if (!preference.getSharedPreferences().getBoolean(PreferenceKey.SETTING_STATUS_UNITE, false)) {
				if (!StatusBarCompat.get().setStatusBarColor(baseAct, getResColor(baseAct.getAttrPrimaryColorId()))) {

				}
			} else {
				StatusBarCompat.get().setStatusBarColor(baseAct, getResColor(baseAct.getAttrPrimaryDarkColorId()));
			}
		} else if (preference == displayRulePreference) {
			int index = displayRulePreference.findIndexOfValue(value);
			preference.setSummary(index >= 0 ? displayRulePreference.getEntries()[index] : null);
		}
		return true;
	}

	//设置缓存项的summary
	private void setCachePreSummary() {
		try {
			float size = (float) getFileSizes(cacheFile) / 1048576f;
			BigDecimal mBigDecimal = new BigDecimal(size);
			clearCache.setSummary(getResString(R.string.setting_category_advance_destroy_cache_summary) +  mBigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue() + " M");
		} catch (Exception e) {
			clearCache.setSummary(null);
		}
	}

	private void setVersionSummary() {
		versionPre.setSummary(getResString(R.string.setting_category_about_version_summary) + VersionUtil.getLocalVersionName(getActivity()));
	}

	private void setJumpSummary() {
		String name = jumpPreference.getSharedPreferences().getString(jumpPreference.getKey(), "");
		if (name.equals("")) {
			jumpPreference.setSummary(getResString(R.string.setting_category_routine_jump_summary));
		} else {
			jumpPreference.setSummary(name + "[" +  getResString(Region.parse(jumpPreference.getSharedPreferences().getString(jumpPreference.getKey() + "_region", "cn")).getResId()) + "]");
		}
	}

	//删除缓存
	private boolean deletePicassoFile(File file) {
		try {
			if (getFileSizes(file) == 0) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		File[] files=file.listFiles();  
		for (File f:files) {  
			if (f.isDirectory()) {  
				deletePicassoFile(f);  
			} else {  
				f.delete();  
			}  
		}  
		return file.delete();  
	}


	//获取文件夹大小
	private long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

	public int dp2px(float dipValue) {
        final float scale = getActivity().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

	/**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    private long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
        }
        return size;
    }

	private void enableIcon(PopupMenu mPopupMenu) {
		try {
			Field field = mPopupMenu.getClass().getDeclaredField("mPopup");
			field.setAccessible(true);
			MenuPopupHelper mHelper = (MenuPopupHelper) field.get(mPopupMenu);
			mHelper.setForceShowIcon(true);
		} catch (IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected int getXmlId() {
		return R.xml.setting_preference;
	}
}
