package vcoty.vainglory.go.mvp.model;

import com.google.gson.*;
import io.reactivex.*;
import io.reactivex.android.schedulers.*;
import io.reactivex.disposables.*;
import io.reactivex.functions.*;
import io.reactivex.schedulers.*;
import java.util.*;
import vcoty.vainglory.go.bean.bmob.*;
import vcoty.vainglory.go.bean.db.*;
import vcoty.vainglory.go.mvp.presenter.*;
import vcoty.vainglory.go.utils.*;

import io.reactivex.Observable;
import io.reactivex.Observer;
import org.litepal.*;

public class PullResourceModel {

	
	private List<Disposable> mDisposables = new ArrayList<Disposable>();
	
	private static final String HERO_TABLE_NAME = "hero_head_imgs";
	private static final String OUTFIT_TABLE_NAME = "outfit_icons";
	
	public static PullResourceModel get() {
		return PullResourceModelHolder.mPullResourceModel;
	}

	public static class PullResourceModelHolder {
		private static final PullResourceModel mPullResourceModel = new PullResourceModel();
	}

	public void updataLocalResource(final UpdataResourceCallBack callBack) {
		Observable.zip(NetTaskUtil.get().getTables(HERO_TABLE_NAME), NetTaskUtil.get().getTables(OUTFIT_TABLE_NAME), new BiFunction<String,String,List<TableResult>>(){

				@Override
				public List<TableResult> apply(String p1, String p2) throws Exception {
					List<TableResult> results = new ArrayList<TableResult>(2);
					TableRoot heroRoot = new Gson().fromJson(p1, TableRoot.class);
					TableRoot outfitRoot = new Gson().fromJson(p2,TableRoot.class);
					
					results.add(heroRoot.getResults().get(0));
					results.add(outfitRoot.getResults().get(0));
					return results;
				}
			})
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Observer<List<TableResult>>(){

				@Override
				public void onSubscribe(Disposable p1) {
					mDisposables.add(p1);
				}

				@Override
				public void onNext(List<TableResult> p1) {
					int heroDbSize = LitePal.count(Hero.class);
					int outfitDbSize = LitePal.count(Outfit.class);
					
					for(int i = 0 ; i < 2 ; i ++){
						if(HERO_TABLE_NAME.equals(p1.get(i).getTableName())){
							if(p1.get(i).getTableSize() > heroDbSize){
								updataResource(heroDbSize,0,callBack);
							}
						}else{
							if(p1.get(i).getTableSize() > outfitDbSize){
								updataResource(0,outfitDbSize,callBack);
							}
						}
					}
				}

				@Override
				public void onError(Throwable p1) {
					callBack.onError(p1);
				}

				@Override
				public void onComplete() {
					//callBack.onComplete();
				}
			});
	}


	private void updataResource(final int heroSize,final int outfitSize,final UpdataResourceCallBack callback){
		Observable.zip(NetTaskUtil.get().updataHeroImgs(heroSize), NetTaskUtil.get().updataOutfitIcon(outfitSize), new BiFunction<String,String,Boolean>(){

				@Override
				public Boolean apply(String heroJson, String outfitJson) throws Exception {
					HeadImgResult result = new Gson().fromJson(heroJson, HeadImgResult.class);
					for (int i = 0;i < result.getResults().size() ; i++) {
						Hero hero = new Hero();
						hero.setId(result.getResults().get(i).getHeroID());
						hero.setNameCN(result.getResults().get(i).getHeroNameCN());
						hero.setNameEN(result.getResults().get(i).getHeroNameEN());
						hero.setNameTitle(result.getResults().get(i).getHeroTitle());
						hero.setFullName(hero.getNameTitle() + hero.getNameCN());
						hero.setUrl(result.getResults().get(i).getHeadImg().getUrl());
						hero.save();
					}
					
					List<OutfitImg> outfits = new Gson().fromJson(outfitJson, OutfitImgResult.class).getResults();
					for (int i = 0 ; i < outfits.size() ; i++) {
						Outfit outfit = new Outfit();
						outfit.setId(outfits.get(i).getOutfitID());
						outfit.setName(outfits.get(i).getApiName());
						outfit.setUrl(outfits.get(i).getOutfitIcon().getUrl());
						outfit.save();
					}
					
					return (heroSize != 0 || outfitSize != 0 );
				}
			}).subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Observer<Boolean>(){

				@Override
				public void onSubscribe(Disposable p1) {
					mDisposables.add(p1);
				}

				@Override
				public void onNext(Boolean p1) {
					if(p1){
						callback.onSuccee(p1);
					}else{
						callback.noNewResource();
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
	
	
	public void despose(){
		if(mDisposables == null || mDisposables.isEmpty()){
			return;
		}
		for(Disposable d : mDisposables){
			d.dispose();
		}
	}
}
