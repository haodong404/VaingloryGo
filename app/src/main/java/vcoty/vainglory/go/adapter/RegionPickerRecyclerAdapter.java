package vcoty.vainglory.go.adapter;

import android.content.*;
import android.graphics.*;
import android.view.*;
import android.widget.*;
import com.chad.library.adapter.base.*;
import java.util.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.bean.item.*;
import vcoty.vainglory.go.enums.*;
import vcoty.vainglory.go.widget.*;

import vcoty.vainglory.go.enums.Region;

public class RegionPickerRecyclerAdapter extends BaseQuickAdapter<RegionPickerItem,BaseViewHolder> {
	private Context ctx ;

	public RegionPickerRecyclerAdapter(Context ctx, List<RegionPickerItem> data){
		super(R.layout.item_region_pick_dialog,data);
		this.ctx = ctx;
	}

	@Override
	protected void convert(BaseViewHolder holder, RegionPickerItem item) {
		CircleTextImageView headimg = holder.getView(R.id.circle_text_iv_item_region_picker_dialog_head_img);
		headimg.setFillColorResource(Region.getColorResID(item.getRegion()));
		headimg.setText(item.getRegion().value().toUpperCase());
		headimg.setTextColor(Color.WHITE);

		ImageView isPickedIcon = holder.getView(R.id.iv_item_region_picker_dialog_is_picker_icon);
		if(item.isPicked()){
			isPickedIcon.setVisibility(View.VISIBLE);
		}else{
			isPickedIcon.setVisibility(View.GONE);
		}


		holder.setText(R.id.tv_item_region_picker_dialog_region_name,item.getRegionNameId());
		
	}
}
