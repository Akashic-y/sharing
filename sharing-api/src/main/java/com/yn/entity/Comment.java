package com.yn.entity;

import java.util.Date;
import java.util.List;
import com.yn.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 评论
 */
@Data
@Table(name = "me_comment")
public class Comment extends BaseEntity{

    private String content;

    @Transient
    private User author;
    
    private Date createDate;

    /**
     * 类型 0 文章的评论 1 评论的评论 2 评论的回复 @
     */
    private Integer level;

    @Transient
    private Article article;

    @Transient
    private List<Comment> childrens;

    @Transient
    private Comment parent;

    @Transient
    private User toUser;

    private Integer articleId;

    private Integer authorId;

    private Integer parentId;

    private Integer toUid;

}