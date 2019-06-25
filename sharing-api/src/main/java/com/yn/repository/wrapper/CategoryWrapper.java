package com.yn.repository.wrapper;

import java.util.List;

import com.yn.vo.CategoryVO;

/**
 * @author yn
 * <p>
 * 2018年1月25日
 */
public interface CategoryWrapper {

    List<CategoryVO> findAllDetail();

    CategoryVO getCategoryDetail(Integer categoryId);
}
