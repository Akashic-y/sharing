package com.yn.dao;

import java.util.List;

import com.yn.entity.Category;
import com.yn.vo.CategoryVO;
import tk.mybatis.mapper.common.Mapper;

public interface CategoryMapper extends Mapper<Category> {
    List<Category> findAll();

    List<CategoryVO> findAllDetail();

    CategoryVO getCategoryDetail(Integer id);
}