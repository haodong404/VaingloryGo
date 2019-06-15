package vcoty.vainglory.go.mvp.presenter;

public interface Callback<T> {
	
	/**
	 * 成功
	 */
	void onSuccee(T data)
	
	/**
	 * 错误回调
	 */
	void onError(Throwable error)
	
	/**
	 * 结束回调
	 */
	void onComplete()
}
