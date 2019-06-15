package vcoty.vainglory.go.adapter;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import com.chad.library.adapter.base.*;
import com.squareup.picasso.*;
import de.hdodenhof.circleimageview.*;
import java.util.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.bean.item.*;
import vcoty.vainglory.go.enums.*;
import vcoty.vainglory.go.ui.*;
import vcoty.vainglory.go.utils.*;

import vcoty.vainglory.go.R;
import vcoty.vainglory.go.enums.Region;

public class RosterRecyclerAdapter extends BaseSectionQuickAdapter<RosterOverviewSection,BaseViewHolder> {

	private Activity ctx;
	
	public RosterRecyclerAdapter(Activity context, List<RosterOverviewSection> datas) {
        super(R.layout.item_roster_overview_list,R.layout.view_roster_overview_list_section,datas);
		this.ctx = context;
    }

	@Override
	protected void convert(BaseViewHolder holder, RosterOverviewSection item) {
		final RosterOverviewItem data = item.getItem();
		
		Typeface mTfSkillTire = Typeface.createFromAsset(ctx.getAssets(), "SkillTires.ttf");

		final CircleImageView headImageview = holder.getView(R.id.circle_text_iv_item_roster_overview_list_head_img);
		TextView mTvPlayerName = holder.getView(R.id.tv_item_roster_overview_list_player_name);

		//队伍颜色
		if(data.isRedSide()){
			headImageview.setBorderColorResource(R.color.MaterialRed);
		}else{
			headImageview.setBorderColorResource(R.color.MaterialBlue);
		}

		//标记自己
		if(data.isMe()){
			holder.setTextColor(R.id.tv_item_roster_overview_list_player_name,getResColor(R.color.MaterialLightGreen));
			headImageview.setBorderColorResource(R.color.MaterialLightGreen);
			headImageview.setBorderWidth(DensityUtil.dp2px(ctx,2.5f));
		}else{
			holder.setTextColor(R.id.tv_item_roster_overview_list_player_name,AttrResourceUtil.getAttrData(ctx,R.attr.colorTextStandard));
		}

		//标记MVP
		ImageView mIvMvpTag = holder.getView(R.id.iv_item_roster_overview_list_mvp_tag);
		if(data.isMvp()){
			mIvMvpTag.setVisibility(View.VISIBLE);
		}else{
			mIvMvpTag.setVisibility(View.GONE);
		}

		Picasso.get().load(data.getUrl()).into(headImageview,
			new com.squareup.picasso.Callback(){

				@Override
				public void onSuccess() {
					headImageview.setAnimation(AnimationUtils.loadAnimation(ctx, R.anim.abc_fade_in));
				}

				@Override
				public void onError(Exception p1) {
					headImageview.setAnimation(AnimationUtils.loadAnimation(ctx, R.anim.abc_fade_in));
				}
			});

		//段位
		holder.setTypeface(mTfSkillTire,R.id.tv_item_roster_overview_list_skill_tier);
		holder.setText(R.id.tv_item_roster_overview_list_skill_tier,SkillTierConverter.get().getTierNum(data.getSkillTier()) + "");
		holder.setTextColor(R.id.tv_item_roster_overview_list_skill_tier,getResColor(SkillTierConverter.get().getTierTextColor(ctx,data.getSkillTier())));

		holder.setText(R.id.tv_item_roster_overview_list_rank_points,data.getRankPoints() + "");

		StringBuilder playerName = new StringBuilder();
		if(!data.getGuild().equals("")){
			playerName.append(data.getGuild() + "_");
		}
		playerName.append(data.getPlayerName());

		mTvPlayerName.setText(playerName);

		mTvPlayerName.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1) {
					Intent mIntent = new Intent(ctx,MainActivity.class);
					mIntent.putExtra(ExtraKey.EXTRA_KEY_PLAYER_NAME,data.getPlayerName());
					mIntent.putExtra(ExtraKey.EXTRA_KEY_REGION,ctx.getPreferences(ctx.MODE_PRIVATE).getString(PreferenceKey.SETTING_REGIONS,Region.CHINA.value()));
					mIntent.putExtra(ExtraKey.EXTRA_KEY_BOOLEAN_1,true);
					ctx.startActivity(mIntent);
				}
			});

		if(getDensityDpi() >= 400 && getDensityDpi() <= 440){
			mTvPlayerName.setEms(5);
		}else if(getDensityDpi() > 440 && getDensityDpi() <= 460){
			mTvPlayerName.setEms(4);
		}else if(getDensityDpi() > 460){
			mTvPlayerName.setEms(3);
		}else if(getDensityDpi() < 400){
			mTvPlayerName.setEms(7);
		}
		holder.setText(R.id.tv_item_roster_overview_list_kda,data.getKda());
		holder.setText(R.id.tv_item_roster_overview_list_farm,data.getFarm());
		holder.setText(R.id.tv_item_roster_overview_list_gold,data.getGold());
		holder.setText(R.id.tv_item_roster_overview_list_kda_num,data.getKdaNum());

		RecyclerView itemsTecyclerView = holder.getView(R.id.rv_item_roster_overview_list_items);
		itemsTecyclerView.setNestedScrollingEnabled(false);
		LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
		//分割线

		layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		itemsTecyclerView.setLayoutManager(layoutManager);

		RosterOverviewItemAdapter adapter = new RosterOverviewItemAdapter(ctx,data.getItems());
		itemsTecyclerView.setAdapter(adapter);
	}

	@Override
	protected void convertHead(BaseViewHolder holder, RosterOverviewSection item) {
		RosterOverviewItemHeader section = item.getHeader();
		holder.setText(R.id.tv_view_roster_overview_list_section_is_win,section.getIsWinRes());

		//更改胜利或失败的颜色
		if(section.getIsWinRes() == R.string.match_item_victory){
			holder.setTextColor(R.id.tv_view_roster_overview_list_section_is_win,getResColor(R.color.MaterialBlue));
		}else{
			holder.setTextColor(R.id.tv_view_roster_overview_list_section_is_win,getResColor(R.color.MaterialRed));
		}

		holder.setText(R.id.tv_view_roster_overview_list_section_kills,section.getKills());
		holder.setText(R.id.tv_view_roster_overview_list_section_turret,section.getTurret());
		holder.setText(R.id.tv_view_roster_overview_list_section_gold,section.getGold());
		holder.setText(R.id.tv_view_roster_overview_list_section_jungle_kills,section.getKaren());
		if(section.isRedSide()){
			holder.setImageResource(R.id.ic_view_roster_overview_list_section_flag,R.drawable.ic_flag_red);
		}else{
			holder.setImageResource(R.id.ic_view_roster_overview_list_section_flag,R.drawable.ic_flag_blue);
		}
	}
	
	private float getDensityDpi() {
        DisplayMetrics dm = new DisplayMetrics();
        ctx.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi;
    }
	
	private int getResColor(int resId){
		return ctx.getResources().getColor(resId);
	}
	
}
