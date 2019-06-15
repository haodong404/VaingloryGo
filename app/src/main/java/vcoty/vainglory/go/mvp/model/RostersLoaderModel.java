package vcoty.vainglory.go.mvp.model;

import io.reactivex.*;
import io.reactivex.android.schedulers.*;
import io.reactivex.disposables.*;
import io.reactivex.schedulers.*;
import java.util.*;
import org.litepal.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.bean.*;
import vcoty.vainglory.go.bean.db.*;
import vcoty.vainglory.go.bean.item.*;
import vcoty.vainglory.go.enums.*;
import vcoty.vainglory.go.interfaces.*;
import vcoty.vainglory.go.model.participant.*;
import vcoty.vainglory.go.model.player.*;
import vcoty.vainglory.go.mvp.presenter.*;
import vcoty.vainglory.go.utils.*;

import io.reactivex.Observable;
import io.reactivex.Observer;
import vcoty.vainglory.go.R;

public class RostersLoaderModel implements IBindLifecycle<RostersLoaderModel> {

	public static class RostersLoaderModelHolder {
		private static final RostersLoaderModel mSearchModel = new RostersLoaderModel();
	}

	public static RostersLoaderModel get() {
		return RostersLoaderModelHolder.mSearchModel;
	}

	private BaseFragment fragment;
	private BaseActivity activity;
	
