package vcoty.vainglory.go.utils;


import io.reactivex.Observable;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import vcoty.vainglory.go.enums.HttpApi;
import com.google.gson.Gson;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import vcoty.vainglory.go.enums.Region;
import vcoty.vainglory.go.enums.GameMode;
import vcoty.vainglory.go.bean.bmob.UpdatePost;
import vcoty.vainglory.go.bean.bmob.Condition;
import vcoty.vainglory.go.bean.bmob.UploadTableInfo;
import vcoty.vainglory.go.bean.bmob.UpdataHero;
import vcoty.vainglory.go.bean.bmob.UpdataOutfit;

/**
 * 用于网络访问和网络相关的工具
 */
public class NetTaskUtil {

	public static NetTaskUtil get() {
		return NetTaskUtilsHolder.mNetTaskUtils;
	}

	public static class NetTaskUtilsHolder {
		private static final NetTaskUtil mNetTaskUtils = new NetTaskUtil(); 
	}


	/*
	 * 根据玩家名字获取玩家信息
	 * GET
	 */
	public Observable<String> getPlayerByName(String playerName, Region region) {
		return Rx2AndroidNetworking.get(HttpApi.VG_DEV_BASE_URL.value() + "shards/{region}/players")
			.addHeaders("Authorization", HttpApi.VG_DEV_APP_KEY.value())
			.addHeaders("Accept", HttpApi.VG_DEV_ACCEPT.value())
			.addPathParameter("region", region.value())
			.addQueryParameter("filter[playerNames]", playerName)
			.build()
			.getStringObservable();
	}

	/*
	 * 根据玩家名字获取对局信息
	 * GET
	 */
	public Observable<String> getMatchByName(String name, int page, Region region, GameMode mode) {
		return Rx2AndroidNetworking.get(HttpApi.VG_DEV_BASE_URL.value() + "shards/{region}/matches")
			.addHeaders("Authorization", HttpApi.VG_DEV_APP_KEY.value())
			.addHeaders("Accept", HttpApi.VG_DEV_ACCEPT.value())
			.addPathParameter("region", region.value())
			.addQueryParameter("filter[gameMode]", mode.getContent())
			.addQueryParameter("sort", "-createdAt")
			.addQueryParameter("page[limit]", "10")
			.addQueryParameter("page[offset]", page + "")
			.addQueryParameter("filter[playerNames]", name)
			.build()
			.getStringObservable();
	}
	
	/**
	 * 从vgpro.gg获取player信息
	 * GET
	 */
	public Observable<String> getPlayerByName(String name){
		return Rx2AndroidNetworking.get(HttpApi.VGPRO_BASE_URL.value() + "player/{name}/stats")
			.addHeaders("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11")
			.addPathParameter("name",name)
			.addQueryParameter("gameMode","")
			.addQueryParameter("season","")
			.build()
			.getStringObservable();
	}
	
	/**
	 * 从vgpro.gg获取Telemetry信息
	 * GET
	 */
	public Observable<String> getTelemetryByVgpro(String matchId, Region region){
		return Rx2AndroidNetworking.get(HttpApi.VGPRO_BASE_URL.value() + "matches/{match}/{region}/telemetry")
			.addHeaders("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11")
			.addPathParameter("match",matchId)
			.addPathParameter("region",region.value())
			.build()
			.getStringObservable();
	}
	
	
	/**
	 * 从vainglory-go.tk获取player信息
	 * GET
	 */
	public Observable<String> getPlayerByNameVgo(String name){
		return Rx2AndroidNetworking.get(HttpApi.VGO_BASE_URL.value() + "vgpro.php?matchId=&&region=&&playerName={playerName}")
			.addPathParameter("playerName",name)
			.build()
			.getStringObservable();
	}
	
	/**
	 * 从vainglory-go.tk获取telemetry信息
	 * GET
	 */
	public Observable<String> getTelemetry(String matchId, Region region){
		return Rx2AndroidNetworking.get(HttpApi.VGO_BASE_URL.value() + "vgpro.php?matchId={matchId}&&region={region}&&playerName=")
			.addPathParameter("matchId",matchId)
			.addPathParameter("region", region.value())
			.build()
			.getStringObservable();
	}

	
	
