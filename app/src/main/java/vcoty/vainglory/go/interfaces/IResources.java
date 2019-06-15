package vcoty.vainglory.go.interfaces;

import android.graphics.drawable.Drawable;

public interface IResources {

	/**
	 * 获取资源字符串
	 */
	String getResString(int resId)

	/**
	 * 获取资源颜色
	 */
	int getResColor(int resId)

	/**
	 * 获取资源drawable
	 */
	Drawable getResDrawable(int resId)

	/**
	 * 获取资源尺寸
	 */
	float getResDimension(int resId)
	
}
