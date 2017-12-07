package blog.mkk.admin.service;

import java.sql.SQLException;

import blog.mkk.admin.dao.AdminDao;
import blog.mkk.article.doamin.Article;
import blog.mkk.comment.domain.Comment;
import blog.mkk.commonUtils.Page;

public class AdminService {

	AdminDao adminDao = new AdminDao(); 
	
	/*
	 * 1.向数据库中添加新的文章
	 */
	public void insertArticleToDB(Article a){
		adminDao.insertArticleToDB(a);
	}
	/*
	 * 2.获取文章的title和id用于编辑
	 */
	public Page<Article> getAllUpdataArticle(){
		return adminDao.getAllUpdataArticle();
	}
	/*
	 * 3.获取文章的id查询文章，用于编辑
	 */
	public Page<Article> findByArt_id(String art_id) throws SQLException{
		return adminDao.findByArt_id(art_id);
	}
	/*
	 * 4.根据文章的id来更新文章内容
	 */
	public void updataArticleByArt_id(Article newArticle) throws SQLException {
		adminDao.updataArticleByArt_id(newArticle);
	}
	/*
	 * 5.根据文章的id来删除该文章
	 */
	public void deletOneArticle(String art_id) throws SQLException {
		adminDao.deletOneArticle(art_id);
	}
	/*************下面是留言板块********************/
	/**
	 * 1.获取所有留言
	 */
	public Page<Comment> getAllComments(){
		return adminDao.getAllComments();
	}
	/**
	 * 2.删词指定id的留言
	 * @param com_id 留言id
	 */
	public void deleteComment(String com_id) {
		adminDao.deleteComment(com_id);
	}
	
}
