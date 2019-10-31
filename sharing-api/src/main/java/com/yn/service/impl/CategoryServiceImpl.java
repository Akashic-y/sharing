package com.yn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yn.dao.CategoryMapper;
import com.yn.entity.Category;
import com.yn.service.CategoryService;
import com.yn.vo.CategoryVO;

/**
 * @author yn
 * <p>
 * 2018年1月25日
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper dao;


    @Override
    public List<Category> findAll() {
        return dao.findAll();
    }

    @Override
    public Category getCategoryById(Integer id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public Integer saveCategory(Category category) {
    	dao.insertSelective(category);
        return category.getId();
    }

    @Override
    @Transactional
    public Integer updateCategory(Category category) {
        return dao.updateByPrimaryKeySelective(category);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Integer id) {
        dao.deleteByPrimaryKey(id);
    }

    @Override
    public List<CategoryVO> findAllDetail() {
        return dao.findAllDetail();
    }

    @Override
    public CategoryVO getCategoryDetail(Integer categoryId) {
        return dao.getCategoryDetail(categoryId);
    }

}
