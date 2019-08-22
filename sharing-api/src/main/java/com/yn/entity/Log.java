package com.yn.entity;

import java.util.Date;
import com.yn.common.entity.BaseEntity;
import lombok.Data;

@Data
public class Log extends BaseEntity{
	private static final long serialVersionUID = 982244216766824068L;

    private Date createDate;

    private String ip;

    private String method;

    private String module;

    private String nickname;

    private String operation;

    private String params;

    private Long time;

    private Integer userId;

}