package com.yn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yn.common.util.UserUtils;
import com.yn.dao.ArticleMapper;
import com.yn.dao.CommentMapper;
import com.yn.entity.Comment;
import com.yn.service.CommentService;

/**
 * @author yn
 * <p>
 * 2018年1月25日
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private ArticleMapper dao;

    @Autowired
    private CommentMapper cdao;

    @Override
    public List<Comment> findAll() {
        return cdao.findAll();
    }

    @Override
    public Comment getCommentById(Integer id) {
        return cdao.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public Integer saveComment(Comment comment) {
    	comment.setAuthorId(UserUtils.getCurrentUser().getId());
        return cdao.insertSelective(comment);
    }


    @Override
    @Transactional
    public void deleteCommentById(Integer id) {
        cdao.deleteById(id);
    }

    @Override
    public List<Comment> listCommentsByArticle(Integer id) {
    	//PageHelper.startPage(page.getPageNumber(), page.getPageSize(),true);
    	//TODO 给页面的数据类型
    	List<Comment> rs = cdao.findByArticle(id);
        return rs;
    }

    @Override
    @Transactional
    public Comment saveCommentAndChangeCounts(Comment comment) {
        comment.setArticleId(UserUtils.getCurrentUser().getId());
        //设置level
        if(null == comment.getParent()){
            comment.setLevel(0);
        }else{
            if(null == comment.getToUser()){
                comment.setLevel(1);
            }else{
                comment.setLevel(2);
            }
        }
        cdao.insertSelective(comment);
        //评论数加1
        dao.changeCount(comment.getArticleId());
        return comment;

    }

    @Override
    @Transactional
    public void deleteCommentByIdAndChangeCounts(Integer id) {
        cdao.deleteById(id);
        //评论数减1
        dao.reduceCount(id);
    }

}
