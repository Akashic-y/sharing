package com.yn.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yn.SharingApiApplicationTests;
import com.yn.repository.CategoryRepository;

public class CategoryRepositoryTest extends SharingApiApplicationTests{

	@Autowired
	private CategoryRepository	categoryRepository;

	@Test
	public void test() {
	}
}
