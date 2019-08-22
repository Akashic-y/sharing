package com.yn.sharing.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Tag implements Serializable{

	private static final long serialVersionUID = -8703756043484996111L;

	private Integer id;
	
	private String avatar;

	private String tagname;

}