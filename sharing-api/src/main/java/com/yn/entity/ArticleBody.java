package com.yn.entity;

import com.yn.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "me_article_body")
/**
 * 文章正文
 */
public class ArticleBody extends BaseEntity{
	
    private String content;

    private String contentHtml;

}