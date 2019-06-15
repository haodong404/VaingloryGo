package vcoty.vainglory.go.adapter;

import android.graphics.*;
import android.widget.*;
import com.chad.library.adapter.base.*;
import java.util.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.bean.item.*;
import vcoty.vainglory.go.enums.*;

public class ProfileSkillTireAdapter extends BaseQuickAdapter<ProfileSkillTireCardItem,BaseViewHolder> {

	private BaseFragment fm;
	
	public ProfileSkillTireAdapter(BaseFragment fm, List<ProfileSkillTireCardItem> data){
		super(R.layout.item_profile_skill_tire_card,data);
		this.fm = fm;
	}

	@Override
	protected void convert(BaseViewHolder holder, ProfileSkillTireCardItem item) {
		ImageView mIvIcon = holder.getView(R.id.iv_item_profile_skill_tire_card_mode_icon);
		Typeface mTfSkillTire = Typeface.createFromAsset(fm.getActivity().getAssets(), "SkillTires.ttf");

		//配置游戏模式icon
		if(item.getGameMode().equals(GameMode.RANKED)){	
			switch(item.getTierColor()){
				case R.color.TierCopper:
					mIvIcon.setImageDrawable(fm.getResDrawable(R.drawable.ic_kraken_copper));
					break;
				case R.color.TierGolden:
					mIvIcon.setImageDrawable(fm.getResDrawable(R.drawable.ic_kraken_golden));
					break;
				case R.color.TierSilver:
					mIvIcon.setImageDrawable(fm.getResDrawable(R.drawable.ic_kraken_silver));
					break;
				default :
					mIvIcon.setImageDrawable(fm.getResDrawable(R.drawable.ic_kraken_white));
					break;
			}
		}else if(item.getGameMode().equals(GameMode._5V5_PVP_RANKEND)){		
			switch(item.getTierColor()){
				case R.color.TierCopper:
					mIvIcon.setImageDrawable(fm.getResDrawable(R.drawable.ic_ghostwing_copper));
					break;
				case R.color.TierGolden:
					mIvIcon.setImageDrawable(fm.getResDrawable(R.drawable.ic_ghostwing_golden));
					break;
				case R.color.TierSilver:
					mIvIcon.setImageDrawable(fm.getResDrawable(R.drawable.ic_ghostwing_silver));
					break;
				default :
					mIvIcon.setImageDrawable(fm.getResDrawable(R.drawable.ic_ghostwing));
					break;
			}
		}else if(item.getGameMode().equals(GameMode.BLITZ_PVP_RANKED)){
			switch(item.getTierColor()){
				case R.color.TierCopper:
					mIvIcon.setImageDrawable(fm.getResDrawable(R.drawable.ic_bolt_copper));
					break;
				case R.color.TierGolden:
					mIvIcon.setImageDrawable(fm.getResDrawable(R.drawable.ic_bolt_golden));
					break;
				case R.color.TierSilver:
					mIvIcon.setImageDrawable(fm.getResDrawable(R.drawable.ic_bolt_silver));
					break;
				default :
					mIvIcon.setImageDrawable(fm.getResDrawable(R.drawable.ic_bolt));
					break;
			}
		}else{
			holder.setBackgroundColor(R.id.rl_item_profile_skill_tire_card,fm.getResColor(R.color.Black));
			mIvIcon.setImageDrawable(fm.getResDrawable(R.drawable.ic_kraken));
		}

		if(item.getRankPoints() < 0){
			holder.setText(R.id.tv_item_profile_skill_tire_card_tire_name,"@");
			holder.setText(R.id.tv_item_profile_skill_tire_card_points,"0");
		}else{
			holder.setText(R.id.tv_item_profile_skill_tire_card_tire_name,item.getSkillTier() + "");
			holder.setText(R.id.tv_item_profile_skill_tire_card_points,item.getRankPoints() + "");
		}
		holder.setBackgroundColor(R.id.rl_item_profile_skill_tire_card,fm.getResColor(item.getTierColor()));
		TextView mTvSkillTier = holder.getView(R.id.tv_item_profile_skill_tire_card_tire_name);
		TextView mTvSkillTierNum = holder.getView(R.id.tv_item_profile_skill_tire_card_points);
		switch(item.getTierColor()){
			case R.color.TierCopper:
				mTvSkillTier.setTextColor(fm.getResColor(R.color.TierCopperText));
				mTvSkillTierNum.setTextColor(fm.getResColor(R.color.TierCopperText));
				break;
			case R.color.TierGolden:
				mTvSkillTier.setTextColor(fm.getResColor(R.color.TierGoldenText));
				mTvSkillTierNum.setTextColor(fm.getResColor(R.color.TierGoldenText));
				break;
			case R.color.TierSilver:
				mTvSkillTier.setTextColor(fm.getResColor(R.color.TierSilverText));
				mTvSkillTierNum.setTextColor(fm.getResColor(R.color.TierSilverText));
				break;
			default :
				mTvSkillTier.setTextColor(fm.getResColor(R.color.White));
				mTvSkillTierNum.setTextColor(fm.getResColor(R.color.White));
				break;
		}

		holder.setTypeface(mTfSkillTire,R.id.tv_item_profile_skill_tire_card_tire_name);
	}
}
