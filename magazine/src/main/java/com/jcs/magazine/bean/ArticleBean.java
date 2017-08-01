package com.jcs.magazine.bean;

import java.io.Serializable;

/**
 * 正文的HTML字符流
 * author：Jics
 * 2017/8/1 14:01
 */
public class ArticleBean implements Serializable {
	String content="";

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ArticleBean{" +
				"content='" + content + '\'' +
				'}';
	}
}
