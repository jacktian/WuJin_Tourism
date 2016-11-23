package wujin.tourism.android.common;

import java.io.Serializable;

public class detail_info implements Serializable {
	private String distance;
	private String type;
	private String tag;
	private String detail_url;
	private String price;
	private String overall_rating;
	private String service_rating;
	private String facility_rating;
	private String hygiene_rating;
	private String image_num;
	private String comment_num;
	private String favorite_num;
	private String checkin_num;

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDetail_url() {
		return detail_url;
	}

	public void setDetail_url(String detail_url) {
		this.detail_url = detail_url;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getOverall_rating() {
		return overall_rating;
	}

	public void setOverall_rating(String overall_rating) {
		this.overall_rating = overall_rating;
	}

	public String getService_rating() {
		return service_rating;
	}

	public void setService_rating(String service_rating) {
		this.service_rating = service_rating;
	}

	public String getFacility_rating() {
		return facility_rating;
	}

	public void setFacility_rating(String facility_rating) {
		this.facility_rating = facility_rating;
	}

	public String getHygiene_rating() {
		return hygiene_rating;
	}

	public void setHygiene_rating(String hygiene_rating) {
		this.hygiene_rating = hygiene_rating;
	}

	public String getImage_num() {
		return image_num;
	}

	public void setImage_num(String image_num) {
		this.image_num = image_num;
	}

	public String getComment_num() {
		return comment_num;
	}

	public void setComment_num(String comment_num) {
		this.comment_num = comment_num;
	}

	public String getFavorite_num() {
		return favorite_num;
	}

	public void setFavorite_num(String favorite_num) {
		this.favorite_num = favorite_num;
	}

	public String getCheckin_num() {
		return checkin_num;
	}

	public void setCheckin_num(String checkin_num) {
		this.checkin_num = checkin_num;
	}

}
