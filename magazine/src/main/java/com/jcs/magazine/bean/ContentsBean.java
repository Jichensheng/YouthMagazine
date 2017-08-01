package com.jcs.magazine.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 全本杂志目录Bean
 * author：Jics
 * 2017/8/1 11:00
 */
public class ContentsBean implements Serializable {

	/**
	 * id : 01
	 * name : 扬城小韵
	 * articles : [{"title":"白日梦","id":530101,"author":"正南七白","image":"http: //xxx.xxx.xxx/xxx","excerpt":"他语气坦荡，当然也有点低落。她觉得这才是他真正说话的样子。","praise":999},{"title":"白日梦","id":530102,"author":"正南七白","image":"http: //xxx.xxx.xxx/xxx","excerpt":"他语气坦荡，当然也有点低落。她觉得这才是他真正说话的样子。","praise":999},{"title":"白日梦","id":530102,"author":"正南七白","image":"http: //xxx.xxx.xxx/xxx","excerpt":"他语气坦荡，当然也有点低落。她觉得这才是他真正说话的样子。","praise":999},{"title":"白日梦","id":530102,"author":"正南七白","image":"http: //xxx.xxx.xxx/xxx","excerpt":"他语气坦荡，当然也有点低落。她觉得这才是他真正说话的样子。","praise":999}]
	 */

	private String id;
	private String name;
	private List<ArticlesBean> articles;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ArticlesBean> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticlesBean> articles) {
		this.articles = articles;
	}

	public class ArticlesBean  implements Serializable {
		/**
		 * title : 白日梦
		 * id : 530101
		 * author : 正南七白
		 * image : http: //xxx.xxx.xxx/xxx
		 * excerpt : 他语气坦荡，当然也有点低落。她觉得这才是他真正说话的样子。
		 * praise : 999
		 */

		private String title;
		private int id;
		private String author;
		private String image;
		private String excerpt;
		private int praise;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getImage() {
			return image;
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

		public int getPraise() {
			return praise;
		}

		public void setPraise(int praise) {
			this.praise = praise;
		}

		@Override
		public String toString() {
			return "ArticlesBean{" +
					"title='" + title + '\'' +
					", id=" + id +
					", author='" + author + '\'' +
					", image='" + image + '\'' +
					", excerpt='" + excerpt + '\'' +
					", praise=" + praise +
					'}';
		}
	}

	@Override
	public String toString() {
		return "ContentsBean{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", articles=" + articles.toString() +
				'}';
	}
}
