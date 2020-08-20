package com.yn.entity;

import java.util.Date;

import com.yn.common.entity.BaseEntity;
import com.yn.common.constant.StaticValue;
import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "sys_user")
public class User extends BaseEntity {
    private static final long serialVersionUID = 7038503285561248484L;

    /**
     * 登录用账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;
    /**
     * 头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号码
     */
    private String mobilePhoneNumber;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后登录时间
     */
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