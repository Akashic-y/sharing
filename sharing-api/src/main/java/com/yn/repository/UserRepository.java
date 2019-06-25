package com.yn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yn.entity.User;

/**
 * @author yn
 * <p>
 * 2018年1月23日
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByAccount(String account);

}
