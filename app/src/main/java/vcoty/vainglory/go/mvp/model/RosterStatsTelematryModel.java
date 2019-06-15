package vcoty.vainglory.go.mvp.model;

import com.google.gson.*;
import com.razerdp.widget.animatedpieview.data.*;
import com.trello.rxlifecycle2.android.*;
import io.reactivex.*;
import io.reactivex.android.schedulers.*;
import io.reactivex.disposables.*;
import io.reactivex.functions.*;
import io.reactivex.schedulers.*;
import java.util.*;
import org.litepal.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.bean.db.*;
import vcoty.vainglory.go.bean.item.*;
import vcoty.vainglory.go.enums.*;
import vcoty.vainglory.go.interfaces.*;
import vcoty.vainglory.go.model.vgpro.telemetry.*;
import vcoty.vainglory.go.mvp.presenter.*;
import vcoty.vainglory.go.utils.*;

import io.reactivex.Observer;
import vcoty.vainglory.go.R;

public class RosterStatsTelematryModel implements IBindLifecycle<RosterStatsTelematryModel> {

	private BaseActivity activity;
	private BaseFragment fragment;

	private List<Integer> themeColors ;
	public static class RosterStatsTelematryModelHolder {
		private static final RosterStatsTelematryModel mRosterStatsTelematryModel = new RosterStatsTelematryModel();
	}

	public static RosterStatsTelematryModel get() {
		return RosterStatsTelematryModelHolder.mRosterStatsTelematryModel;
	}

	public void getStats(String matchId, final Map<Boolean,String> myself, Region region, final Callback<List<RosterStatsRootItem>> callback) {	
		if (fragment == null) {
			callback.onError(new NullPointerException("Please bind lifecycle first (RosterStatsTelematryModel.class : #23)"));
			return;
		}
		
		themeColors = new ArrayList<Integer>(10);
		Integer[] colorsArray = {R.color.MaterialAmber,R.color.MaterialLightGreen,R.color.MaterialBlueGrey,R.color.MaterialOrange,R.color.MaterialTeal,R.color.MaterialPink,R.color.MaterialYellow,R.color.MaterialPurple,R.color.MaterialCyan,R.color.MaterialLime};
		for(int i = 0 ; i < colorsArray.length ; i ++){
			themeColors.add(colorsArray[i]);
		}
		
		NetTaskUtil.get().getTelemetryByVgpro(matchId, region)
			.subscribeOn(Schedulers.io())
			.compose(fragment.<String>bindUntilEvent(FragmentEvent.DESTROY))
			.map(new io.reactivex.functions.Function<String,List<RosterStatsRootItem>>(){

				@Override
				public List<RosterStatsRootItem> apply(String p1) throws Exception {
					Gson gson = new Gson();
					Telemetry telemetry = gson.fromJson(p1, Telemetry.class);

					List<RosterStatsRootItem> rootItems = new ArrayList<RosterStatsRootItem>(2);
					rootItems.add(loadData(telemetry.getFacts().getRed(), myself, true));
					rootItems.add(loadData(telemetry.getFacts().getBlue(), myself, false));	
					
					return rootItems;
				}
			})
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Observer<List<RosterStatsRootItem>>(){

				@Override
				public void onSubscribe(Disposable p1) {

				}

				@Override
				public void onNext(List<RosterStatsRootItem> p1) {
					callback.onSuccee(p1);
					callback.onComplete();
				}

				@Override
				public void onError(Throwable p1) {
					callback.onError(p1);
					callback.onComplete();
				}

				@Override
				public void onComplete() {
					
				}
			});
	}

	private RosterStatsRootItem loadData(Map<String, Actor> side,Map<Boolean,String> myself,boolean isRed) {
		if (!side.isEmpty()) {
			side.remove("KindredSocialPingsManifest");
			RosterStatsRootItem item = new RosterStatsRootItem();
			item.setTitleRes(R.string.roster_stats_enemy_side);
			
			//填充扇形统计图
			
			/**
			 * 在vgpro的api中，伤害是getDealt() [不是getDamage()]
			 * 承伤是getTaken()
			 * 治愈是getHealt()
			 */
			int totalDamage = 0;
			int totalDealt = 0;
			int totalHealt = 0;
			List<SimplePieInfo> pieInfos = new ArrayList<SimplePieInfo>();
			Iterator<Map.Entry<String, Actor>> iter = side.entrySet().iterator();
			while(iter.hasNext()){
				SimplePieInfo pieInfo = new SimplePieInfo();
				
				pieInfo.setColor(fragment.getResColor(themeColors.get(0)));
				themeColors.remove(0);
				
				Map.Entry<String, Actor> map = iter.next();
				pieInfo.setDesc(HeroConverter.getInstance().getHeroNameByEn(fragment.getActivity(),map.getKey()));
				pieInfo.setValue(map.getValue().getDealt());
				pieInfos.add(pieInfo);
				
				totalDamage += map.getValue().getDealt();
				totalDealt += map.getValue().getTaken();
				totalHealt += map.getValue().getHealed();
			}
			item.setPieInfos(pieInfos);
			
			//填充每一个英雄的具体数据
			List<RosterStatsPlayerItem> playerItems = new ArrayList<RosterStatsPlayerItem>();
			
			Iterator<Map.Entry<String, Actor>> iter2 = side.entrySet().iterator();
			while(iter2.hasNext()){
				Map.Entry<String,Actor> map = iter2.next();
				RosterStatsPlayerItem playerItem = new RosterStatsPlayerItem();
				playerItem.setDamage(map.getValue().getDealt());
				playerItem.setDamageTotal(totalDamage);
				playerItem.setDealt(map.getValue().getTaken());
				playerItem.setDealtTotal(totalDealt);
				playerItem.setHealt(map.getValue().getHealed());
				playerItem.setHealedTotal(totalHealt);
				playerItem.setIsRed(isRed);
				if(map.getKey().equals(myself.get(isRed))){
					item.setTitleRes(R.string.roster_stats_our_side);
					playerItem.setIsMe(true);
				}else{
					playerItem.setIsMe(false);
				}
				
				playerItem.setUrl(LitePal.where("nameEN = ?",ApiHeroNameAdaptation.adapt(map.getKey())).find(Hero.class).get(0).getUrl());
				if(!map.getKey().equals("Sanfeng")){
					playerItems.add(playerItem);
				}
			}
			item.setTotal(totalDamage);
			item.setItems(playerItems);
			
			return item;

		} else {
			return new RosterStatsRootItem();
		}
	}

	@Override
	public RosterStatsTelematryModel bindLifecycle(BaseActivity activity) {
		this.activity = activity;
		return this;
	}

	@Override
	public RosterStatsTelematryModel bindLifecycle(BaseFragment fragment) {
		this.fragment = fragment;
		return this;
	}


}
