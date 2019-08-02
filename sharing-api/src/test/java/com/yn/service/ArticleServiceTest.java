package com.yn.service;

import java.util.List;

import javax.swing.text.AbstractDocument.Content;

import com.yn.vo.PageVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yn.SharingApiApplicationTests;
import com.yn.form.ArticleForm;

public class ArticleServiceTest extends SharingApiApplicationTests {

	@Autowired
	private ArticleService articleService;

	@Test
	public void listArticlesByTagTest() {
		int id = 1;
		List<Content> as = articleService.listArticlesByTag(id);

		System.out.println(as.size());

	}

	@Test
	public void listArticlesByCategoryTest() {
		int id = 1;

		List<Content> as = articleService.listArticlesByCategory(id);

		System.out.println(as.size());
	}

	@Test
	public void listHotArticlesTest() {

		List<Content> as = articleService.listHotArticles(4);

		System.out.println(as.size());
	}

	@Test
	public void listNewArticlesTest() {

		List<Content> as = articleService.listNewArticles(4);

		System.out.println(as.size());
	}

	@Test
	public void listArticlesTest() {

		PageVo p = new PageVo();
		p.setPageNumber(1);
		p.setPageSize(5);
		p.setName("createDate");
		p.setSort("desc");

		List<Content> as = articleService.listArticles(p);

		System.out.println(as);

	}

	@Test
	public void listArticlesTest2() {

		PageVo p = new PageVo();
		p.setPageNumber(1);
		p.setPageSize(5);
		p.setName("a.createDate");
		p.setSort("desc");

		ArticleForm form = new ArticleForm();
		form.setYear(2018);
		form.setMonth(2);
		form.setTagId(2);
		form.setCategoryId(3);

		List<Content> as = articleService.listArticles(form, p);
		System.out.println(as);

	}
}
