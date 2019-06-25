package com.yn.repository.wrapper;

import java.util.List;

import com.yn.vo.TagVO;

/**
 * @author yn
 * <p>
 * 2018年1月25日
 */
public interface TagWrapper {

    List<TagVO> findAllDetail();

    TagVO getTagDetail(Integer tagId);
}
