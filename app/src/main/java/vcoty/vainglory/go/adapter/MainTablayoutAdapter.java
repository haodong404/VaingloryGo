package vcoty.vainglory.go.adapter;

import vcoty.vainglory.go.base.LazyFragmentPagerAdapter;
import android.support.v4.app.Fragment;
import java.util.List;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

public class MainTablayoutAdapter extends LazyFragmentPagerAdapter {
	private List<Fragment> fragments;

	public MainTablayoutAdapter(FragmentManager fm,List<Fragment> fragments){
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	protected Fragment getItem(ViewGroup container, int position) {
		return fragments.get(position);
	}
}
