package vcoty.vainglory.go.adapter;

import android.content.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import com.chad.library.adapter.base.*;
import com.squareup.picasso.*;
import java.util.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.bean.item.*;

public class MatchsRecyclerAdapter extends BaseQuickAdapter<MatchsListItem,BaseViewHolder> {
	private Context ctx;


	public MatchsRecyclerAdapter(Context ctx,List<MatchsListItem> datas){
		super(R.layout.item_match_fragment,datas);
		this.ctx = ctx;
	}

	@Override
	protected void convert(BaseViewHolder holder, MatchsListItem item) {
		final ImageView imageView = holder.getView(R.id.circle_text_iv_match_item_hero);
		Picasso.get().load(item.getHeadImgUrl()).into(imageView,
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

		// 配置是否获胜的字体颜色 //
		if(item.isWon()){
			holder.setTextColor(R.id.tv_match_item_aftermath,getResColor( R.color.MaterialBlue));
			holder.setText(R.id.tv_match_item_aftermath,R.string.match_item_victory);
		}else{
			holder.setTextColor(R.id.tv_match_item_aftermath,getResColor(R.color.MaterialRed));
			holder.setText(R.id.tv_match_item_aftermath,R.string.match_item_defeat);
		}
		holder.setText(R.id.tv_match_item_mode,item.getGameMode().getStringResId());
		holder.setText(R.id.tv_match_item_kda,item.getKda());
		holder.setText(R.id.tv_match_item_date,item.getDate());

		ImageView mvpLayout = holder.getView(R.id.iv_match_fragment_mvp);
		//配置是否mvp
		if(item.isMvp()){
			mvpLayout.setVisibility(View.VISIBLE);
		}else{
			mvpLayout.setVisibility(View.GONE);
		}
		
	}
	
	private int getResColor(int resId){
		return ctx.getResources().getColor(resId);
	}
}
