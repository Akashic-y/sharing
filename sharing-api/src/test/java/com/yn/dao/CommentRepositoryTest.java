package com.yn.dao;

import com.yn.SharingApiApplicationTests;
import com.yn.entity.Comment;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class CommentRepositoryTest extends SharingApiApplicationTests {

    @Autowired
    private CommentMapper dao;

    @Test
    public void add() {

        Comment parent = dao.selectByPrimaryKey(1);

        Comment sub1 = new Comment();
        sub1.setContent("sub1");
        sub1.setParent(parent);

        Comment sub2 = new Comment();
        sub2.setContent("sub2");
        sub2.setParent(parent);

        dao.insertSelective(sub1);
        dao.insertSelective(sub2);

    }

    @Test
    @Transactional
    public void get() {

        Comment parent = dao.selectByPrimaryKey(1);

        System.out.println(parent.getChildrens().size());

    }
}
