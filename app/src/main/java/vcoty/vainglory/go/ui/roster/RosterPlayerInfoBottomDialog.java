package vcoty.vainglory.go.ui.roster;

import android.content.*;
import android.graphics.*;
import android.view.*;
import android.widget.*;
import com.jakewharton.rxbinding2.view.*;
import com.squareup.picasso.*;
import com.trello.rxlifecycle2.android.*;
import de.hdodenhof.circleimageview.*;
import io.reactivex.functions.*;
import java.util.concurrent.*;
import me.shaohui.bottomdialog.*;
import vcoty.vainglory.go.*;
import vcoty.vainglory.go.utils.*;

import vcoty.vainglory.go.R;
import vcoty.vainglory.go.base.*;
import vcoty.vainglory.go.ui.*;

public class RosterPlayerInfoBottomDialog extends BaseBottomDialog {

	private Context ctx;
	
	private String playerName;
	private String heroUrl;
	private String heroName;
	private int skillTier;
	private String playerGuild;
	
	private Intent mIntent;
	
	
	public RosterPlayerInfoBottomDialog(Context ctx,DataBuilder builder){
		this.playerName = builder.playerName;
		this.heroUrl = builder.heroUrl;
		this.heroName = builder.heroName;
		this.skillTier = builder.skillTier;
		this.playerGuild = builder.playerGuild;
		
		this.mIntent = builder.mIntent;
		
		this.ctx = ctx;
	}
	
	public RosterPlayerInfoBottomDialog(){}
	
	@Override
	public void bindView(View v) {
		Typeface mTfSkillTire = Typeface.createFromAsset(ctx.getAssets(), "SkillTires.ttf");
		
		TextView mTvPlayerName = v.findViewById(R.id.tv_view_bottom_dialog_roster_player_info_player_name);
		TextView mTvHeroName = v.findViewById(R.id.tv_view_bottom_dialog_roster_player_info_hero_name);
		TextView mTvSkillTier = v.findViewById(R.id.tv_view_bottom_dialog_roster_player_info_skill_tier);
		mTvSkillTier.setTypeface(mTfSkillTire);
		CircleImageView mImgHeroHead = v.findViewById(R.id.circle_iv_view_bottom_dialog_roster_player_info_head_img);
		Button mBtnToHomePage = v.findViewById(R.id.btn_view_bottom_dialog_roster_player_info_to_main_page);
		
		
		Picasso.get().load(heroUrl).into(mImgHeroHead);
		mTvHeroName.setText(heroName);
		mTvSkillTier.setText(SkillTierConverter.get().getTierNum(skillTier) + "");
		mTvSkillTier.setTextColor(ctx.getResources().getColor(SkillTierConverter.get().getTierTextColor(ctx,skillTier)));
		
		StringBuilder playerNameBuilder = new StringBuilder();
		if(!"".equals(playerGuild)){
			playerNameBuilder.append(playerGuild + "_");
		}
		playerNameBuilder.append(playerName);

		mTvPlayerName.setText(playerNameBuilder);
		
		RxView.clicks(mBtnToHomePage)
			.throttleFirst(600,TimeUnit.MILLISECONDS)
			.compose(((BaseActivity) this.getActivity()).<Object>bindUntilEvent(ActivityEvent.DESTROY))
			.subscribe(new Consumer<Object>(){

				@Override
				public void accept(Object p1) throws Exception {
					if(mIntent != null){
						startActivity(mIntent);
					}	
				}
			});
			
	}

	@Override
	public int getLayoutRes() {
		return R.layout.view_bottom_dialog_roster_player_info;
	}
	
	public static class DataBuilder{
		
		String playerName;
		String heroUrl;
		String heroName;
		String playerGuild;
		int skillTier;
		
		Intent mIntent;
		
		public DataBuilder setIntent(Intent mIntent){
			this.mIntent = mIntent;
			return this;
		}
		
		public DataBuilder setPlayerGuild(String playerGuild ){
			this.playerGuild = playerGuild;
			return this;
		};
		
		public DataBuilder setPlayerName(String playerName) {
			this.playerName = playerName;
			return this;
		}

		public DataBuilder setHeroUrl(String heroUrl) {
			this.heroUrl = heroUrl;
			return this;
		}

		public DataBuilder setHeroName(String heroName) {
			this.heroName = heroName;
			return this;
		}


		public DataBuilder setSkillTier(int skillTier) {
			if(skillTier != 0){
				this.skillTier = skillTier;
			}
			return this;
		}

		public RosterPlayerInfoBottomDialog build(Context ctx){
			return new RosterPlayerInfoBottomDialog(ctx,this);
		}
	}
}
