package com.jcs.magazine.bean;

/**
 * 轮播图
 */
public class BannerItem {
	private String image;
	private String title;
	private String link;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * 必须fanhuititle
	 * @return
	 */
	@Override
	public String toString() {
		return getTitle();
	}
}