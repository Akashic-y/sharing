package com.yn.repository;

import com.yn.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yn
 * <p>
 * 2018年4月18日
 */
public interface LogRepository extends JpaRepository<Log, Integer> {
}
