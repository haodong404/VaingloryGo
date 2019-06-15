package vcoty.vainglory.go.widget;
import android.content.*;
import android.preference.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import com.chad.library.adapter.base.*;
import java.util.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.adapter.*;
import vcoty.vainglory.go.bean.item.*;
import vcoty.vainglory.go.enums.*;
import vcoty.vainglory.go.utils.*;

public class RegionSelectDialog {

	private SharedPreferences preference;
	private AlertDialog dialog;
	private String region;
	private Context ctx;

	private OnRegionSelectedListener lis;

	public interface OnRegionSelectedListener {
		/**
		 * 选择回调
		 */
		void onRegionSelected(Region region)
	}

	public RegionSelectDialog(SharedPreferences preference) {
		this.preference = preference;
	}

	public RegionSelectDialog creat(Context ctx) {
		dialog = new AlertDialog.Builder(ctx).create();
		this.ctx = ctx;
		return this;
	}

	public RegionSelectDialog setTitle(String title) {
		dialog.setTitle(title);
		return this;
	}

	public RegionSelectDialog setTitle(int resId) {
		dialog.setTitle(resId);
		return this;
	}

	public RegionSelectDialog loadView(final Preference pre) {
		//找到view
		View v = LayoutInflater.from(ctx).inflate(R.layout.view_region_picker_dialog, null);
		//配置recycler
		RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_view_setting_region_dialog);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));

		//添加数据
		Region[] regions = {Region.CHINA,Region.EAST_ASIA,Region.EUROPE,Region.NORTH_AMERICA,Region.SOUTH_AMERICA,Region.SOUTHEAST_ASIA};
		List<RegionPickerItem> items = new ArrayList<RegionPickerItem>(6);
		for (int i = 0;i < regions.length ; i++) {
			RegionPickerItem item = new RegionPickerItem();
			item.setRegion(regions[i]);
			item.setRegionNameId(Region.getStringResID(regions[i]));

			region = preference.getString(PreferenceKey.SETTING_REGIONS, null);
			if (region == null) {
				region = Region.CHINA.value();
			}
			if (region.equals(regions[i].value())) {
				item.setIsPicked(true);
			} else {
				item.setIsPicked(false);
			}
			items.add(item);
		}

		//设置适配器
		RegionPickerRecyclerAdapter adapter = new RegionPickerRecyclerAdapter(ctx, items);
		mRecyclerView.setAdapter(adapter);
		adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){

				@Override
				public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
					if(adapter.getData().get(position) instanceof RegionPickerItem){
						RegionPickerItem item = (RegionPickerItem) adapter.getData().get(position);
						if (pre != null) {
							pre.setSummary(item.getRegion().getResId());
						}
						if (dialog.isShowing()) {
							dialog.setOnDismissListener(null);
							dialog.dismiss();
						}
						preference.edit().putString(PreferenceKey.SETTING_REGIONS, item.getRegion().value()).commit();
						if (lis != null) {
							lis.onRegionSelected(item.getRegion());
						}
					}
				}
			});
		//添加到dialog
		dialog.setView(v);
		return this;
	}

	public RegionSelectDialog loadView() {
		loadView(null);
		return this;
	}

	public RegionSelectDialog setOnRegionSelectedListener(OnRegionSelectedListener lis) {
		this.lis = lis;
		return this;
	}

	public RegionSelectDialog setOnDismissListener(DialogInterface.OnDismissListener onDismiss) {
		dialog.setOnDismissListener(onDismiss);
		return this;
	}

	public RegionSelectDialog show() {
		if (dialog != null) {
			dialog.show();
		}
		return this;
	}
}
