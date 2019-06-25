package com.yn.vo;

import com.yn.entity.Category;

/**
 * @author yn
 * <p>
 * 2018年1月29日
 */
public class CategoryVO extends Category {

    /**
     *
     */
    private static final long serialVersionUID = -2975739216517528014L;


    private int articles;

    public int getArticles() {
        return articles;
    }

    public void setArticles(int articles) {
        this.articles = articles;
    }


}