	public void loadOverviews(final RosterInformationEntity data, final Callback<List<RosterOverviewSection>> callback) {
		
		if(activity == null){
			callback.onError(new NullPointerException("Please bind lifecycle first (RostersLoaderModel.claas (#42))"));
			return;
		}
		
		Observable<List<RosterOverviewSection>> observable = Observable.create(new ObservableOnSubscribe<List<RosterOverviewSection>>(){

				@Override
				public void subscribe(ObservableEmitter<List<RosterOverviewSection>> p1) throws Exception {
					try {
						List<RosterOverviewSection> overviewData = new ArrayList<RosterOverviewSection>();
						for(int i = 0 ; i < 2 ; i++){
							if (data.getRosters().get(i).getStats().getSide().equals("right/red")) {
								loadData(overviewData,data,0);
							}else{
								loadData(overviewData,data,1);
							}
						}
						p1.onNext(overviewData);
					} catch (Throwable e) {
						p1.onError(e);
					}
					p1.onComplete();
				}
			});

		observable.subscribeOn(Schedulers.newThread())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Observer<List<RosterOverviewSection>>(){

				@Override
				public void onSubscribe(Disposable p1) {
					
				}

				@Override
				public void onNext(List<RosterOverviewSection> p1) {
					callback.onSuccee(p1);
				}

				@Override
				public void onError(Throwable p1) {
					callback.onError(p1);
				}

				@Override
				public void onComplete() {
					callback.onComplete();
				}
			});
	}

	private void loadData (List<RosterOverviewSection> list, RosterInformationEntity data,int side) throws Throwable{
		RosterOverviewSection section = new RosterOverviewSection(true,"hhhh");
		RosterOverviewItemHeader header = new RosterOverviewItemHeader();
		header.setGold(data.getRosters().get(side).getStats().getGold() + "");
		header.setIsWinRes(data.getRosters().get(side).isWon() ? R.string.match_item_victory : R.string.match_item_defeat);
		header.setKaren(data.getRosters().get(side).getStats().getKrakenCaptures() + "");
		header.setKills(data.getRosters().get(side).getStats().getHeroKills() + "");
		if(side == 0){
			header.setRedSide(true);
		}else{
			header.setRedSide(false);
		}
		header.setTurret(data.getRosters().get(side).getStats().getTurretKills() + "");
		
		section.setHeader(header);
		list.add(section);
		for (int i = 0 ; i < data.getRosters().get(side).getParticipants().size(); i++) {
			Participant mParticipant = data.getRosters().get(side).getParticipants().get(data.getRosters().get(side).getDocument()).get(i);
			RosterOverviewItem item = new RosterOverviewItem();
			item.setIsRedSide(side == 0 ? true : false);
			item.setIsMe(mParticipant.getPlayer().get(mParticipant.getDocument()).getName().equals(data.getMyselfName()));
			item.setFarm(mParticipant.getStats().getFarm() + "");
			item.setGold(mParticipant.getStats().getGold());
			item.setIsMvp(mParticipant.getPlayer().get(mParticipant.getDocument()).equals(data.getMvpPlayer()));
			item.setKda(mParticipant.getStats().getKda());
			item.setRankPoints(convertRankedPoints(mParticipant.getPlayer().get(mParticipant.getDocument()),data.getGameMode()));
			item.setSkillTier(SkillTierConverter.get().convertPoints(convertRankedPoints(mParticipant.getPlayer().get(mParticipant.getDocument()),data.getGameMode())));
			item.setKdaNum(mParticipant.getStats().getKills() + "/" + mParticipant.getStats().getDeaths() + "/" + mParticipant.getStats().getAssists());
			item.setPlayerName(mParticipant.getPlayer().get(mParticipant.getDocument()).getName());
			item.setGuild(mParticipant.getPlayer().get(mParticipant.getDocument()).getStats().getGuildTag());
			Hero hero = LitePal.where("nameEN = ?", ApiHeroNameAdaptation.adapt(mParticipant.getActor())).find(Hero.class).get(0);
			item.setUrl(hero.getUrl());
			item.setHeroName(HeroConverter.getInstance().getHeroNameByEn(activity,hero.getNameEN()));
			List<String> itemsStrings = mParticipant.getStats().getItems();
			List<RosterItemsItem> itemsList = new ArrayList<RosterItemsItem>();
			
			for(int j = 1 ; j < itemsStrings.size() ; j ++){
				RosterItemsItem itemSingle = new RosterItemsItem();
				List<Outfit> outfit = LitePal.where("name = ?",itemsStrings.get(j)).find(Outfit.class);
				if(!outfit.isEmpty()){
					itemSingle.setId(outfit.get(0).getId());
					itemSingle.setUrl(outfit.get(0).getUrl());
					itemsList.add(itemSingle);
				}
			}
			item.setItems(itemsList);
			list.add(new RosterOverviewSection(false,"").setItem(item));
		}
	}
	public int convertRankedPoints(Player player,GameMode gameMode ){
		if(activity != null && player != null && gameMode != null){
			int skillTier = 0;
			switch(activity.getPreference().getString(PreferenceKey.SETTING_RANKING_POINTS_SHOW,"auto")){
				case "auto":
					switch(gameMode){
						case RANKED:
							skillTier = (int)player.getStats().getRankPoints().getRanked();
							break;
						case CASUAL:
							skillTier = (int)player.getStats().getRankPoints().getRanked();
							break;	
						case _5V5_PVP_RANKEND:
							skillTier = (int)player.getStats().getRankPoints().getRanked_5v5();	
							break;
						case _5V5_PVP_CASUAL:
							skillTier = (int)player.getStats().getRankPoints().getRanked_5v5();
							break;
						case BLITZ_PVP_RANKED:
							skillTier = (int)player.getStats().getRankPoints().getBlitz();
							break;
						case CASUAL_ARAL:
							skillTier = (int)player.getStats().getRankPoints().getBlitz();
							break;
						default :
							skillTier = (int)player.getStats().getRankPoints().getRanked();
					}
					break;
				case "_33":
					skillTier = (int)player.getStats().getRankPoints().getRanked();
					break;
				case "_55":
					skillTier = (int)player.getStats().getRankPoints().getRanked_5v5();
					break;
				case "blitz":
					skillTier = (int)player.getStats().getRankPoints().getBlitz();
					break;
			}

			return skillTier;
		}else{
			return 0;
		}
	}
	

	@Override
	public RostersLoaderModel bindLifecycle(BaseActivity activity) {
		this.activity = activity;
		return this;
	}

	@Override
	public RostersLoaderModel bindLifecycle(BaseFragment fragment) {
		this.fragment = fragment;
		return this;
	}
}
