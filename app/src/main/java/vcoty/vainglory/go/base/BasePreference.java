package vcoty.vainglory.go.base;

import android.os.Bundle;
import vcoty.vainglory.go.interfaces.IToasts;
import vcoty.vainglory.go.interfaces.IResources;
import android.widget.Toast;
import android.graphics.drawable.Drawable;
import com.trello.rxlifecycle2.components.RxPreferenceFragment;
import android.view.View;
import android.preference.ListPreference;
import android.preference.EditTextPreference;

public abstract class BasePreference extends RxPreferenceFragment implements IToasts,IResources {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(getXmlId());
		initial();
	}
	
	@Override
	public String getResString(int resId) {
		return getContext().getResources().getString(resId);
	}

	@Override
	public int getResColor(int resId) {
		return getContext().getResources().getColor(resId);
	}

	@Override
	public Drawable getResDrawable(int resId) {
		return getContext().getResources().getDrawable(resId);
	}

	@Override
	public float getResDimension(int resId) {
		return getContext().getResources().getDimension(resId);
	}

	@Override
	public void shortToast(String content) {
		Toast.makeText(getContext(),content,Toast.LENGTH_SHORT).show();
	}

	@Override
	public void longToast(String content) {
		Toast.makeText(getContext(),content,Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 将subtitle的描述与已选择的项一致
	 */
	protected void entry2Summary(ListPreference mListPreference){
		mListPreference.setSummary(mListPreference.getEntry());

	}
	
	/**
	 * 获取Preference的view
	 */
	public View getPreferenceView(){
		return getView();
	}
	
	/**
	 * 初始化
	 */
	protected abstract void initial()
	
	/**
	 * 获取xml文件的id
	 */
	protected abstract int getXmlId()
}
