package com.yn.sharing.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 文章
 *
 * @author yn
 *         <p>
 *         2019年1月23日
 */
@Data
public class Article implements Serializable {

	private static final long serialVersionUID = 2415079192367330581L;

	public static final int Article_TOP = 1;

	public static final int Article_Common = 0;

	private Integer id;

	/* 评论数 */
	private Integer commentCounts;

	private String createDate;
	
	/* 简介 */
	private String summary;

	private String title;
	
	/* 浏览数量 */
	private Integer viewCounts;

	private User author;

	private ArticleBody body;
	
	/* 分类 */
	private Category category;

	/* 标签 */
	private List<Tag> tags;

	private List<Comment> comments;

	/**
	 * 置顶
	 */
	private int weight = Article_Common;

}