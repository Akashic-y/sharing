package com.yn.dao;

import com.yn.entity.ArticleBody;
import tk.mybatis.mapper.common.Mapper;

public interface ArticleBodyMapper extends Mapper<ArticleBody> {
    int update(ArticleBody record);
}