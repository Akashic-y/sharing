package com.yn.entity;

import java.util.List;

import com.yn.common.entity.BaseEntity;

import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 文章
 * @author yn
 * @Date   2019年1月23日
 */
@Data
@Table(name = "me_article")
public class Article extends BaseEntity {

	public static final int Article_TOP = 1;

	public static final int Article_Common = 0;

	/** 评论数 */
	private Integer commentCounts;

	/**
     * 创建时间
     */
	private String createDate;
	
	/** 简介 */
	private String summary;
	
	/** 标题 */
	private String title;
	
	/** 浏览数量 */
	private Integer viewCounts;

	/** 作者 */
	@Transient
	private User author;

	/** 文章正文 */
	@Transient
	private ArticleBody body;
	
	/** 分类 */
	@Transient
	private Category category;

	/** 标签 */
	@Transient
	private List<Tag> tags;

	/** 评论 */
	@Transient
	private List<Comment> comments;

	/**
	 * 置顶
	 */
	private int weight = Article_Common;

}