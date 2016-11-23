package wujin.tourism.android.common;

public class User {
	double a;
	double b;
	String nameString;
	String stateString;

	public User(double a, double b, String nameString, String stateString) {
		this.a = a;
		this.b = b;
		this.nameString = nameString;
		this.stateString = stateString;
	}

	public User() {
	}

	public double geta() {
		return a;
	}

	public void seta(double a) {
		this.a = a;
	}

	public double getb() {
		return b;
	}

	public void setb(double b) {
		this.b = b;
	}

	public String getnameString() {
		return nameString;
	}

	public void setName(String nameString) {
		this.nameString = nameString;
	}

	public String getstateString() {
		return stateString;
	}

	public void setstateString(String stateString) {
		this.stateString = stateString;
	}
}
