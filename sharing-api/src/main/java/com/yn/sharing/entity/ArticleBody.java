package com.yn.sharing.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ArticleBody implements Serializable{
    /** 
	 */
	private static final long serialVersionUID = 3719281236365828904L;

	private Integer id;
	
    private String content;

    private String contentHtml;

}