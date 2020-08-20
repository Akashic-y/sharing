package com.yn.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yn.common.cache.RedisManager;
import com.yn.common.util.IpUtils;
import com.yn.common.util.PasswordHelper;
import com.yn.dao.UserMapper;
import com.yn.entity.User;
import com.yn.service.UserService;

/**
 * @author yn
 * <p>
 * 2018年1月23日
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper dao;

    @Autowired
    private RedisManager redisManager;

    @Override
    public List<User> findAll() {
        return dao.findAll();
    }

    @Override
    public User getUserByAccount(String account) {
        return dao.findByAccount(account);
    }

    @Override
    public User getUserById(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public int saveUser(User user) {
        PasswordHelper.encryptPassword(user);
        int index = new Random().nextInt(6) + 1;
        String avatar = "/static/user/user_" + index + ".png";
        user.setAvatar(avatar);
        return dao.insertSelective(user);
    }

    @Override
    @Transactional
    public int updateUser(User user) {
        return dao.updateByPrimaryKeySelective(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        dao.deleteById(id);
    }

    @Override
    public int exitUser(String account) {
        return dao.exitUser(account);
    }

    @Override
    public void updateLoginInfo(Integer id, String ip) {
        dao.updateLoginInfo(id, ip);
    }

    @Override
    public boolean isLimitIP() {
        if (redisManager.get("limitIP") == null) {
            synchronized (this) {
                if (redisManager.get("limitIP") == null) {
                    List<String> limitIp = dao.getLimitIp();
                    redisManager.set("limitIP", limitIp);//默认30分钟
                }
            }
        }
        ArrayList set = redisManager.get("limitIP", ArrayList.class);
        return set.contains(IpUtils.getIpAddr());
    }

}