	/**
	 * 从vgpro.gg获取Asset信息
	 * GET
	 */
	public Observable<String> getAsset(String id,Region region){
		return Rx2AndroidNetworking.get(HttpApi.VGPRO_BASE_URL.value() + "matches/{id}/{region}/telemetry")
			.addHeaders("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11")
			.addPathParameter("id",id)
			.addPathParameter("region",region.value())
			.build()
			.getStringObservable();
	}
	
	/**
	 * 测试
	 */
	public Observable<String> testVgo(){
		return Rx2AndroidNetworking.get(HttpApi.VGPRO_BASE_URL.value())
			.build()
			.getStringObservable();
	}
	
	public Observable<String> testApi(){
		return Rx2AndroidNetworking.get(HttpApi.VG_DEV_BASE_URL.value())
			.build()
			.getStringObservable();
	}
	
	
	/**
	 * 获取更新信息
	 * GET
	 */
	public Observable<String> getUpdata(int versionCode) {
		UpdatePost root = new UpdatePost();
		root.setVersionCode(new Condition(versionCode));
		String json = new Gson().toJson(root);
		return Rx2AndroidNetworking.get(HttpApi.BMOB_BASE_URL.value() + "versions")
			.addHeaders("X-Bmob-Application-Id", HttpApi.BMOB_APP_ID.value())
			.addHeaders("X-Bmob-REST-API-Key", HttpApi.BMOB_REST_API_KEY.value())
			.addHeaders("Content-Type", "application/json")
			.addQueryParameter("where", json)
			.build()
			.getStringObservable();
	}

	/**
	 * 获取数据表结构
	 * GET
	 */
	public Observable<String> getTables(String tableName) {

		return Rx2AndroidNetworking.get(HttpApi.BMOB_BASE_URL.value() + "table_info")
			.addHeaders("X-Bmob-Application-Id", HttpApi.BMOB_APP_ID.value())
			.addHeaders("X-Bmob-REST-API-Key", HttpApi.BMOB_REST_API_KEY.value())
			.addHeaders("Content-Type", "application/json")
			.addQueryParameter("where", new Gson().toJson(new UploadTableInfo(tableName)))
			.build()
			.getStringObservable();
	}

	/**
	 * 获取英雄头像更新
	 * GET
	 */
	public Observable<String> updataHeroImgs(int nowHero) {

		UpdataHero root = new UpdataHero();
		root.setHeroID(new Condition(nowHero));

		return Rx2AndroidNetworking.get(HttpApi.BMOB_BASE_URL.value() + "hero_head_imgs")
			.addHeaders("X-Bmob-Application-Id", HttpApi.BMOB_APP_ID.value())
			.addHeaders("X-Bmob-REST-API-Key", HttpApi.BMOB_REST_API_KEY.value())
			.addHeaders("Content-Type", "application/json")
			.addQueryParameter("where", new Gson().toJson(root))
			.build()
			.getStringObservable();
	}

	/**
	 * 获取英雄头像更新
	 * GET
	 */
	public Observable<String> updataOutfitIcon(int nowIcons) {

		UpdataOutfit root = new UpdataOutfit();
		root.setOutfitID(new Condition(nowIcons));

		return Rx2AndroidNetworking.get(HttpApi.BMOB_BASE_URL.value() + "outfit_icons")
			.addHeaders("X-Bmob-Application-Id", HttpApi.BMOB_APP_ID.value())
			.addHeaders("X-Bmob-REST-API-Key", HttpApi.BMOB_REST_API_KEY.value())
			.addHeaders("Content-Type", "application/json")
			.addQueryParameter("where", new Gson().toJson(root))
			.build()
			.getStringObservable();
	}


	/**
	 * 检测是否有网络连接
	 */
	public static boolean isNetworkConnected(Context context) { 
		if (context != null) { 
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context 
				.getSystemService(Context.CONNECTIVITY_SERVICE); 
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo(); 
			if (mNetworkInfo != null) { 
				return mNetworkInfo.isAvailable(); 
			} 
		} 
		return false; 
	}
}
