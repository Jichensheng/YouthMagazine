package com.jcs.magazine.bean;

import java.io.Serializable;

/**
 * 评论
 * author：Jics
 * 2017/8/24 17:13
 */
public class CommentBean implements Serializable {
	//评论id
	private String cid;
	//帖子id
	private String mid;
	//发帖人id
	private String uid;
	//评论文字
	private String excerpt;
	//评论时间
	private String date;
	//赞数
	private String praise;
	//评论人昵称
	private String nick;
	//评论人头像地址
	private String head;
	//引用评论
	private CommentBean quote;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPraise() {
		return praise;
	}

	public void setPraise(String praise) {
		this.praise = praise;
	}


	public CommentBean getQuote() {
		return quote;
	}

	public void setQuote(CommentBean quote) {
		this.quote = quote;
	}

	@Override
	public String toString() {
		return "CommentBean{" +
				"mid='" + mid + '\'' +
				", uid='" + uid + '\'' +
				", excerpt='" + excerpt + '\'' +
				", date='" + date + '\'' +
				", praise='" + praise + '\'' +
				", nick='" + nick + '\'' +
				", head='" + head + '\'' +
				", quote=" + quote +
				'}';
	}
}
