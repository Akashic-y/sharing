package com.yn.dao;

import java.util.List;

import javax.swing.text.AbstractDocument.Content;

import org.apache.ibatis.annotations.Param;

import com.yn.entity.Article;
import com.yn.entity.Tag;
import com.yn.form.ArticleForm;
import tk.mybatis.mapper.common.Mapper;

public interface ArticleMapper extends Mapper<Article> {

	Article selectById(int id);

	List<Content> listArticles(ArticleForm form);

	List<Content> findOrderByViewsAndLimit();

	List<Content> findOrderByCreateDateAndLimit();

	List<Article> listArticlesName(ArticleForm articleForm);

	List<Article> findAll();

	void addView(Integer id);

	List<ArticleForm> listArticleForms();

	void insertTags(@Param("articleId")int articleId, @Param("tags")List<Tag> tags);

	void deleteTags(Integer id);

	void changeCount(Integer id);

	void reduceCount(Integer id);

	int deleteById(Integer id);

}