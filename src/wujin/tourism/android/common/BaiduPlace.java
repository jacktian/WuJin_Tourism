package wujin.tourism.android.common;

import java.util.List;

public class BaiduPlace {
	private String status;
	private String message;
	private String total;

	private List<results> results;

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<results> getResults() {
		return results;
	}

	public void setResults(List<results> results) {
		this.results = results;
	}

}
