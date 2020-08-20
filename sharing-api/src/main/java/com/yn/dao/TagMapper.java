package com.yn.dao;

import java.util.List;

import com.yn.entity.Tag;
import com.yn.vo.TagVO;
import tk.mybatis.mapper.common.Mapper;

public interface TagMapper extends Mapper<Tag> {

    List<TagVO> findAllDetail();

    TagVO getTagDetail(Integer id);

    List<Tag> listHotTagsByArticleUse(int limit);

    List<Tag> findAll();
}