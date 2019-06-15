package vcoty.vainglory.go.enums;

import android.content.*;
import android.preference.*;
import android.support.v7.app.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.utils.*;

public enum Theme {
	//经典白
	WHITE(0,R.string.setting_category_routine_theme_white,R.style.DefaultTheme),
	//彩鹀靛
	INDIGO(1,R.string.setting_category_routine_theme_purple,R.style.IndigoTheme),
	//原谅绿
	GREEN(2,R.string.setting_category_routine_theme_green,R.style.GreenTheme),
	//暗夜黑
	BLACK(3,R.string.setting_category_routine_theme_black,R.style.DarkTheme),
	//少女粉
	PINK(4,R.string.setting_category_routine_theme_pink,R.style.PinkTheme),
	//知乎蓝
	BLUE(5,R.string.setting_category_routine_theme_blue,R.style.BlueTheme),
	//中国红
	RED(6,R.string.setting_category_routine_theme_red,R.style.RedTheme);
	
	private int id;
	private int nameId;
	private int styleId;
	
	public Theme(int id,int nameId,int styleId){
		this.id = id;
		this.nameId = nameId;
		this.styleId = styleId;
	}
	
	public static void setThemeFromPreference(Context ctx){
		int theme = PreferenceManager.getDefaultSharedPreferences(ctx).getInt(PreferenceKey.SETTING_THEME, WHITE.getId());

		if(theme != BLACK.getId()){
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
		}else{
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
		}
		
		ctx.setTheme(parse(theme).getStyleResId());

	}
	
	public static Theme parse(int id){
		for(Theme theme : values()){
			if(theme.getId() == id){
				return theme;
			}
		}
		return WHITE;
	}
	
	
	// 返回第一个枚举的值 //
	public int getId(){
		return this.id;
	}

	// 返回第二个枚举值 //
	public int getNameResId(){
		return this.nameId;
	}
	
	// 返回第三个枚举值 //
	public int getStyleResId(){
		return this.styleId;
	}
}
