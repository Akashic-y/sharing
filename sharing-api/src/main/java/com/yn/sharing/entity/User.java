package com.yn.sharing.entity;

import java.io.Serializable;
import java.util.Date;

import com.yn.form.StaticValue;

import lombok.Data;

@Data
public class User implements Serializable {
	private static final long serialVersionUID = 7038503285561248484L;

	private Long id;
	
	private String account;

	private String password;
	/**
	 * 头像
	 */
	private String avatar;

	private String email;

	private String nickname;

	private String mobilePhoneNumber;

	private String salt;

	private Date createDate;

	private Date lastLogin;

	/**
	 * 系统用户的状态
	 */
	private String status = StaticValue.normal;

	/**
	 * 是否是管理员
	 */
	private Boolean admin = false;

	/**
	 * 逻辑删除flag
	 */
	private Boolean deleted = Boolean.FALSE;

}