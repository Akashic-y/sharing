package com.yn.common.util;

import org.apache.shiro.SecurityUtils;

import com.yn.common.constant.Base;
import com.yn.entity.User;

/**
 * @author yn
 * <p>
 * 2018年1月25日
 */
public class UserUtils {

    public static User getCurrentUser() {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(Base.CURRENT_USER);
        return user;
    }
}
