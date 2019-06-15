package vcoty.vainglory.go.bean.bmob;

import com.google.gson.annotations.SerializedName;

public class Condition {
	@SerializedName("$gt")
	private int moreThan;

	public Condition(){}
	
	public Condition(int moreThan){
		this.moreThan = moreThan;
	}
	
	public void setMoreThan(int moreThan) {
		this.moreThan = moreThan;
	}

	public int getMoreThan() {
		return moreThan;
	}
}
