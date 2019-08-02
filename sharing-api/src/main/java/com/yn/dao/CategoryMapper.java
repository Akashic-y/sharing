package com.yn.dao;

import com.yn.sharing.entity.Category;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int update(Category record);
}