package com.jcs.magazine.bean;

import com.jcs.magazine.network.YzuClientDemo;

import java.io.Serializable;

/**
 * author：Jics
 * 2017/9/3 12:57
 */
public class TalkBean  implements Serializable {
	/**
	 * title : 白日梦
	 * articleId : 530101
	 * author : 正南七白
	 * dj : jay
	 * url : http: //xxx.xxx.xxx/xxx.mp3
	 * image : http: //xxx.xxx.xxx/xxx.jpg
	 * excerpt : 他语气坦荡，当然也有点低落。她觉得这才是他真正说话的样子。
	 * createTime : 2017-9-3
	 * praise : 999
	 */

	private String title;
	private int articleId;
	private String author;
	private String dj;//电台主播
	private String url;//电台音频地址
	private String image;
	private String excerpt;
	private String createTime;
	private int praise;

	public TalkBean(String title, int articleId, String author, String dj, String url, String image, String excerpt, String createTime, int praise) {
		this.title = title;
		this.articleId = articleId;
		this.author = author;
		this.dj = dj;
		this.url = url;
		this.image = image;
		this.excerpt = excerpt;
		this.createTime = createTime;
		this.praise = praise;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDj() {
		return dj;
	}

	public void setDj(String dj) {
		this.dj = dj;
	}

	public String getUrl() {
		return YzuClientDemo.RESOURCE_AUDIO_HOST+url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return YzuClientDemo.RESOURCE_COVER_HOST+image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getPraise() {
		return praise;
	}

	public void setPraise(int praise) {
		this.praise = praise;
	}
}
