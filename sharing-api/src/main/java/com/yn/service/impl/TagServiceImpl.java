package com.yn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yn.dao.TagMapper;
import com.yn.entity.Tag;
import com.yn.service.TagService;
import com.yn.vo.TagVO;

/**
 * @author yn
 * <p>
 * 2018年1月25日
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper dao;

    @Override
    public List<Tag> findAll() {
        return dao.findAll();
    }

    @Override
    public Tag getTagById(Integer id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public Integer saveTag(Tag tag) {
        return dao.insertSelective(tag);
    }

    @Override
    @Transactional
    public Integer updateTag(Tag tag) {
        return dao.updateByPrimaryKeySelective(tag);
    }

    @Override
    @Transactional
    public void deleteTagById(Integer id) {
        dao.deleteByPrimaryKey(id);
    }

    @Override
    public List<Tag> listHotTags(int limit) {
        return dao.listHotTagsByArticleUse(limit);
    }

    @Override
    public List<TagVO> findAllDetail() {
        return dao.findAllDetail();
    }

    @Override
    public TagVO getTagDetail(Integer tagId) {
        return dao.getTagDetail(tagId);
    }
}
