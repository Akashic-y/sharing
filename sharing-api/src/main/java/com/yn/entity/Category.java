package com.yn.entity;

import com.yn.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;

/**
 * 类别
 */
@Data
@Table(name = "me_article_tag")
public class Category extends BaseEntity{

	/** 图片路径 */
	private String avatar;

	/** 名称 */
	private String categoryname;

	/** 描述 */
	private String description;

}