package vcoty.vainglory.go.base;

public interface BaseView {
	
	/**
	 * 显示加载中..
	 */
	void showLoading()

	/**
	 * 隐藏加载中...
	 */
	void hideLoading()

	/**
	 * 失败
	 */
	void showError(Throwable error)
}
