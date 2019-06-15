package vcoty.vainglory.go.enums;

import android.content.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.utils.*;

public enum GameMode {
	ALL("",R.string.mode_all), 

	RANKED("ranked",R.string.mode_ranked),

	CASUAL("casual",R.string.mode_casual),

	CASUAL_ARAL("casual_aral",R.string.mode_casual_aral),

	PRIVATE("private",R.string.mode_private),

	PRIVATE_PARTY_VG_5V5("private_party_vg_5v5",R.string.mode_private_party_vg_5v5),

	PRIVATE_PARTY_DRAFT_MATCH_5V5("private_party_draft_match_5v5",R.string.mode_private_party_draft_match_5v5),
	
	PRIVATE_PARTY_DRAFT_MATCH("private_party_draft_match",R.string.mode_private_party_draft_match),

	PRIVATE_PARTY_ARAL_MATCH("private_party_aral_match",R.string.mode_private_party_aral_match),

	PRIVATE_PARTY_BLITZ_MATCH("private_party_blitz_match",R.string.mode_private_party_blitz_match),

	_5V5_PVP_RANKEND("5v5_pvp_ranked",R.string.mode_5v5_pvp_ranked),

	_5V5_PVP_CASUAL("5v5_pvp_casual",R.string.mode_5v5_pvp_casual),

	BLITZ_PVP_RANKED("blitz_pvp_ranked",R.string.mode_blitz_pvp_ranked);

	private String content;
	private int resId;
	
	public GameMode(String content,int resId){
		this.content = content;
		this.resId = resId;
	}

	//转换为GameMode
	public static GameMode parse(String content){
		for(GameMode mode : values()){
			if(mode.getContent().equals(content)){		
				return mode;
			}
		}
		return ALL;
	}
	
	public static GameMode parseById(int id){
		GameMode mode = ALL;
		switch(id){
			case R.id.item_mode_all:
				mode = ALL;
				break;
			case R.id.item_mode_ranked:
				mode = RANKED;
				break;
			case R.id.item_mode_5v5_pvp_casual:
				mode = _5V5_PVP_CASUAL;
				break;
			case R.id.item_mode_5v5_pvp_ranked:
				mode = _5V5_PVP_RANKEND;
				break;
			case R.id.item_mode_blitz_pvp_ranked:
				mode = BLITZ_PVP_RANKED;
				break;
			case R.id.item_mode_casual:
				mode = CASUAL;
				break;
			case R.id.item_mode_casual_aral:
				mode = CASUAL_ARAL;
				break;
		}
		return mode;
	}
	
	// 由选择器来转换 //
	public static int convertById(Context ctx,int id){
		int stringId = ALL.getStringResId();
		SharedPreferences.Editor preference = ctx.getSharedPreferences(PreferenceKey.MODES_PREFERENCE, Context.MODE_PRIVATE).edit();
		switch(id){
			case R.id.item_mode_all:
				stringId = ALL.getStringResId();
				preference.putString("mode", ALL.getContent());
				break;
			case R.id.item_mode_ranked:
				stringId = RANKED.getStringResId();
				preference.putString("mode", RANKED.getContent());
				break;
			case R.id.item_mode_5v5_pvp_casual:
				stringId = _5V5_PVP_CASUAL.getStringResId();
				preference.putString("mode", _5V5_PVP_CASUAL.getContent());
				break;
			case R.id.item_mode_5v5_pvp_ranked:
				stringId = _5V5_PVP_RANKEND.getStringResId();
				preference.putString("mode", _5V5_PVP_RANKEND.getContent());
				break;
			case R.id.item_mode_blitz_pvp_ranked:
				stringId = BLITZ_PVP_RANKED.getStringResId();
				preference.putString("mode", BLITZ_PVP_RANKED.getContent());
				break;
			case R.id.item_mode_casual:
				stringId = CASUAL.getStringResId();
				preference.putString("mode", CASUAL.getContent());
				break;
			case R.id.item_mode_casual_aral:
				stringId = CASUAL_ARAL.getStringResId();
				preference.putString("mode", CASUAL_ARAL.getContent());
				break;
		}
		preference.commit();
		return stringId;
	}
	
	// 返回第一个枚举的值 //
	public String getContent(){
		return content;
	}

	// 返回第二个枚举值 //
	public int getStringResId(){
		return resId;
	}
	
	@Override
	public String toString() {
		return  "name : " + name() + "\nvalue : " + content + 
			" ; value2 : " + resId ;
	}

}
