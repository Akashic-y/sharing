package com.yn.service.impl;

import com.yn.dao.LogMapper;
import com.yn.entity.Log;
import com.yn.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yn
 * <p>
 * 2018年4月18日
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper dao;

    @Override
    public Integer saveLog(Log log) {
    	dao.insertSelective(log);
        return log.getId();
    }
}
