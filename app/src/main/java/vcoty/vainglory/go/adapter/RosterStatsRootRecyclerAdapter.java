package vcoty.vainglory.go.adapter;

import android.animation.*;
import android.content.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import com.chad.library.adapter.base.*;
import com.razerdp.widget.animatedpieview.*;
import java.util.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.bean.item.*;
import vcoty.vainglory.go.utils.*;
import vcoty.vainglory.go.widget.*;

import vcoty.vainglory.go.R;

public class RosterStatsRootRecyclerAdapter extends BaseQuickAdapter<RosterStatsRootItem,BaseViewHolder> implements OnTouchListener {

	private Context ctx;
	
	public RosterStatsRootRecyclerAdapter(Context ctx,List<RosterStatsRootItem> data){
		super(R.layout.item_roster_stats_root,data);
		this.ctx = ctx;
	}

	@Override
	protected void convert(BaseViewHolder holder, RosterStatsRootItem item) {
		holder.setText(R.id.tv_item_roster_stats_root_title,item.getTitleRes());

		holder.setText(R.id.tv_item_roster_stats_root_total,item.getTotal() + "K");

		AnimatedPieView pieView = holder.getView(R.id.apv_item_roster_stats_root);
		AnimatedPieViewConfig config = new AnimatedPieViewConfig();
		config.startAngle(-90)
			.drawText(true)
			.autoSize(false)
			.pieRadius(DensityUtil.dp2px(ctx,80))
			.setStrokeWidth(DensityUtil.dp2px(ctx,38))
			.textSize(DensityUtil.sp2px(ctx,14))
			.duration(800);

		for(int i = 0 ; i < item.getPieInfos().size();i++){
			config.addData(item.getPieInfos().get(i));
		}

		pieView.start(config);

		RecyclerView mRecyclerView = holder.getView(R.id.rv_item_roster_stats_root_players);
		mRecyclerView.setFocusable(false);
		LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setNestedScrollingEnabled(false);

		//分割线
		mRecyclerView.addItemDecoration(new NormalItemDecoration.Builder(ctx)
										.colorResId(AttrResourceUtil.getAttrResId(ctx, R.attr.colorDivider))
										.sizeResId(R.dimen.divider_size)
										.marginResId(R.dimen.divider_margin_left, R.dimen.divider_margin_right)
										.build());

		RosterStatsPlayerRecyclerAdapter adapter = new RosterStatsPlayerRecyclerAdapter(ctx,null);
		mRecyclerView.setAdapter(adapter);

		if(item.getItems() != null && !item.getItems().isEmpty()){
			adapter.replaceData(item.getItems());
		}

		CardView mCvRoot = holder.getView(R.id.cv_item_roster_stats_root);
		mCvRoot.setOnTouchListener(this);
		
	}
	
	@Override
	public boolean onTouch(View p1, MotionEvent motionEvent) {
		switch (motionEvent.getAction()) {
			case MotionEvent.ACTION_DOWN:
				ObjectAnimator upAnim = ObjectAnimator.ofFloat(p1, "translationZ", 16);
				upAnim.setDuration(150);
				upAnim.setInterpolator(new DecelerateInterpolator());
				upAnim.start();
				break;

			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				ObjectAnimator downAnim = ObjectAnimator.ofFloat(p1, "translationZ", 0);
				downAnim.setDuration(150);
				downAnim.setInterpolator(new AccelerateInterpolator());
				downAnim.start();
				break;
		}
		return false;
	}

}
