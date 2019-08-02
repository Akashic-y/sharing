package com.yn.sharing.entity;

import java.io.Serializable;

public class ArticleBody implements Serializable{
    /** 
	 */
	private static final long serialVersionUID = 3719281236365828904L;

	private Integer id;
	
    private String content;

    private String contentHtml;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml == null ? null : contentHtml.trim();
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}