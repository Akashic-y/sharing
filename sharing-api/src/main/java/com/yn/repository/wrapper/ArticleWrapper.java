package com.yn.repository.wrapper;

import com.yn.entity.Article;
import com.yn.vo.ArticleVo;
import com.yn.vo.PageVo;

import java.util.List;

public interface ArticleWrapper {
    List<Article> listArticles(PageVo page);

    List<Article> listArticles(ArticleVo article, PageVo page);

    List<ArticleVo> listArchives();

}
