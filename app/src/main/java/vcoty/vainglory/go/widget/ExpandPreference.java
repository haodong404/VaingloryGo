package vcoty.vainglory.go.widget;

import android.view.View;
import android.preference.Preference;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.Gravity;

public class ExpandPreference extends Preference {

	private View view;
	private Context context;
	private int gravity;

	public ExpandPreference(Context context) {
		super(context);
		this.context = context;
	}

	public ExpandPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public ExpandPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	@Override
	protected void onBindView(View view) {
		this.view = view;
		view.setOnTouchListener(new View.OnTouchListener(){

				@Override
				public boolean onTouch(View p1, MotionEvent p2) {
					WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE); 
					float windowX = wm.getDefaultDisplay().getWidth();
					if(p2.getX() >= (windowX /2)){
						gravity = Gravity.RIGHT;
					}else{
						gravity = Gravity.LEFT;
					}
					return false;
				}
			});
		super.onBindView(view);
	}	 

	public View getItemView(){
		return view;
	}

	public int getGravity(){
		return gravity;
	}

	@Override
	protected void onClick() {
		super.onClick();
	}


}

