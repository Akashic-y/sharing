package com.yn.service.impl;

import java.util.List;

import javax.swing.text.AbstractDocument.Content;

import com.yn.vo.PageVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yn.common.util.UserUtils;
import com.yn.dao.ArticleBodyMapper;
import com.yn.dao.ArticleMapper;
import com.yn.entity.Article;
import com.yn.entity.User;
import com.yn.form.ArticleForm;
import com.yn.common.constant.StaticValue;
import com.yn.service.ArticleService;

/**
 * @author yn
 * <p>
 * 2018年1月25日
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper dao;
    
    @Autowired
    private ArticleBodyMapper bodyDao;
    
    @Override
    public List<Content> listArticles(PageVo page) {
    	PageHelper.startPage(page.getPageNumber(), page.getPageSize(),true);
    	List<Content> rs = dao.listArticles(null);
    	PageInfo<Content> pi = new PageInfo<>(rs);
        return pi.getList();
    }

    @Override
    public List<Content> listArticles(ArticleForm article, PageVo page) {
    	PageHelper.startPage(page.getPageNumber(), page.getPageSize(),true);
    	List<Content> rs = dao.listArticles(article);
    	PageInfo<Content> pi = new PageInfo<>(rs);
        return pi.getList();
    }

    @Override
    public List<Content> findAll() {
        return dao.listArticles(new ArticleForm());
    }

    @Override
    public Article getArticleById(Integer id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public Integer publishArticle(Article article) {

        if(null != article.getId()){
            return this.updateArticle(article);
        }else{
            return this.saveArticle(article);
        }

    }

    @Override
    @Transactional
    public Integer saveArticle(Article article) {
    	bodyDao.insertSelective(article.getBody());
        User currentUser = UserUtils.getCurrentUser();
        if (null != currentUser) {
            article.setAuthor(currentUser);
        }
        dao.insertSelective(article);
        dao.insertTags(article.getId(),article.getTags());
        return article.getId();
    }

    @Override
    @Transactional
    public Integer updateArticle(Article article) {
    	bodyDao.update(article.getBody());
    	dao.deleteTags(article.getId());
    	dao.insertTags(article.getId(),article.getTags());
    	User currentUser = UserUtils.getCurrentUser();
        if (null != currentUser) {
            article.setAuthor(currentUser);
        }
        dao.updateByPrimaryKeySelective(article);
        return article.getId();
    }

    @Override
    @Transactional
    public void deleteArticleById(Integer id) {
        dao.deleteByPrimaryKey(id);
    }

    @Override
    public List<Content> listArticlesByTag(Integer id) {
    	ArticleForm article = new ArticleForm();
    	article.setTagId(id);
        return dao.listArticles(article);
    }

    @Override
    public List<Content> listArticlesByCategory(Integer id) {
    	ArticleForm article = new ArticleForm();
    	article.setCategoryId(id);
        return dao.listArticles(article);
    }

    @Override
    @Transactional
    public Article getArticleAndAddViews(Integer id) {
        Article article = dao.selectByPrimaryKey(id);
        new Thread(() -> {
        	dao.addView(id);
		}, "观看数量加1").start();
        return article;
    }

    @Override
    public List<Article> listHotArticles(int limit) {
        ArticleForm articleForm = new ArticleForm();
        articleForm.setOrderBy(StaticValue.view_counts);
        PageHelper.startPage(0, limit,true);
        List<Article> rs = dao.listArticlesName(articleForm);;
        return rs;
    }

    @Override
    public List<Article> listNewArticles(int limit) {
    	PageHelper.startPage(0, limit,true);
    	List<Article> rs = dao.listArticlesName(new ArticleForm());
        return rs;
    }

    @Override
    public List<ArticleForm> listArticleForms() {
        return dao.listArticleForms();
    }
}
