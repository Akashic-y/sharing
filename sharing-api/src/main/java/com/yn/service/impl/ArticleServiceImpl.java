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
import com.yn.dao.CategoryMapper;
import com.yn.entity.Article;
import com.yn.entity.User;
import com.yn.form.ArticleForm;
import com.yn.form.StaticValue;
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
    
    @Autowired
    private CategoryMapper categoryDao;
    
    @Override
    public List<Content> listArticles(PageVo page) {
    	PageHelper.startPage(page.getPageNumber(), page.getPageSize(),true);
    	List<Content> rs = dao.listArticles(null);
    	PageInfo<Content> pi = new PageInfo<Content>(rs);
        return pi.getList();
    }

    @Override
    public List<Content> listArticles(ArticleForm article, PageVo page) {
    	PageHelper.startPage(page.getPageNumber(), page.getPageSize(),true);
    	List<Content> rs = dao.listArticles(article);
    	PageInfo<Content> pi = new PageInfo<Content>(rs);
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
    	//TODO
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
		List<Content> rs = dao.listArticles(article);
        return rs;
    }

    @Override
    public List<Content> listArticlesByCategory(Integer id) {
    	ArticleForm article = new ArticleForm();
    	article.setCategoryId(id);
		List<Content> rs = dao.listArticles(article);
        return rs;
    }

    @Override
    @Transactional
    public Article getArticleAndAddViews(Integer id) {
        Article article = dao.selectByPrimaryKey(id);
        //TODO
        dao.addView(id);
        return article;
    }

    @Override
    public List<Content> listHotArticles(int limit) {
        PageHelper.startPage(0, limit,true);
        ArticleForm articleForm = new ArticleForm();
        articleForm.setOrderBy(StaticValue.view_counts);
    	List<Content> rs = dao.listArticlesName(articleForm);
    	PageInfo<Content> pi = new PageInfo<Content>(rs);
        return pi.getList();
    }

    @Override
    public List<Content> listNewArticles(int limit) {
    	PageHelper.startPage(0, limit,true);
    	List<Content> rs = dao.listArticlesName(new ArticleForm());
    	PageInfo<Content> pi = new PageInfo<Content>(rs);
        return pi.getList();
    }

    @Override
    public List<ArticleForm> listArticleForms() {

        return dao.listArticleForms();
    }
}
