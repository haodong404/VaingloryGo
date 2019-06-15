package vcoty.vainglory.go.model.error;

import java.util.*;

public class ErrorResponse {
	
	private List<Error> errors;

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	public List<Error> getErrors() {
		return errors;
	}
	
	public static class Error {
		private String title;
		private String detail;

		public void setTitle(String title) {
			this.title = title;
		}

		public String getTitle() {
			return title;
		}

		public void setDetail(String detail) {
			this.detail = detail;
		}

		public String getDetail() {
			return detail;
		}
	}
}
