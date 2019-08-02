package com.yn.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yn.common.util.PasswordHelper;
import com.yn.dao.UserMapper;
import com.yn.sharing.entity.User;
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

}
