package wujin.tourism.android.tpc;

public class Timestreambean {
	private String id;
	private String textcontent;
	private String time;
	private String imageurl;
	private int image;

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public String getTextcontent() {
		return textcontent;
	}

	public void setTextcontent(String textcontent) {
		this.textcontent = textcontent;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
}
