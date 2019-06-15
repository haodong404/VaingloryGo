package vcoty.vainglory.go.utils;

import android.content.*;
import android.preference.*;
import java.util.*;
import org.litepal.*;
import vcoty.vainglory.go.bean.db.*;

public class HeroConverter {
	
	private static HeroConverter instance;

	public static synchronized HeroConverter getInstance() {
		if (instance == null) {
			instance = new HeroConverter();
		}
		return instance;
	}
	
	public String getHeroNameByEn(Context ctx, String nameEn) {
		String name = "Unknow";
		List<Hero> heroes = LitePal.where("nameEN = ?",ApiHeroNameAdaptation.adapt(nameEn)).find(Hero.class);
		if (heroes.isEmpty()) {
			return name;
		} else {
			switch (PreferenceManager.getDefaultSharedPreferences(ctx).getString(PreferenceKey.SETTING_LANGUAGE,LanguageUtil.AUTO )) {
				case LanguageUtil.AUTO :
					if (Locale.getDefault().getLanguage().equals(Locale.CHINESE.getLanguage())) {
						name = heroes.get(0).getNameCN();
					} else {
						name = heroes.get(0).getNameEN();
					}
					break;
				case LanguageUtil.CHINESE :
					name = heroes.get(0).getNameCN();
					break;
				case LanguageUtil.ENGLISH :
					name = heroes.get(0).getNameEN();
					break;
			}
			return name;
		}
	}
	
	public int getHeroThemeColorRes(Hero Hero){
		int[] colorReses = {R.color.MaterialYellow,R.color.MaterialTeal,R.color.MaterialRed,R.color.MaterialPink,R.color.MaterialOrange,R.color.MaterialGrey};
		
		return colorReses[(int) (0 + Math.random() * 6 )];
	}
}
