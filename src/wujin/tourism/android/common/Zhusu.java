package wujin.tourism.android.common;

import java.io.Serializable;

public class Zhusu implements Serializable {
	private String imv_hotel;
	private String tv_hotelname;
	private String tv_juli;
	private String tv_address;
	private String tv_oldprice;
	private String tv_nowprice;
	private Float ratingBar;
	private String jianjie;
	private String jiaotong;
	private String tel;

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getJiaotong() {
		return jiaotong;
	}

	public void setJiaotong(String jiaotong) {
		this.jiaotong = jiaotong;
	}

	public String getJianjie() {
		return jianjie;
	}

	public void setJianjie(String jianjie) {
		this.jianjie = jianjie;
	}

	public String getImv_hotel() {
		return imv_hotel;
	}

	public void setImv_hotel(String imv_hotel) {
		this.imv_hotel = imv_hotel;
	}

	public String getTv_hotelname() {
		return tv_hotelname;
	}

	public void setTv_hotelname(String tv_hotelname) {
		this.tv_hotelname = tv_hotelname;
	}

	public String getTv_juli() {
		return tv_juli;
	}

	public void setTv_juli(String tv_juli) {
		this.tv_juli = tv_juli;
	}

	public String getTv_address() {
		return tv_address;
	}

	public void setTv_address(String tv_address) {
		this.tv_address = tv_address;
	}

	public String getTv_oldprice() {
		return tv_oldprice;
	}

	public void setTv_oldprice(String tv_oldprice) {
		this.tv_oldprice = tv_oldprice;
	}

	public String getTv_nowprice() {
		return tv_nowprice;
	}

	public void setTv_nowprice(String tv_nowprice) {
		this.tv_nowprice = tv_nowprice;
	}

	public Float getRatingBar() {
		return ratingBar;
	}

	public void setRatingBar(Float ratingBar) {
		this.ratingBar = ratingBar;
	}

}
