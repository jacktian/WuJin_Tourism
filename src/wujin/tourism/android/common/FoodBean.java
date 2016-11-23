package wujin.tourism.android.common;

import java.io.Serializable;

public class FoodBean implements Serializable {
	private String name;
	private String oldprice;
	private String nowprice;
	private Float rating;
	private String tel;
	private String address;
	private String jianjie;
	private String juli;
	private String jiaotong;

	public String getJiaotong() {
		return jiaotong;
	}

	public void setJiaotong(String jiaotong) {
		this.jiaotong = jiaotong;
	}

	public String getJuli() {
		return juli;
	}

	public void setJuli(String juli) {
		this.juli = juli;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOldprice() {
		return oldprice;
	}

	public void setOldprice(String oldprice) {
		this.oldprice = oldprice;
	}

	public String getNowprice() {
		return nowprice;
	}

	public void setNowprice(String nowprice) {
		this.nowprice = nowprice;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getJianjie() {
		return jianjie;
	}

	public void setJianjie(String jianjie) {
		this.jianjie = jianjie;
	}

}
