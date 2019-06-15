package vcoty.vainglory.go.utils;

import com.squareup.moshi.*;
import moe.banana.jsonapi2.*;
import vcoty.vainglory.go.model.asset.*;
import vcoty.vainglory.go.model.match.*;
import vcoty.vainglory.go.model.participant.*;
import vcoty.vainglory.go.model.player.*;
import vcoty.vainglory.go.model.roster.*;

public class MoshiInstance {
	
	public static MoshiInstance get(){
		return MoshiInstanceHolder.mMoshiInstance;
	}
	
	public static class MoshiInstanceHolder{
		private static final MoshiInstance mMoshiInstance = new MoshiInstance();
	}
	
	public Moshi getStandard(){
		return new Moshi.Builder().build();
	}
	
	public Moshi getVg(){
		JsonAdapter.Factory factory = ResourceAdapterFactory.builder()
			.add(Match.class)
			.add(Roster.class)
			.add(Player.class)
			.add(Participant.class)
			.add(Asset.class)
			.build();

		Moshi moshi = new Moshi.Builder().add(factory).build();
		return moshi;
	}
}
