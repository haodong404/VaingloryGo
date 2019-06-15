package vcoty.vainglory.go.mvp.model;

import android.content.*;
import com.trello.rxlifecycle2.android.*;
import com.trello.rxlifecycle2.components.support.*;
import io.reactivex.*;
import io.reactivex.android.schedulers.*;
import io.reactivex.disposables.*;
import io.reactivex.schedulers.*;
import java.util.*;
import moe.banana.jsonapi2.*;
import org.litepal.*;
import org.litepal.crud.*;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.bean.*;
import vcoty.vainglory.go.bean.db.*;
import vcoty.vainglory.go.bean.item.*;
import vcoty.vainglory.go.enums.*;
import vcoty.vainglory.go.interfaces.*;
import vcoty.vainglory.go.model.match.*;
import vcoty.vainglory.go.model.participant.*;
import vcoty.vainglory.go.model.roster.*;
import vcoty.vainglory.go.mvp.presenter.*;
import vcoty.vainglory.go.utils.*;

import io.reactivex.Observer;
import com.google.gson.*;
import vcoty.vainglory.go.model.vgpro.*;
import android.util.*;
import vcoty.vainglory.go.model.player.*;
import vcoty.vainglory.go.*;

public class SearchModel implements IBindLifecycle<SearchModel> {

	public static class SearchModelHolder {
		private static final SearchModel mSearchModel = new SearchModel();
	}

	public static SearchModel get() {
		return SearchModelHolder.mSearchModel;
	}

	private RxFragment fragment;
	private RxAppCompatActivity activity;

