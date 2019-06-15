package vcoty.vainglory.go.utils;

import android.content.*;
import vcoty.vainglory.go.R;

public class SkillTierConverter {

	public static SkillTierConverter get() {
		return SkillTierConverterHolder.mSkillTierConverter;
	}

	public static class SkillTierConverterHolder {
		private static final SkillTierConverter mSkillTierConverter = new SkillTierConverter();
	}

	/**
	 * 根据points计算出skillTier
	 */
	public int convertPoints(int points) {
		
		if (points <= 0) {
			return -1;
		} else if(points <= 1089.9) {
			return (int) (points / 108.9 );
		}else if(points >= 1090 && points <= 1199.9){
			return 10 + (int) ((points - 1090) / 109.9);
		}else if(points >= 1200 && points <= 1349.9){
			return 11 +  (int)  ((points - 1200) / 49.9);
		}else if(points >= 1350 && points <= 1399.9){
			return 14 + (int) ((points - 1350) / 46.9);
		}else if(points >= 1400 && points <= 1999.9){
			return 15 + (int) ((points - 1400) / 66.9);
		}else if(points >= 2000 && points <= 2133.9){
			return 24 + (int) ((points - 2000) / 133.9);
		}else if(points >= 2134 && points <= 2399.9){
			return 25 + (int) ((points - 2134) / 132.9);
		}else if(points >= 2400 && points <= 2799.9){
			return 27 + (int) ((points - 2400) / 199.9);
		}else{
			return 29;
		}
	}

	/**
	 * 返回段位数字
	 */
	public int getTierNum(int num) {
		int tier = num / 3 + 1 ;
		if (tier == 10) {
			return 0;
		} else if (tier == -1) {
			return -1;
		}
		return tier;
	}

	/**
	 * 返回金银铜段背景
	 */
	public int getTierColor(Context ctx, int tier) {
		int RecID = R.color.MaterialPink;
		int tinyTier = tier % 3;
		switch (tinyTier) {
			case 0:
				RecID = R.color.TierCopper;
				break;
			case 1:
				RecID = R.color.TierSilver;
				break;
			case 2:
				RecID = R.color.TierGolden;
				break;	
		}

		return RecID;
	}
	

	/**
	 * 返回金银铜段字体颜色
	 */
	public int getTierTextColor(Context ctx, int tier) {
		int RecID = R.color.MaterialPink;
		int tinyTier = tier % 3;
		switch (tinyTier) {
			case 0:
				RecID = R.color.TierCopperText;
				break;
			case 1:
				RecID = R.color.TierSilverText;
				break;
			case 2:
				RecID = R.color.TierGoldenText;
				break;	
		}

		return RecID;
	}
}
