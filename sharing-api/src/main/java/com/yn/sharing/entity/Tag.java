package com.yn.sharing.entity;

import java.io.Serializable;

public class Tag implements Serializable{

	private static final long serialVersionUID = -8703756043484996111L;

	private Integer id;
	
	private String avatar;

	private String tagname;

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar == null ? null : avatar.trim();
	}

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname == null ? null : tagname.trim();
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Tag [avatar=" + avatar + ", tagname=" + tagname + "]";
	}
	
}