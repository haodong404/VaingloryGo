package vcoty.vainglory.go.adapter;
import android.content.*;
import android.view.animation.*;
import com.chad.library.adapter.base.*;
import com.squareup.picasso.*;
import de.hdodenhof.circleimageview.*;
import java.util.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.bean.item.*;

import vcoty.vainglory.go.R;

public class RosterOverviewItemAdapter extends BaseQuickAdapter<RosterItemsItem,BaseViewHolder> {

	private Context ctx;

	public RosterOverviewItemAdapter(Context ctx,List<RosterItemsItem> datas){
		super(R.layout.item_roster_overview_items,datas);
		this.ctx = ctx;
	}

	@Override
	protected void convert(BaseViewHolder holder, RosterItemsItem item) {
		final CircleImageView imageView = holder.getView(R.id.circle_text_iv_item_roster_overview_item);

		Picasso.get().load(item.getUrl()).error(R.drawable.ic_error_pic).into(imageView,
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
	}
	
}
