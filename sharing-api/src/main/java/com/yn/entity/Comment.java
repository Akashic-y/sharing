package com.yn.entity;

import java.util.Date;
import java.util.List;
import com.yn.common.entity.BaseEntity;
import lombok.Data;

@Data
public class Comment extends BaseEntity{

	private Integer id;

    private String content;

    private User author;
    
    private Date createDate;

    /**
     * 类型 0 文章的评论 1 评论的评论 2 评论的回复 @
     */
    private String level;
    
    private Article article;
    
    private List<Comment> childrens;
    
    private Comment parent;
    
    private User toUser;

    private Integer articleId;

    private Long authorId;

    private Integer parentId;

    private Long toUid;

}