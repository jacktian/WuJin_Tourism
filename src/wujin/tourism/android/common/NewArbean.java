package wujin.tourism.android.common;

public class NewArbean {
	private String nameString;
	private double latitude;
	private double longitude;

	public NewArbean(String nameString, double latitude, double longitude) {
		this.nameString = nameString;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getNameString() {
		return nameString;
	}

	public void setNameString(String nameString) {
		this.nameString = nameString;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "{nameString:" + nameString + ", latitude:" + latitude + ", longitude:" + longitude + "}";
	}
	
	
}
