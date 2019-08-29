package com.yn.dao;

import com.yn.entity.Log;

public interface LogMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Log record);

    Log selectByPrimaryKey(Integer id);

}