	private List<Participant> mParticipants;
	
	
	/**
	 * 查询对局列表
	 */
	public void queryMatch(final String name, final Region region, GameMode gameMode, int page, final Callback<List<MatchsListItem>> callBack) {
		if (fragment == null) {
			return ;
		}
		
		NetTaskUtil.get().getMatchByName(name, page, region, gameMode)
			.subscribeOn(Schedulers.io())
			.compose(fragment.<String>bindUntilEvent(FragmentEvent.DESTROY))
			.map(new io.reactivex.functions.Function<String,List<MatchsListItem>>(){

				@Override
				public List<MatchsListItem> apply(String p1) throws Exception {
					
					ArrayDocument<Match> doc = MoshiInstance.get().getVg().adapter(Document.class).fromJson(p1).asArrayDocument();
					
					ArrayList<MatchsListItem> items = new ArrayList<MatchsListItem>();
					
					for(int i = 0 ; i < doc.size() ; i ++ ){
						MatchsListItem item = new MatchsListItem();
						item.setDate(DateConverter.convert(fragment.getActivity(),doc.get(i).getCreatedAt()));
						item.setGameMode(GameMode.parse((doc.get(i).getGameMode())));
						item.setDirection(DateConverter.directionConvert(doc.get(i).getDuration()));
						
						List<Roster> rosters = doc.get(i).getRosters().get(doc.get(i).getDocument());
						
						mParticipants = new ArrayList<Participant>();
						for(int j = 0 ; j < rosters.size() ; j ++){
							for(int k = 0 ; k < rosters.get(j).getParticipants().size() ; k ++){
								mParticipants.add(rosters.get(j).getParticipants().get(rosters.get(j).getDocument()).get(k));
								sortList(mParticipants);
							}
						}
						
						for(int j = 0 ; j < rosters.size(); j ++){
							List<Participant> participants = rosters.get(j).getParticipants().get(rosters.get(j).getDocument());
							for(int k = 0 ; k < participants.size() ; k++){
								if(participants.get(k).getPlayer().get(participants.get(k).getDocument()).getName().equals(name)){
									item.setHeadImgUrl(LitePal.where("nameEN = ?",ApiHeroNameAdaptation.adapt(participants.get(k).getActor())).find(Hero.class).get(0).getUrl());
									item.setIsMvp(mParticipants.get(0).getPlayer().get(participants.get(0).getDocument()).getName().equals(name));	
									item.setMvpPlayer(mParticipants.get(0).getPlayer().get(mParticipants.get(0).getDocument()));
									item.setIsWon(rosters.get(j).isWon());
									Map<Boolean,String> myself = new HashMap<Boolean,String>();
									myself.put(rosters.get(j).getStats().getSide().equals("right/red"),participants.get(k).getActor());
									item.setMyself(myself);
									item.setId(doc.get(i).getId());
									item.setSkillTier(participants.get(k).getPlayer().get(participants.get(k).getDocument()).getStats().getSkillTier());
									item.setKda(participants.get(k).getStats().getKills() + "/" + participants.get(k).getStats().getDeaths() + "/" + participants.get(k).getStats().getAssists());
									
									//存储公会标签(仍有bug)
									Player player = participants.get(k).getPlayer().get(participants.get(k).getDocument());
									if(player.getName().equals(name)){
										fragment.getActivity().getSharedPreferences(PreferenceKey.GUILD_PREFERENCE,fragment.getActivity().MODE_PRIVATE).edit().putString(PreferenceKey.GUILD,player.getStats().getGuildTag()).commit();
									}
								}	
							}
							
						}
						
						item.setRosters(rosters);
						items.add(item);
					}
					
					return items;
				}
			})
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new io.reactivex.Observer<List<MatchsListItem>>(){

				@Override
				public void onSubscribe(Disposable p1) {

				}

				@Override
				public void onNext(List<MatchsListItem> result) {
					callBack.onSuccee(result);
				}

				@Override
				public void onError(Throwable p1) {
					callBack.onError(p1);
				}

				@Override
				public void onComplete() {
					callBack.onComplete();
					//更改建议列表
					new Thread(){
						@Override
						public void run(){
							ContentValues value = new ContentValues();
							value.put("region", region.value());
							DataSupport.updateAll(SuggestionPlayer.class, value, "name = ?", name);
							
						}
					}.start();
				}
			});		
	}
	
	// 从vgpro.gg中获取player
	public void queryPlayerFromVgpro(String name,final Callback<ProfileInformationEntity> callback){
		NetTaskUtil.get().getPlayerByName(name)
			.subscribeOn(Schedulers.io())
			.compose(fragment.<String>bindUntilEvent(FragmentEvent.DESTROY))
			.map(new io.reactivex.functions.Function<String,ProfileInformationEntity>(){

				@Override
				public ProfileInformationEntity apply(String p1) throws Exception {
					Gson gson = new Gson();
					VgproPlayer player = gson.fromJson(p1,VgproPlayer.class);
					
					if(player == null){
						return new ProfileInformationEntity();
					}		
					//对heroes进行排序
					Collections.sort(player.getStats().getHeroes(), new Comparator<VgproHero>(){

							@Override
							public int compare(VgproHero p1, VgproHero p2) {
								if(p1.getGames() >= p2.getGames()){
									return -1;
								}else {
									return 1;
								}
							}
						});
					
					ProfileInformationEntity infoEntity = new ProfileInformationEntity();
					infoEntity.setAvgAssists (player.getStats().getAvgAssists() + "");
					infoEntity.setAvgDeaths(player.getStats().getAvgDeaths() + "");
					infoEntity.setAvgKills(player.getStats().getAvgKills() + "");
					infoEntity.setFields(player.getStats().getGames());
					infoEntity.setHeadImageUrl(LitePal.where("nameEN = ?",ApiHeroNameAdaptation.adapt(player.getStats().getHeroes().get(0).getName())).find(Hero.class).get(0).getUrl());
					infoEntity.setKp(player.getStats().getKp());
					infoEntity.setKda(player.getStats().getKda());
					infoEntity.setName(player.getName());
					infoEntity.setGuild(fragment.getActivity().getSharedPreferences(PreferenceKey.GUILD_PREFERENCE,Context.MODE_PRIVATE).getString(PreferenceKey.GUILD,""));
					infoEntity.setRegion(Region.parse(player.getRegion()));
					infoEntity.setWinrate(player.getStats().getWinRate());
					infoEntity.setWins(player.getStats().getWins());
					
					infoEntity.setRedGames(player.getStats().getRedGames());
					infoEntity.setRedWinRate(player.getStats().getRedWinRate());
					infoEntity.setRedWins(player.getStats().getRedWins());

					infoEntity.setBlueGames(player.getStats().getBlueGames());
					infoEntity.setBlueWinRate(player.getStats().getBlueWinRate());
					infoEntity.setBlueWins(player.getStats().getBlueWins());
					
					List<ProfileSkillTireCardItem> skillTireItems = new ArrayList<ProfileSkillTireCardItem>(3);
					GameMode[] modes = {GameMode.RANKED,GameMode._5V5_PVP_RANKEND,GameMode.BLITZ_PVP_RANKED};
					int[] rankPoints = {(int)player.getRankVst(), (int) player.getRank5v5Vst(),(int)player.getBlitzVst()};
					for(int i = 0 ; i < 3 ; i ++){
						ProfileSkillTireCardItem item = new ProfileSkillTireCardItem();
						int rankPoint = SkillTierConverter.get().convertPoints(rankPoints[i]);
						item.setGameMode(modes[i]);
						if(i == 0){
							item.setSkillTier(SkillTierConverter.get().getTierNum(player.getTier()));
							item.setTierColor(SkillTierConverter.get().getTierColor(fragment.getActivity(),player.getTier()));
						}else{
							item.setTierColor(SkillTierConverter.get().getTierColor(fragment.getActivity(),rankPoint));
							item.setSkillTier(SkillTierConverter.get().getTierNum(rankPoint));
						}
						item.setRankPoints(rankPoints[i]);
						skillTireItems.add(item);
					}
					
					infoEntity.setSkillTireItems(skillTireItems);
					
					List<ProfileRoleItem> roleItems = new ArrayList<ProfileRoleItem>(3);
					for(int i = 0 ; i < player.getStats().getRoles().size() ; i ++){
						ProfileRoleItem item = new ProfileRoleItem();
						VgproRole role = player.getStats().getRoles().get(i);
						item.setAssists(role.getAvgAssists() + "");
						item.setDeaths(role.getAvgDeaths() + "");
						item.setKills(role.getAvgKills() + "");
						item.setFields(role.getGames() + "");
						item.setKda("KDA:" + role.getKda());
						item.setRole(Role.parse(role.getName()));
						item.setWinrate(role.getWinRate());
						roleItems.add(item);
					}
					
					infoEntity.setRoleItems(roleItems);
					
					List<ProfilePlayedHeroIitem> playedHeroItems = new ArrayList<ProfilePlayedHeroIitem>(60);
					for(int i = 0 ; i < player.getStats().getHeroes().size() ; i++){
						VgproHero hero = player.getStats().getHeroes().get(i);
						ProfilePlayedHeroIitem item = new ProfilePlayedHeroIitem();
						item.setAssists(hero.getAvgAssists());
						item.setDeaths(hero.getAvgDeaths());
						item.setKda(hero.getKda() + "");
						item.setKills(hero.getAvgKills());
						item.setName(HeroConverter.getInstance().getHeroNameByEn(fragment.getActivity(),hero.getName()));
						item.setPlayeds(hero.getGames());
						item.setUrl(LitePal.where("nameEN = ?",ApiHeroNameAdaptation.adapt(hero.getName())).find(Hero.class).get(0).getUrl());
						item.setWinrate(hero.getWinRate());
						item.setWins(hero.getWins());
						playedHeroItems.add(item);
					}
					
					infoEntity.setPlayedHeroItems(playedHeroItems);
					
					return infoEntity;
				}
			})
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Observer<ProfileInformationEntity>(){

				@Override
				public void onSubscribe(Disposable p1) {
					
				}

				@Override
				public void onNext(ProfileInformationEntity p1) {
					if(p1 == null){
						callback.onError(new NullPointerException());
					}else{			
						callback.onSuccee(p1);
					}
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

	//计算出mvp
	public void sortList(List<Participant> mParticipantEntity) {

		Collections.sort(mParticipantEntity, new Comparator<Participant>(){

				@Override
				public int compare(Participant p1, Participant p2) {
					float p1Kda = p1.getStats().getKda();
					float p2Kda = p2.getStats().getKda();
					if (p1Kda > p2Kda) {
						return -1;
					} else if (p1Kda == p2Kda) {
						if (p1.getStats().getKills() > p2.getStats().getKills()) {
							return -1;
						} else if (p1.getStats().getKills() == p2.getStats().getKills()) {
							if (p1.getStats().getDeaths() < p2.getStats().getDeaths()) {
								return -1;
							} else if (p1.getStats().getDeaths() == p2.getStats().getDeaths()) {
								if (p1.getStats().getGold() > p2.getStats().getGold()) {
									return -1;
								} else {
									if (p1.getStats().getFarm() > p2.getStats().getFarm()) {
										return -1;
									} else {
										return 1;
									}
								}
							} else {
								return 1;
							}
						} else {
							return 1;
						}
					} else {
						return 1;
					}
				}
			});
	}
	
	@Override
	public SearchModel bindLifecycle(BaseActivity activity) {
		this.activity = activity;
		return this;
	}

	@Override
	public SearchModel bindLifecycle(BaseFragment fragment) {
		this.fragment = fragment;
		return this;
	}
}
