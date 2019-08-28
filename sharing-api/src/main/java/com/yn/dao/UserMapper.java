package com.yn.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.yn.entity.User;

public interface UserMapper {
    int deleteUserById(Long id);

    Long saveUser(User record);

    User getUserById(Long id);

    Long updateUser(User record);

	User findByAccount(String account);

	int exitUser(String account);

	List<User> findAll();

	void updateLoginInfo(@Param("id") Integer id,@Param("ip") String ip);

	Set<String> getLimitIp();
}