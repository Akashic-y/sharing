package com.yn.sharing.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
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

}