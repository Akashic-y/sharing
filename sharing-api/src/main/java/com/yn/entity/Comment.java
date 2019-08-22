package com.yn.entity;

import java.util.Date;
import java.util.List;
import com.yn.common.entity.BaseEntity;
import lombok.Data;

@Data
public class Comment extends BaseEntity{

    private String content;

    private User author;
    
    private Date createDate;

    /**
     * 类型 0 文章的评论 1 评论的评论 2 评论的回复 @
     */
    private Integer level;
    
    private Article article;
    
    private List<Comment> childrens;
    
    private Comment parent;
    
    private User toUser;

    private Integer articleId;

    private Integer authorId;

    private Integer parentId;

    private Integer toUid;

}