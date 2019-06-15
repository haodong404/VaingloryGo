package vcoty.vainglory.go.interfaces;

import vcoty.vainglory.go.base.BaseActivity;
import vcoty.vainglory.go.base.BaseFragment;

public interface IBindLifecycle<T> {
	
	/**
	 * 绑定生命周期
	 */
	T bindLifecycle(BaseActivity activity)
	
	T bindLifecycle(BaseFragment fragment)
}
