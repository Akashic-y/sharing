package com.yn.service.impl;

import java.util.List;
import java.util.Random;
import java.util.Set;

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
 *         <p>
 *         2018年1月23日
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
		return dao.getUserById(id);
	}

	@Override
	@Transactional
	public Long saveUser(User user) {

		PasswordHelper.encryptPassword(user);
		int index = new Random().nextInt(6) + 1;
		String avatar = "/static/user/user_" + index + ".png";

		user.setAvatar(avatar);
		return dao.saveUser(user);
	}

	@Override
	@Transactional
	public Long updateUser(User user) {
		return dao.updateUser(user);
	}

	@Override
	@Transactional
	public void deleteUserById(Long id) {
		dao.deleteUserById(id);
	}

	@Override
	public int exitUser(String account) {
		return dao.exitUser(account);
	}

	@Override
	public void updateLoginInfo(Integer id,String ip) {
		dao.updateLoginInfo(id,ip);
	}

	@Override
	public boolean isLimitIP() {
		if(redisManager.get("limitIP") == null) {
			synchronized (this) {
				if(redisManager.get("limitIP") == null) {
					Set<String> limitIp = dao.getLimitIp();
					redisManager.set("limitIP", limitIp);//默认30分钟
				}
			}
		}
		Set set = redisManager.get("limitIP",Set.class);
		return set.contains(IpUtils.getIpAddr());
	}

}
