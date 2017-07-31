package com.jcs.magazine.bean;

/**
 * 封面bean
 * author：Jics
 * 2017/7/31 13:37
 */
public class MagazineCoverBean {

	/**
	 * vol : 53
	 * editorship : 季晨生
	 * subeditor : Jcs
	 * images : http://xxx.xxx.xxx/xxx
	 */
	private int vol;
	private String editorship;
	private String subeditor;
	private String images;

	public int getVol() {
		return vol;
	}

	public void setVol(int vol) {
		this.vol = vol;
	}

	public String getEditorship() {
		return editorship;
	}

	public void setEditorship(String editorship) {
		this.editorship = editorship;
	}

	public String getSubeditor() {
		return subeditor;
	}

	public void setSubeditor(String subeditor) {
		this.subeditor = subeditor;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "MagazineCoverBean{" +
				"vol=" + vol +
				", editorship='" + editorship + '\'' +
				", subeditor='" + subeditor + '\'' +
				", images='" + images + '\'' +
				'}';
	}
}
