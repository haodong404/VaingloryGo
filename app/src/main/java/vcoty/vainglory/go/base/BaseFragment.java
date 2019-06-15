package vcoty.vainglory.go.base;

import com.trello.rxlifecycle2.components.support.RxFragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import vcoty.vainglory.go.interfaces.IToasts;
import vcoty.vainglory.go.interfaces.IResources;
import android.widget.Toast;
import android.graphics.drawable.Drawable;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public abstract class BaseFragment extends RxFragment implements IToasts,IResources, LazyFragmentPagerAdapter.Laziable{

	private View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(getLayoutResId(),null);
		initial(rootView);
		return rootView;
	}
	
	@Override
	public String getResString(int resId) {
		return getActivity().getResources().getString(resId);
	}

	@Override
	public int getResColor(int resId) {
		return getActivity().getResources().getColor(resId);
	}

	@Override
	public Drawable getResDrawable(int resId) {
		return getActivity().getResources().getDrawable(resId);
	}

	@Override
	public float getResDimension(int resId) {
		return getActivity().getResources().getDimension(resId);
	}

	@Override
	public void shortToast(String content) {
		Toast.makeText(getActivity(),content,Toast.LENGTH_SHORT).show();
	}

	@Override
	public void longToast(String content) {
		Toast.makeText(getActivity(),content,Toast.LENGTH_LONG).show();
	}
	
	// 获取设置的preference //
	public SharedPreferences getPreference(){
		return PreferenceManager.getDefaultSharedPreferences(getActivity());
	}
	
	/**
	 * 初始化
	 */
	protected abstract void initial(View view)
	
	/**
	 * 获取资源id
	 */
	protected abstract int getLayoutResId()
	
}
