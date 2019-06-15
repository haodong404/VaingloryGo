package vcoty.vainglory.go.bean.event;

import java.util.*;
import vcoty.vainglory.go.bean.item.*;

public class PlayedHeroesEvent {
	private List<ProfilePlayedHeroIitem> event;

	public PlayedHeroesEvent(List<ProfilePlayedHeroIitem> event) {
		this.event = event;
	}

	public PlayedHeroesEvent(){}
	
	public void setEvent(List<ProfilePlayedHeroIitem> event) {
		this.event = event;
	}

	public List<ProfilePlayedHeroIitem> getEvent() {
		return event;
	}
}
