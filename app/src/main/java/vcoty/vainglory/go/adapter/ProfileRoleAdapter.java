package vcoty.vainglory.go.adapter;

import android.graphics.*;
import com.chad.library.adapter.base.*;
import java.util.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.bean.item.*;
import vcoty.vainglory.go.enums.*;

public class ProfileRoleAdapter extends BaseQuickAdapter<ProfileRoleItem,BaseViewHolder> {

	private BaseFragment fm;
	
	public ProfileRoleAdapter(BaseFragment fm,List<ProfileRoleItem> data) {
		super(R.layout.item_profile_roles,data);
		this.fm = fm;
	}

	@Override
	protected void convert(BaseViewHolder holder, ProfileRoleItem item) {
		Typeface typeKollektif = Typeface.createFromAsset(fm.getActivity().getAssets(), "Kollektif-Bold.ttf");

		//位置图标
		if(item.getRole().equals(Role.CAPTAIN)) {
			holder.setImageResource(R.id.iv_item_profile_roles_role,R.drawable.ic_captain);
		}else if(item.getRole().equals(Role.CARRY)) {
			holder.setImageResource(R.id.iv_item_profile_roles_role,R.drawable.ic_carry);
		}else{
			holder.setImageResource(R.id.iv_item_profile_roles_role,R.drawable.ic_jungler);
		}

		holder.setText(R.id.tv_item_profile_roles_kills,item.getKills());
		holder.setText(R.id.tv_item_profile_roles_deaths,item.getDeaths());
		holder.setText(R.id.tv_item_profile_roles_assists,item.getAssists());

		holder.setText(R.id.tv_item_profile_roles_winrate,item.getWinrate() + "%");
		holder.setTypeface(typeKollektif,R.id.tv_item_profile_roles_winrate);
		if(item.getWinrate() < 60){
			holder.setTextColor(R.id.tv_item_profile_roles_winrate,getResColor(R.color.MaterialRed));
		}else if(item.getWinrate() >= 60 && item.getWinrate() < 80){
			holder.setTextColor(R.id.tv_item_profile_roles_winrate,getResColor(R.color.MaterialGreen));
		}else{
			holder.setTextColor(R.id.tv_item_profile_roles_winrate,getResColor(R.color.MaterialBlue));
		}


		holder.setText(R.id.tv_item_profile_roles_playeds,item.getFields() + " " + fm.getResString(R.string.profile_role_field));
		holder.setText(R.id.tv_item_profile_roles_kda,item.getKda());
	}
	
	private int getResColor(int resId){
		return fm.getActivity().getResources().getColor(resId);
	}
}
