package com.jcs.magazine.bean;

import java.io.Serializable;

/**
 * 评论
 * author：Jics
 * 2017/8/24 17:13
 */
public class CommentBean implements Serializable {
	//评论id
	private int id;
	//帖子id
	private int talkId;
	//发帖人id
	private UserBean user;
	//评论文字
	private String excerpt;
	//赞数
	private int praise;
	private String createDate;
	//引用的评论对象
	private CommentBean quote;
	//评论类型 0：talk里边的三种 1:杂志文章 2：广场
	private String type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTalkId() {
		return talkId;
	}

	public void setTalkId(int talkId) {
		this.talkId = talkId;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}


	public int getPraise() {
		return praise;
	}

	public void setPraise(int praise) {
		this.praise = praise;
	}

	public CommentBean getQuote() {
		return quote;
	}

	public void setQuote(CommentBean quote) {
		this.quote = quote;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "CommentBean{" +
				"id=" + id +
				", talkId=" + talkId +
				", user=" + user +
				", excerpt='" + excerpt + '\'' +
				", praise='" + praise + '\'' +
				", quote=" + quote!=null?quote.toString():"null" +
				", type='" + type + '\'' +
				'}';
	}
}
