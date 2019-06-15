package vcoty.vainglory.go.bean.item;

import java.util.*;
import com.razerdp.widget.animatedpieview.data.*;

public class RosterStatsPlayerItem {
	private String url;
	private boolean isRed;
	
	private int damageTotal;
	private int damage;
	
	private int dealtTotal;
	private int dealt;
	
	private int healedTotal;
	private int healt;
	private boolean isMe;

	public void setIsMe(boolean isMe) {
		this.isMe = isMe;
	}

	public boolean isMe() {
		return isMe;
	}

	public void setIsRed(boolean isRed) {
		this.isRed = isRed;
	}

	public boolean isRed() {
		return isRed;
	}


	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setDamageTotal(int damageTotal) {
		this.damageTotal = damageTotal;
	}

	public int getDamageTotal() {
		return damageTotal;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}

	public void setDealtTotal(int dealtTotal) {
		this.dealtTotal = dealtTotal;
	}

	public int getDealtTotal() {
		return dealtTotal;
	}

	public void setDealt(int dealt) {
		this.dealt = dealt;
	}

	public int getDealt() {
		return dealt;
	}

	public void setHealedTotal(int healedTotal) {
		this.healedTotal = healedTotal;
	}

	public int getHealedTotal() {
		return healedTotal;
	}

	public void setHealt(int healt) {
		this.healt = healt;
	}

	public int getHealt() {
		return healt;
	}
}
