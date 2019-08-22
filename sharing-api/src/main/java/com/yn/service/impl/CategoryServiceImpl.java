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

        return null;
    }

    @Override
    @Transactional
    public Integer updateCategory(Category category) {
//        Category oldCategory = categoryRepository.getOne(category.getId());
//
//        oldCategory.setCategoryname(category.getCategoryname());
//        oldCategory.setAvatar(category.getAvatar());
//        oldCategory.setDescription(category.getDescription());

        return 0;
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
