package blog.mkk.admin.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import blog.mkk.article.dao.ArticleDao;
import blog.mkk.article.doamin.Article;
import blog.mkk.comment.dao.CommentDao;
import blog.mkk.comment.domain.Comment;
import blog.mkk.commonUtils.Page;
import cn.itcast.jdbc.TxQueryRunner;

public class AdminDao {
 
	QueryRunner query = new TxQueryRunner();
	
	
	/*
	 * 1.把新的文章写入数据库
	 */
	
	public void insertArticleToDB(Article a){
		ArticleDao articleDao = new ArticleDao();
		articleDao.insertArticleToDB(a);
	}
	/*
	 * 2.获取文章的title和id用于编辑
	 */
	public Page<Article> getAllUpdataArticle(){
		return new ArticleDao().getAllUpdataArticle();
	}
	/*
	 * 3.获取文章的id查询文章，用于编辑
	 */
	public Page<Article> findByArt_id(String art_id) throws SQLException{
		return new ArticleDao().findByArt_id(art_id);
	}
	/*
	 * 4.根据文章的id来更新文章内容
	 */
	public void updataArticleByArt_id(Article newArticle) throws SQLException {
		new ArticleDao().updataArticleByArt_id(newArticle);
	}
	/*
	 * 5.根据文章的id来删除该文章
	 */
	public void deletOneArticle(String art_id) throws SQLException {
		new ArticleDao().deletOneArticle(art_id);
	}
	
	/*********************下面是留言内容**********************/
	/**
	 * 1.获取所有留言
	 */
	public Page<Comment> getAllComments(){
		return new CommentDao().getAllComment();
	}
	/**
	 * 2.删除指定id的留言
	 * @param com_id
	 */
	public void deleteComment(String com_id) {
		new CommentDao().deleteComment(com_id);
	}
	
	
	
	
	
}
