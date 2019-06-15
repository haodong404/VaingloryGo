package vcoty.vainglory.go.utils;

import android.content.*;
import android.text.*;
import com.androidnetworking.error.*;
import com.google.gson.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.model.error.*;

import android.text.ClipboardManager;

public class ANErrorConverter {
	
	public static ANErrorConverter get(){
		return ANErrorConverterHolder.mANErrorConverter;
	}
	
	public static class ANErrorConverterHolder{
		private static final ANErrorConverter mANErrorConverter = new ANErrorConverter();
	}
	
	public String convertNormal(Context ctx, ANError error) throws Exception{
		if(error != null){
			
			if(error.getErrorCode() == 404){
				return getResString(ctx,R.string.error_404_text);
			}else if(!NetTaskUtil.get().isNetworkConnected(ctx)){
				return getResString(ctx,R.string.error_network_broken);
			}
			
			ErrorResponse response = new Gson().fromJson(error.getErrorBody(),ErrorResponse.class);
			if(response != null && response.getErrors() != null && !response.getErrors().isEmpty()){
				if(response.getErrors().get(0).getTitle().equals("Unauthorized")){
					return getResString(ctx,R.string.error_authorization);
				}else{
					if(response.getErrors().get(0).getDetail() == null){
						return getResString(ctx,R.string.error_unknow) + response.getErrors().get(0).getTitle();
					}else{
						return getResString(ctx,R.string.error_unknow) + response.getErrors().get(0).getDetail();
					}
					
				}
			}else if("java.net.SocketTimeoutException: timeout".equals(error.getMessage())){
				return getResString(ctx,R.string.error_time_out);
			} else{
				return getResString(ctx,R.string.error_unknow) + error.getMessage();
			}
		}else{
			return getResString(ctx,R.string.error_unknow) + error + "";
		}
	}
	
	private String getResString(Context ctx,int id){
		return ctx.getResources().getString(id);
	}
}
