package blog.mkk.comment.service;

import java.sql.SQLException;

import blog.mkk.comment.dao.CommentDao;
import blog.mkk.comment.domain.Comment;
import blog.mkk.commonUtils.Page;

public class CommentService {
	/*
	 * 这里是评论service层
	 * */
	CommentDao  commentDao = new CommentDao();
	
	/**
	 * 2.添加新的留言
	 *     -->包括回复留言的留言
	 * @throws SQLException 
	 */
	
	public void updataComment(Comment m) throws SQLException{
		commentDao.updataComment(m);
	}
	
	
	/**
	 * 1.根据com_type和 page_now来查询评论
	 * @throws SQLException 
	 */
	public Page<Comment> findByCom_typeAndPage_now(String com_type,String page_now) throws SQLException{
		return commentDao.findByCom_typeAndPage_now(com_type, page_now);
	}
	
}
