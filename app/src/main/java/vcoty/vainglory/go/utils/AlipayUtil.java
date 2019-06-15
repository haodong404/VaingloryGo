package vcoty.vainglory.go.utils;

import android.content.pm.PackageManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.app.Activity;
import java.net.URLEncoder;
import android.content.Intent;
import android.net.Uri;
import android.content.ActivityNotFoundException;

public class AlipayUtil {
	
	// 支付宝包名
    private static final String ALIPAY_PACKAGE_NAME = "com.eg.android.AlipayGphone";
	
	
	public static AlipayUtil get(){
		return AlipayUtilHolder.mAlipayUtil; 
	}
	
	public static class AlipayUtilHolder{
		private static final AlipayUtil mAlipayUtil = new AlipayUtil();
	}
	
	//检查支付宝是否安装
    public boolean hasInstalledAlipayClient(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(ALIPAY_PACKAGE_NAME, 0);
            return info != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    //调用者调用此方法跳转到支付宝
    public boolean startAlipayClient(Activity activity, String urlCode) {
        return startIntentUrl(activity, doFormUri(urlCode));
    }

	//格式化urlCode
    private String doFormUri(String urlCode) {
        try {
            urlCode = URLEncoder.encode(urlCode, "utf-8");
        } catch (Exception e) {
        }
        final String alipayqr = "alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=" + urlCode;
        String openUri = alipayqr + "%3F_s%3Dweb-other&_t=" + System.currentTimeMillis();
        return openUri;
    }

    //跳转到支付宝
    private boolean startIntentUrl(Activity activity, String intentFullUrl) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(intentFullUrl));
            activity.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
