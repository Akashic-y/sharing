package com.yn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yn.entity.Log;

/**
 * @author yn
 * <p>
 * 2018年4月18日
 */
public interface LogRepository extends JpaRepository<Log, Integer> {
}
