package com.jcs.magazine.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 广场说说bean
 * author：Jics
 * 2017/8/24 16:41
 */
public class MomentBeanRefactor implements Serializable {
	//帖子id,用于查询此贴的评论列表
	private int mid;
	//发帖人id
	private UserBean postman;
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

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public UserBean getPostman() {
		return postman;
	}

	public void setPostman(UserBean postman) {
		this.postman = postman;
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
		return "MomentBeanRefactor{" +
				"mid='" + mid + '\'' +
				", postman=" + postman +
				", praiser=" + praiser +
				", excerpt='" + excerpt + '\'' +
				", images=" + images +
				", date='" + date + '\'' +
				", praise=" + praise +
				", comment=" + comment +
				'}';
	}
}
