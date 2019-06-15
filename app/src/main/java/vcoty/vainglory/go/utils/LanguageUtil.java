package vcoty.vainglory.go.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import java.util.Locale;

public class LanguageUtil {
	
	public static LanguageUtil get(){
		return LanguageUtilHolder.mLanguageUtil;
	}
	
	public static class LanguageUtilHolder {
		private static final LanguageUtil mLanguageUtil = new LanguageUtil();
	}
	
	public static final String AUTO = "auto";
	public static final String CHINESE = "cn";
	public static final String ENGLISH = "en";

	public void changeLanguage(Context ctx, String language) {
		Resources res = ctx.getResources();
		Configuration config = res.getConfiguration();
		DisplayMetrics metrics = res.getDisplayMetrics();

		if (Locale.getDefault().equals(Locale.CHINESE) && Locale.getDefault().equals(Locale.ENGLISH)) {
			config.setLocale(Locale.ENGLISH);
		} else {
			switch (language) {
				case AUTO: //自动
					config.setLocale(Locale.getDefault());
					break;
				case CHINESE: //中文
					config.setLocale(Locale.CHINESE);
					break;
				case ENGLISH: //英文
					config.setLocale(Locale.ENGLISH);
					break;
			}	
		}
		res.updateConfiguration(config, metrics);
	}


	/**
	 * 用id来切换(主要用于设置)
	 */
	public void changeLanguageByIndex(Context ctx, int language) {
		Resources res = ctx.getResources();
		Configuration config = res.getConfiguration();
		DisplayMetrics metrics = res.getDisplayMetrics();
		if (Locale.getDefault() != Locale.CHINESE && Locale.getDefault() != Locale.ENGLISH) {
			config.setLocale(Locale.ENGLISH);
		} else {
			switch (language) {
				case 0: //自动
					config.setLocale(Locale.getDefault());
					break;
				case 1: //中文
					config.setLocale(Locale.CHINESE);
					break;
				case 2: //英文
					config.setLocale(Locale.ENGLISH);
					break;
			}	
		}
		res.updateConfiguration(config, metrics);
	}
}
