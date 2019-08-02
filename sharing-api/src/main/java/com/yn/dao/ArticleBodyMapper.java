package com.yn.dao;

import com.yn.sharing.entity.ArticleBody;

public interface ArticleBodyMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(ArticleBody record);

    ArticleBody selectByPrimaryKey(Long id);

    int update(ArticleBody record);
}