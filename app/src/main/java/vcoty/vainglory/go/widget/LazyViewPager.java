package vcoty.vainglory.go.widget;

import android.support.v4.view.ViewPager;
import android.content.Context;
import android.util.AttributeSet;
import android.support.v4.view.PagerAdapter;
import vcoty.vainglory.go.base.LazyViewPagerAdapter;

public class LazyViewPager extends ViewPager {

	private static final float DEFAULT_OFFSET = 0.5f;

	private LazyViewPagerAdapter mLazyPagerAdapter;
	private float mInitLazyItemOffset = DEFAULT_OFFSET;

	public LazyViewPager(Context context) {
		super(context);
	}

	public LazyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

    /**
     * change the initLazyItemOffset
     * @param initLazyItemOffset set mInitLazyItemOffset if {@code 0 < initLazyItemOffset <= 1}
     */
	public void setInitLazyItemOffset(float initLazyItemOffset) {
		if (initLazyItemOffset > 0 && initLazyItemOffset <= 1) {
		    mInitLazyItemOffset = initLazyItemOffset;
        }
	}

	@Override
	public void setAdapter(PagerAdapter adapter) {
		super.setAdapter(adapter);
        mLazyPagerAdapter = adapter != null && adapter instanceof LazyViewPagerAdapter ? (LazyViewPagerAdapter) adapter : null;
	}

	@Override
	protected void onPageScrolled(int position, float offset, int offsetPixels) {
		if (mLazyPagerAdapter != null) {
			if (getCurrentItem() == position) {
				int lazyPosition = position + 1;
				if (offset >= mInitLazyItemOffset && mLazyPagerAdapter.isLazyItem(lazyPosition)) {
                    mLazyPagerAdapter.startUpdate(this);
                    mLazyPagerAdapter.addLazyItem(this, lazyPosition);
                    mLazyPagerAdapter.finishUpdate(this);
				}
			} else if (getCurrentItem() > position) {
				int lazyPosition = position;
				if (1 - offset >= mInitLazyItemOffset && mLazyPagerAdapter.isLazyItem(lazyPosition)) {
                    mLazyPagerAdapter.startUpdate(this);
                    mLazyPagerAdapter.addLazyItem(this, lazyPosition);
                    mLazyPagerAdapter.finishUpdate(this);
				}
			}
		}
		super.onPageScrolled(position, offset, offsetPixels);
	}
}

