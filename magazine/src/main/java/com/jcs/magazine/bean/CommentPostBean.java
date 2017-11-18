package com.jcs.magazine.bean;

/**
 * author：Jics
 * 2017/11/17 17:49
 */
public class CommentPostBean {

	/**
	 * type : 0
	 * talkId : 2
	 * excerpt : 故事凄美，声音好听
	 * uid : 1
	 * quoteId : 4
	 */

	private String type;
	private int talkId;
	private String excerpt;
	private int uid;
	private int quoteId;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTalkId() {
		return talkId;
	}

	public void setTalkId(int talkId) {
		this.talkId = talkId;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(int quoteId) {
		this.quoteId = quoteId;
	}
}
