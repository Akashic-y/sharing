package com.yn.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.yn.entity.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
	User selectById(String Long);

	User findByAccount(String account);

	int exitUser(String account);

	List<User> findAll();

	void updateLoginInfo(@Param("id") Integer id,@Param("ip") String ip);

	List<String> getLimitIp();

    void deleteById(Long id);
}