package wujin.tourism.android.tpc;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

@Table(name = "Wzglbean")
public class Wzglbean {
	@Id(column = "Id")
	private String id;
	private String album;
	private String intro;
	private String name;
	private String imagelist;

	public String getImagelist() {
		return imagelist;
	}

	public void setImagelist(String imagelist) {
		this.imagelist = imagelist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
