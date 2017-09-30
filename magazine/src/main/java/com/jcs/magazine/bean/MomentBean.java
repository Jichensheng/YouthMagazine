package com.jcs.magazine.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 广场说说bean
 * author：Jics
 * 2017/8/24 16:41
 */
public class MomentBean implements Serializable {
	//帖子id,用于查询此贴的评论列表
	private String mid;
	//发帖人id
	private String uid;
	//昵称
	private String nick;
	//头像地址
	private String head;
	//最新的三个赞的用户id
	private List<UserBean> praiser;
	//帖子文字
	private String excerpt;
	//帖子图片
	private List<String> images;
	//发帖时间
	private String date;
	//赞数
	private int praise;
	//评论数
	private int comment;

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

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public List<UserBean> getPraiser() {
		return praiser;
	}

	public void setPraiser(List<UserBean> praiser) {
		this.praiser = praiser;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getPraise() {
		return praise;
	}

	public void setPraise(int praise) {
		this.praise = praise;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "MomentBean{" +
				"mid='" + mid + '\'' +
				", uid='" + uid + '\'' +
				", nick='" + nick + '\'' +
				", head='" + head + '\'' +
				", praiser=" + praiser +
				", excerpt='" + excerpt + '\'' +
				", images=" + images +
				", date='" + date + '\'' +
				", praise=" + praise +
				", comment=" + comment +
				'}';
	}

}
