package com.jcs.magazine.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 广场说说bean
 * author：Jics
 * 2017/8/24 16:41
 */
//TODO 评论的图地址、文字内容、用户id、时间、点赞数、评论数、分享数实体
public class MomentBean implements Serializable {
	//帖子id,用于查询此贴的评论列表
	private String mid;
	//发帖人id
	private String uid;
	//最新的三个赞的用户id
	private List<String> praiser;
	//帖子文字
	private String excerpt;
	//帖子图片
	private List<String> images;
	//发帖时间
	private String date;
	//赞数
	private String praise;
	//评论数
	private String comment;

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

	public List<String> getPraiser() {
		return praiser;
	}

	public void setPraiser(List<String> praiser) {
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

	public String getPraise() {
		return praise;
	}

	public void setPraise(String praise) {
		this.praise = praise;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "MomentBean{" +
				"mid='" + mid + '\'' +
				", uid='" + uid + '\'' +
				", praiser=" + praiser +
				", excerpt='" + excerpt + '\'' +
				", images=" + images +
				", date='" + date + '\'' +
				", praise='" + praise + '\'' +
				", comment='" + comment + '\'' +
				'}';
	}
}
