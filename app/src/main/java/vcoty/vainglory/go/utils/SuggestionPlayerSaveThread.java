package vcoty.vainglory.go.utils;

import org.litepal.crud.*;
import vcoty.vainglory.go.bean.db.*;
import java.util.*;

public class SuggestionPlayerSaveThread extends Thread {
	
	private String name;
	
	public SuggestionPlayerSaveThread(String name){
		this.name = name;
	}
	
	@Override
	public void run() {
		//数据库操作
		SuggestionPlayer player = new SuggestionPlayer();
		player.setName(name);
		player.setRegion("···");
		player.save();

		int suggestionCount = DataSupport.count(SuggestionPlayer.class);

		if(suggestionCount >= 2){
			List<SuggestionPlayer> players = DataSupport.where("name = ?",name).find(SuggestionPlayer.class);
			if(players != null && !players.isEmpty()){
				SuggestionPlayer suggestionPlayer = players.get(0).clone();
				players.get(0).delete();
				suggestionPlayer.save();
			}
		}
		
	}
}
