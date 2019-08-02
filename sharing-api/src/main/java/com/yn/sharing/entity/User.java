package com.yn.sharing.entity;

import java.io.Serializable;
import java.util.Date;

import com.yn.form.StaticValue;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account == null ? null : account.trim();
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar == null ? null : avatar.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber == null ? null : mobilePhoneNumber.trim();
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt == null ? null : salt.trim();
	}

	@Override
	public String toString() {
		return "User [account=" + account + ", password=" + password + ", avatar=" + avatar + ", email=" + email
				+ ", nickname=" + nickname + ", mobilePhoneNumber=" + mobilePhoneNumber + ", salt=" + salt
				+ ", createDate=" + createDate + ", lastLogin=" + lastLogin + ", status=" + status + ", admin=" + admin
				+ ", deleted=" + deleted + "]";
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public User() {
	}

	public User(Long id, String account, Boolean admin, String avatar, Date createDate, Boolean deleted, String email,
			Date lastLogin, String mobilePhoneNumber, String nickname, String password, String salt, String status) {
		this.id = id;
		this.account = account;
		this.password = password;
		this.avatar = avatar;
		this.email = email;
		this.nickname = nickname;
		this.mobilePhoneNumber = mobilePhoneNumber;
		this.salt = salt;
		this.createDate = createDate;
		this.lastLogin = lastLogin;
		this.status = status;
		this.admin = admin;
		this.deleted = deleted;
	}
}