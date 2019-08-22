package com.yn.entity;

import com.yn.common.entity.BaseEntity;
import lombok.Data;

@Data
public class ArticleBody extends BaseEntity{
	
    private String content;

    private String contentHtml;

}