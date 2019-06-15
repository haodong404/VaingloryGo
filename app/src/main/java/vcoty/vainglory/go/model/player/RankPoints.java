package vcoty.vainglory.go.model.player;

public class RankPoints {
	private double blitz;
    private double ranked;
	private double ranked_5v5;

	public void setBlitz(double blitz) {
		this.blitz = blitz;
	}

	public double getBlitz() {
		return blitz == -1 ? 0 : blitz;
	}

	public void setRanked(double ranked) {
		this.ranked = ranked;
	}

	public double getRanked() {
		return ranked == -1 ? 0 : ranked;
	}

	public void setRanked_5v5(double ranked_5v5) {
		this.ranked_5v5 = ranked_5v5;
	}

	public double getRanked_5v5() {
		return ranked_5v5 == -1 ? 0 : ranked_5v5;
	}
}
