package com.yn.sharing.entity;

import java.io.Serializable;

public class Category implements Serializable{
	/** 
	 */
	private static final long serialVersionUID = -4986500579964761909L;
	
	private Integer id;

	private String avatar;

	private String categoryname;

	private String description;

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar == null ? null : avatar.trim();
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname == null ? null : categoryname.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}