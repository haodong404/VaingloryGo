package vcoty.vainglory.go.bean.bmob;

import java.util.List;

public class UpdateRequestRoot {
	private List<UpdateRequest> results;

	public void setResults(List<UpdateRequest> results) {
		this.results = results;
	}

	public List<UpdateRequest> getResults() {
		return results;
	}
}
