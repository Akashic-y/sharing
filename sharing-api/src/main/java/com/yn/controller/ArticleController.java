package com.yn.controller;

import java.util.List;

import javax.swing.text.AbstractDocument.Content;

import com.yn.common.annotation.LogAnnotation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.yn.common.constant.Base;
import com.yn.common.constant.ResultCode;
import com.yn.common.result.Result;
import com.yn.entity.Article;
import com.yn.entity.ArticleBody;
import com.yn.entity.Tag;
import com.yn.entity.User;
import com.yn.vo.PageVo;
import com.yn.form.ArticleForm;
import com.yn.service.ArticleService;

/**
 * 文章api
 *
 * @author yn
 *         <p>
 *         2018年1月25日
 */
@RestController
@RequestMapping(value = "/articles")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@GetMapping
	@FastJsonView(exclude = { @FastJsonFilter(clazz = Article.class, props = { "body", "category", "comments" }),
			@FastJsonFilter(clazz = Tag.class, props = { "id", "avatar" }) }, include = {
					@FastJsonFilter(clazz = User.class, props = { "nickname" }) })
	@LogAnnotation(module = "文章", operation = "获取所有文章")
	public Result listArticles(ArticleForm article, PageVo page) {
		List<Content> articles = articleService.listArticles(article, page);
		return Result.success(articles);
	}

	@GetMapping("/hot")
	@FastJsonView(include = { @FastJsonFilter(clazz = Article.class, props = { "id", "title" }) })
	@LogAnnotation(module = "文章", operation = "获取最热文章")
	public Result listHotArticles() {
		int limit = 6;
		List<Content> articles = articleService.listHotArticles(limit);

		return Result.success(articles);
	}

	@GetMapping("/new")
	@FastJsonView(include = { @FastJsonFilter(clazz = Article.class, props = { "id", "title" }) })
	@LogAnnotation(module = "文章", operation = "获取最新文章")
	public Result listNewArticles() {
		int limit = 6;
		List<Content> articles = articleService.listNewArticles(limit);

		return Result.success(articles);
	}

	@GetMapping("/{id}")
	@FastJsonView(exclude = { @FastJsonFilter(clazz = Article.class, props = { "comments" }),
			@FastJsonFilter(clazz = ArticleBody.class, props = { "contentHtml" }) })
	@LogAnnotation(module = "文章", operation = "根据id获取文章")
	public Result getArticleById(@PathVariable("id") Integer id) {

		Result r = new Result();

		if (null == id) {
			r.setResultCode(ResultCode.PARAM_IS_BLANK);
			return r;
		}

		Article article = articleService.getArticleById(id);

		r.setResultCode(ResultCode.SUCCESS);
		r.setData(article);
		return r;
	}

	@GetMapping("/view/{id}")
	@FastJsonView(exclude = { @FastJsonFilter(clazz = Article.class, props = { "comments" }),
			@FastJsonFilter(clazz = ArticleBody.class, props = { "contentHtml" }),
			@FastJsonFilter(clazz = Tag.class, props = { "avatar" }) }, include = {
					@FastJsonFilter(clazz = User.class, props = { "id", "nickname", "avatar" }) })
	@LogAnnotation(module = "文章", operation = "根据id获取文章，添加阅读数")
	public Result getArticleAndAddViews(@PathVariable("id") Integer id) {

		Result r = new Result();

		if (null == id) {
			r.setResultCode(ResultCode.PARAM_IS_BLANK);
			return r;
		}

		Article article = articleService.getArticleAndAddViews(id);

		r.setResultCode(ResultCode.SUCCESS);
		r.setData(article);
		return r;
	}

	@GetMapping("/tag/{id}")
	@FastJsonView(exclude = { @FastJsonFilter(clazz = Article.class, props = { "body", "category", "comments" }),
			@FastJsonFilter(clazz = Tag.class, props = { "id", "avatar" }) }, include = {
					@FastJsonFilter(clazz = User.class, props = { "nickname" }) })
	@LogAnnotation(module = "文章", operation = "根据标签获取文章")
	public Result listArticlesByTag(@PathVariable Integer id) {
		List<Content> articles = articleService.listArticlesByTag(id);
		return Result.success(articles);
	}

	@GetMapping("/category/{id}")
	@FastJsonView(exclude = { @FastJsonFilter(clazz = Article.class, props = { "body", "category", "comments" }),
			@FastJsonFilter(clazz = Tag.class, props = { "id", "avatar" }) }, include = {
					@FastJsonFilter(clazz = User.class, props = { "nickname" }) })
	@LogAnnotation(module = "文章", operation = "根据分类获取文章")
	public Result listArticlesByCategory(@PathVariable Integer id) {
		List<Content> articles = articleService.listArticlesByCategory(id);
		return Result.success(articles);
	}

	@PostMapping("/publish")
	@RequiresAuthentication
	@LogAnnotation(module = "文章", operation = "发布文章")
	public Result saveArticle(@Validated @RequestBody Article article) {

		Integer articleId = articleService.publishArticle(article);

		Result r = Result.success();
		r.simple().put("articleId", articleId);
		return r;
	}

	@PostMapping("/update")
	@RequiresRoles(Base.ROLE_ADMIN)
	@LogAnnotation(module = "文章", operation = "修改文章")
	public Result updateArticle(@RequestBody Article article) {
		Result r = new Result();

		if (null == article.getId()) {
			r.setResultCode(ResultCode.USER_NOT_EXIST);
			return r;
		}

		Integer articleId = articleService.updateArticle(article);

		r.setResultCode(ResultCode.SUCCESS);
		r.simple().put("articleId", articleId);
		return r;
	}

	@GetMapping("/delete/{id}")
	@RequiresRoles(Base.ROLE_ADMIN)
	@LogAnnotation(module = "文章", operation = "删除文章")
	public Result deleteArticleById(@PathVariable("id") Integer id) {
		Result r = new Result();

		if (null == id) {
			r.setResultCode(ResultCode.PARAM_IS_BLANK);
			return r;
		}

		articleService.deleteArticleById(id);

		r.setResultCode(ResultCode.SUCCESS);
		return r;
	}

	@GetMapping("/listArchives")
	@LogAnnotation(module = "文章", operation = "获取文章归档日期")
	public Result listArchives() {
		return Result.success(articleService.listArticleForms());
	}

}
