package com.jcs.magazine.bean;

import java.io.Serializable;

/**
 * 封面bean
 * author：Jics
 * 2017/7/31 13:37
 */
public class MgzCoverBean implements Serializable {

	/**
	 * vol : 53
	 * editorship : 季晨生
	 * subeditor : Jcs
	 * images : http://xxx.xxx.xxx/xxx
	 */
	private int id;
	private int vol;
	private String editorship;
	private String subeditor;
	private String images;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
		return "MgzCoverBean{" +
				"id=" + id +
				", vol=" + vol +
				", editorship='" + editorship + '\'' +
				", subeditor='" + subeditor + '\'' +
				", images='" + images + '\'' +
				'}';
	}
}
