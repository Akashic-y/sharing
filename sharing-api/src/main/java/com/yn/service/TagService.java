package com.yn.service;

import java.util.List;

import com.yn.entity.Tag;
import com.yn.vo.TagVO;

/**
 * @author yn
 * <p>
 * 2018年1月25日
 */
public interface TagService {

    List<Tag> findAll();

    Tag getTagById(Integer id);

    Integer saveTag(Tag tag);

    Integer updateTag(Tag tag);

    void deleteTagById(Integer id);

    List<Tag> listHotTags(int limit);

    List<TagVO> findAllDetail();

    TagVO getTagDetail(Integer tagId);

}
