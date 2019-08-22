package com.yn.sharing.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Category implements Serializable{
	private static final long serialVersionUID = -4986500579964761909L;
	
	private Integer id;

	private String avatar;

	private String categoryname;

	private String description;

}