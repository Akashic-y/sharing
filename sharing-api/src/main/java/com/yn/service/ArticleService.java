package com.yn.service;

import java.util.List;

import javax.swing.text.AbstractDocument.Content;

import com.yn.entity.Article;
import com.yn.form.ArticleForm;
import com.yn.vo.PageVo;

/**
 * @author yn
 * <p>
 * 2018年1月25日
 */
public interface ArticleService {

    List<Content> listArticles(PageVo page);

    List<Content> findAll();

    Article getArticleById(Integer id);

    Integer publishArticle(Article article);

    Integer saveArticle(Article article);

    Integer updateArticle(Article article);

    void deleteArticleById(Integer id);

    List<Content> listArticlesByTag(Integer id);

    List<Content> listArticlesByCategory(Integer id);

    Article getArticleAndAddViews(Integer id);

    List<Content> listHotArticles(int limit);

    List<Content> listNewArticles(int limit);

	List<Content> listArticles(ArticleForm article, PageVo page);

	List<ArticleForm> listArticleForms();

}
