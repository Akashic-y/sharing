package com.yn.sharing.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Log implements Serializable{
	private static final long serialVersionUID = 982244216766824068L;

	private Integer id;

    private Date createDate;

    private String ip;

    private String method;

    private String module;

    private String nickname;

    private String operation;

    private String params;

    private Long time;

    private Long userid;

}