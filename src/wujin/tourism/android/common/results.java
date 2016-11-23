package wujin.tourism.android.common;

import java.io.Serializable;

public class results implements Serializable {
	private String name;
	private location location;
	private String address;
	private String street_id;
	private String telephone;
	private String uid;
	private detail_info detail_info;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public location getLocation() {
		return location;
	}

	public void setLocation(location location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStreet_id() {
		return street_id;
	}

	public void setStreet_id(String street_id) {
		this.street_id = street_id;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public detail_info getDetail_info() {
		return detail_info;
	}

	public void setDetail_info(detail_info detail_info) {
		this.detail_info = detail_info;
	}

}
