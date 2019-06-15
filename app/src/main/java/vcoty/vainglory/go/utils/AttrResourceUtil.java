package vcoty.vainglory.go.utils;

import android.util.TypedValue;
import android.content.Context;
import android.app.*;

public class AttrResourceUtil {
	public static int getAttrResId(Context ctx,int attrsId){
		TypedValue typedValue = new TypedValue();
		ctx.getTheme().resolveAttribute(attrsId, typedValue, true);
		return typedValue.resourceId;
	}
	
	public static int getAttrData(Context ctx,int attrsId){
		TypedValue typedValue = new TypedValue();
		ctx.getTheme().resolveAttribute(attrsId, typedValue, true);
		return typedValue.data;
	}
}
