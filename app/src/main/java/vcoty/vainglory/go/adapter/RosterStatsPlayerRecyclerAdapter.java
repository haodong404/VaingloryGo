package vcoty.vainglory.go.adapter;
import android.content.*;
import android.view.animation.*;
import com.chad.library.adapter.base.*;
import com.squareup.picasso.*;
import de.hdodenhof.circleimageview.*;
import java.text.*;
import java.util.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.bean.item.*;
import vcoty.vainglory.go.utils.*;
import vcoty.vainglory.go.widget.*;

import vcoty.vainglory.go.R;

public class RosterStatsPlayerRecyclerAdapter extends BaseQuickAdapter<RosterStatsPlayerItem,BaseViewHolder> {

	private Context ctx;
	
	public RosterStatsPlayerRecyclerAdapter(Context ctx, List<RosterStatsPlayerItem> data){
		super(R.layout.item_roster_stats_player,data);
		this.ctx = ctx;
	}

	@Override
	protected void convert(BaseViewHolder holder, RosterStatsPlayerItem item) {
		final CircleImageView headImageView = holder.getView(R.id.circle_image_tv_item_roster_stats_player_head_pic);

		Picasso.get().load(item.getUrl()).error(R.drawable.ic_error_pic).into(headImageView,
			new com.squareup.picasso.Callback(){

				@Override
				public void onSuccess() {
					headImageView.setAnimation(AnimationUtils.loadAnimation(ctx, R.anim.abc_fade_in));
				}

				@Override
				public void onError(Exception p1) {
					headImageView.setAnimation(AnimationUtils.loadAnimation(ctx, R.anim.abc_fade_in));
				}
			});

		if(item.isRed() && !item.isMe()){
			headImageView.setBorderColorResource(R.color.MaterialRed);
		}else if(item.isMe()){
			headImageView.setBorderColorResource(R.color.MaterialLightGreen);
		}else{
			headImageView.setBorderColorResource(R.color.MaterialBlue);
		}

		if(item.isMe()){
			headImageView.setBorderWidth(DensityUtil.dp2px(ctx,2.5f));
		}else{
			headImageView.setBorderWidth(DensityUtil.dp2px(ctx,1));
		}

		DecimalFormat df = new DecimalFormat("######0.00");

		NumberProgressBar damageProgressBar = holder.getView(R.id.npb_item_roster_stats_player_damage);
		damageProgressBar.setProgress((int)((float) item.getDamage() / item.getDamageTotal() * 100));
		holder.setText(R.id.tv_item_roster_stats_player_damage,df.format((float) item.getDamage() / 1000) + "K");

		NumberProgressBar dealtProgressBar = holder.getView(R.id.npb_item_roster_stats_player_dealt);
		dealtProgressBar.setProgress((int)((float) item.getDealt() / item.getDealtTotal() * 100));
		holder.setText(R.id.tv_item_roster_stats_player_dealt,df.format((float) item.getDealt() / 1000) + "K");

		NumberProgressBar healtProgressBar = holder.getView(R.id.npb_item_roster_stats_player_healed);
		healtProgressBar.setProgress((int)((float)item.getHealt() / item.getHealedTotal() * 100));
		holder.setText(R.id.tv_item_roster_stats_player_healed,df.format((float) item.getHealt() / 1000) + "K");
		
	}
}
