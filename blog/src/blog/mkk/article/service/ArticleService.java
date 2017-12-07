package blog.mkk.article.service;

import java.sql.SQLException;

import blog.mkk.article.dao.ArticleDao;
import blog.mkk.article.doamin.Article;
import blog.mkk.commonUtils.Page;

public class ArticleService {

	ArticleDao article = new  ArticleDao();
	
	/*
	 * 2.根据art_id来查询某一篇文章
	 */
	public Page<Article> findByArt_id(String oper,String art_id) throws SQLException{
		if(oper.equals("p")){//上一篇文章
			return article.findByArt_id_before(art_id);
		}else if(oper.equals("n")){//下一篇文章
			return article.findByArt_id_next(art_id);
		}else{//art_id某一篇
			return article.findByArt_id(art_id);
		}
	}
	
	
	/*
	 * 1.根据pagenow来查询文章
	 */
	public Page<Article> findByPage_noe(int page_now) throws SQLException{
		return article.findByPage_now(page_now);
	}
	
}
