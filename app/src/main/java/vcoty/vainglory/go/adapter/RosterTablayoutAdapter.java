package vcoty.vainglory.go.adapter;
import android.support.v4.app.*;
import android.view.*;
import java.util.*;
import vcoty.vainglory.go.base.*;

public class RosterTablayoutAdapter extends LazyFragmentPagerAdapter {
	private List<Fragment> fragments;

	private String[] titles;
	
	public RosterTablayoutAdapter(FragmentManager fm,String[] titles ,List<Fragment> fragments){
		super(fm);
		this.fragments = fragments;
		this.titles = titles;
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}
	
	
	@Override
	protected Fragment getItem(ViewGroup container, int position) {
		return fragments.get(position);
	}
}
