package com.yn.sharing.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 文章
 *
 * @author yn
 *         <p>
 *         2019年1月23日
 */
public class Article implements Serializable {

	private static final long serialVersionUID = 2415079192367330581L;

	public static final int Article_TOP = 1;

	public static final int Article_Common = 0;
	private Integer id;
	/*评论数*/
	private Integer commentCounts;

	private String createDate;
	/*简介*/
	private String summary;

	private String title;
	/*浏览数量*/
	private Integer viewCounts;

	private User author;

	private ArticleBody body;
	/*分类*/
	private Category category;

	/*标签*/
	private List<Tag> tags;

	private List<Comment> comments;

	/**
	 * 置顶
	 */
	private int weight = Article_Common;

	public Integer getCommentCounts() {
		return commentCounts;
	}

	public void setCommentCounts(Integer commentCounts) {
		this.commentCounts = commentCounts;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary == null ? null : summary.trim();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public Integer getViewCounts() {
		return viewCounts;
	}

	public void setViewCounts(Integer viewCounts) {
		this.viewCounts = viewCounts;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public User getAuthor() {
		return author;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public ArticleBody getBody() {
		return body;
	}

	public void setBody(ArticleBody body) {
		this.body = body;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "Article [commentCounts=" + commentCounts + ", createDate=" + createDate + ", summary=" + summary
				+ ", title=" + title + ", viewCounts=" + viewCounts + ", author=" + author + ", body=" + body
				+ ", category=" + category + ", tags=" + tags + ", comments=" + comments + ", weight=" + weight + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}