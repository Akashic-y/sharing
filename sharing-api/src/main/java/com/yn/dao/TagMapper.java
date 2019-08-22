package com.yn.dao;

import java.util.List;

import com.yn.entity.Tag;
import com.yn.vo.TagVO;

public interface TagMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tag record);

	List<TagVO> findAllDetail();

	TagVO getTagDetail(Integer id);

	List<Tag> listHotTagsByArticleUse(int limit);

	List<Tag> findAll();
}