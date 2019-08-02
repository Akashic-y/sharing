package com.yn.sharing.entity;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable{
	private static final long serialVersionUID = 3654822021920142401L;

	private Integer id;

    private String content;

    private Date createDate;

    private String level;

    private Integer articleId;

    private Long authorId;

    private Integer parentId;

    private Long toUid;

    public Comment(Integer id, String content, Date createDate, String level, Integer articleId, Long authorId, Integer parentId, Long toUid) {
        this.id = id;
        this.content = content;
        this.createDate = createDate;
        this.level = level;
        this.articleId = articleId;
        this.authorId = authorId;
        this.parentId = parentId;
        this.toUid = toUid;
    }

    public Comment() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Long getToUid() {
        return toUid;
    }

    public void setToUid(Long toUid) {
        this.toUid = toUid;
    }
}