package vcoty.vainglory.go.utils;

import io.reactivex.*;
import io.reactivex.subjects.*;
import java.util.*;
import java.util.concurrent.*;

import io.reactivex.Observable;

public class RxBus {
	private static volatile RxBus mDefaultInstance;
	private final Subject<Object> mBus;

	private final Map<Class<?>, Object> mStickyEventMap;

	public RxBus() {
		mBus = PublishSubject.create().toSerialized();
		mStickyEventMap = new ConcurrentHashMap<>();
	}

	public static RxBus getDefault() {
		if (mDefaultInstance == null) {
			synchronized (RxBus.class) {
				if (mDefaultInstance == null) {
					mDefaultInstance = new RxBus();
				}
			}
		}
		return mDefaultInstance;
	}
	/**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }

	public void postSticky(Object event) {
		synchronized (mStickyEventMap) {
			mStickyEventMap.put(event.getClass(), event);
		}
		post(event);
	}

	public void post(Object event) {
        mBus.onNext(event);
    }

	
	public <T> Observable<T> toObservableSticky(final Class<T> eventType) {
		synchronized (mStickyEventMap) {
			Observable<T> observable = mBus.ofType(eventType);
			final Object event = mStickyEventMap.get(eventType);

			if (event != null) {
				return observable.mergeWith(Observable.just(eventType.cast(event)));
			} else {
				return observable;
			}
		}
	}
}
