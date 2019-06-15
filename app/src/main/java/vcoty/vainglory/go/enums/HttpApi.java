package vcoty.vainglory.go.enums;

/**
 * 用于网络访问的相关key与url
 */
public enum HttpApi {
	
	// 虚荣官方API授权 //
	VG_DEV_BASE_URL("https://api.dc01.gamelockerapp.com/"),
	VG_DEV_APP_KEY("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjNzM2YjM4MC02MGYyLTAxMzYtMDc0MS0wYTU4NjQ2MGI5MjkiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTMwNjI0NDU4LCJwdWIiOiJzZW1jIiwidGl0bGUiOiJ2YWluZ2xvcnkiLCJhcHAiOiJ2Y290eTIzMy0xNjMtY29tLXMtYXBwIiwic2NvcGUiOiJjb21tdW5pdHkiLCJsaW1pdCI6MjAwfQ.pHfzFp4S4iPFbg7Sn3KrOmZROOwLcAKvCDImU_hWR_E"),
	VG_DEV_ACCEPT("application/vnd.api+json"),
	GITHUB_HEAD_IMG("https://raw.githubusercontent.com/VcotyQin/VaingloryGoData/master/hero_head_imgs/"),
	
	
	// 支付宝code //
	ALIPAY_MY_CODE("https://qr.alipay.com/tsx05259qoxareyvdhnlwf5"),
	
	// Bmob授权 //
	BMOB_BASE_URL("https://api.bmob.cn/1/classes/"),
	BMOB_APP_ID("05c4bad17dbe52cd19ccd8241862d541"),
	BMOB_REST_API_KEY("e6452ade1b3d17888fc4701bb2a7445d"),
	
	// vgpro.gg //
	VGPRO_BASE_URL("http://api.vgpro.gg/"),
	VGO_BASE_URL("http://vainglory-go.tk/html/"),
	
	// Bugly授权 //
	BUGLY_APP_ID("e4c7905829");
	
	private String content;
	
	public HttpApi(String content){
		this.content = content;
	}

	// 返回每一个枚举的值 //
	public String value(){
		return content;
	}
	
	@Override
	public String toString() {
		return  "name : " + name() + "\nvalue : " + content;
	}
	
}
