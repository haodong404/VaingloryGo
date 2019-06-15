package vcoty.vainglory.go.adapter;

import android.content.*;
import android.graphics.*;
import android.view.animation.*;
import com.chad.library.adapter.base.*;
import com.squareup.picasso.*;
import de.hdodenhof.circleimageview.*;
import java.util.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.bean.item.*;

import vcoty.vainglory.go.R;

public class ProfilePlayedHeroesAdapter extends BaseQuickAdapter<ProfilePlayedHeroIitem,BaseViewHolder> {

	private Context ctx;
	
	public ProfilePlayedHeroesAdapter(Context ctx,List<ProfilePlayedHeroIitem> data){
		super(R.layout.item_profile_played_hero,data);
		this.ctx = ctx;
	}

	@Override
	protected void convert(BaseViewHolder holder, ProfilePlayedHeroIitem item) {
		Typeface typeKollektif = Typeface.createFromAsset(ctx.getAssets(), "Kollektif-Bold.ttf");

		final CircleImageView imageView = holder.getView(R.id.circle_text_iv_item_profile_played_hero_head_image);
		Picasso.get().load(item.getUrl()).into(imageView,
			new com.squareup.picasso.Callback(){

				@Override
				public void onSuccess() {
					imageView.setAnimation(AnimationUtils.loadAnimation(ctx, R.anim.abc_fade_in));
				}

				@Override
				public void onError(Exception p1) {
					imageView.setAnimation(AnimationUtils.loadAnimation(ctx, R.anim.abc_fade_in));
				}
			});
		holder.setText(R.id.tv_item_profile_played_hero_name,item.getName());
		holder.setText(R.id.tv_item_profile_played_hero_fields,item.getPlayeds() + " " + ctx.getResources().getString(R.string.profile_role_field));
		holder.setText(R.id.tv_item_profile_played_hero_kills_deaths,(int)item.getKills() + "/" + (int)item.getDeaths() + "/" + (int)item.getAssists());
		holder.setText(R.id.tv_item_profile_played_hero_kda,"KDA:" + item.getKda());

		//胜率
		holder.setText(R.id.tv_item_profile_played_hero_winrate,item.getWinrate() + "%");
		holder.setTypeface(typeKollektif,R.id.tv_item_profile_played_hero_winrate);
		if(item.getWinrate() < 60){
			holder.setTextColor(R.id.tv_item_profile_played_hero_winrate,getResColor(R.color.MaterialRed));
		}else if(item.getWinrate() >= 60 && item.getWinrate() < 80){
			holder.setTextColor(R.id.tv_item_profile_played_hero_winrate,getResColor(R.color.MaterialGreen));
		}else{
			holder.setTextColor(R.id.tv_item_profile_played_hero_winrate,getResColor(R.color.MaterialBlue));
		}
		
	}
	
	private int getResColor(int resId){
		return ctx.getResources().getColor(resId);
	}
}
