package com.yn.dao;

import java.util.List;

import javax.swing.text.AbstractDocument.Content;

import org.apache.ibatis.annotations.Param;

import com.yn.form.ArticleForm;
import com.yn.sharing.entity.Article;
import com.yn.sharing.entity.Tag;

public interface ArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Article record);

	List<Content> listArticles(ArticleForm form);

	List<Content> findOrderByViewsAndLimit();

	List<Content> findOrderByCreateDateAndLimit();

	List<Content> listArticlesName(ArticleForm articleForm);

	List<Article> findAll();

	void addView(Integer id);

	List<ArticleForm> listArticleForms();

	void insertTags(@Param("articleId")int articleId, @Param("tags")List<Tag> tags);

	void deleteTags(Integer id);

}