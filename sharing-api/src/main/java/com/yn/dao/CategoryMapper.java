package com.yn.dao;

import java.util.List;

import com.yn.entity.Category;
import com.yn.vo.CategoryVO;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int update(Category record);

	List<Category> findAll();

	List<CategoryVO> findAllDetail();

	CategoryVO getCategoryDetail(Integer categoryId);
}