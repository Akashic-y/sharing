package com.yn.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yn.BlogApiApplicationTests;
import com.yn.entity.Category;
import com.yn.repository.CategoryRepository;

public class CategoryRepositoryTest extends BlogApiApplicationTests{

	@Autowired
	private CategoryRepository	categoryRepository;

	@Test
	public void test() {
	}
}
