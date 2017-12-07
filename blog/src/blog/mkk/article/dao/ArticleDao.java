package blog.mkk.article.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import blog.mkk.article.doamin.Article;
import blog.mkk.commonUtils.Constant;
import blog.mkk.commonUtils.Page;
import cn.itcast.jdbc.TxQueryRunner;

public class ArticleDao {

	QueryRunner queryRunner = new TxQueryRunner();
	
	/*
	 * 1.写新文章
	 * */
	public void insertArticleToDB(Article a){
		String sql = "insert into blog.article (art_type,art_author,art_date,art_content,art_mark,art_title) values(?,?,?,?,?,?)";
		Object[] params = {a.getArt_type(),a.getArt_author(),a.getArt_date(),a.getArt_content(),a.getArt_mark(),a.getArt_title()};
		try {
			queryRunner.update(sql,params);
		} catch (SQLException e) {
			System.out.println("插入新文章不成功");
			e.printStackTrace();
		}
	}
	
	/*
	 * 2.获取文章的title和id用于编辑
	 */
	public Page<Article> getAllUpdataArticle(){
		Page<Article> page = new Page<>();
		String sql = "SELECT art_title, art_id   FROM blog.article  order by art_id desc";
		try {
			List<Article> articleList = queryRunner.query(sql, new BeanListHandler<>(Article.class) );
			page.setList(articleList);
		} catch (SQLException e) {
			System.out.println("getAllUpdataArticle方法失败！");
			e.printStackTrace();
		}
		return page;
	}
	
	/*
	 * 3.根据文章的id来更新文章内容
	 */
	public void updataArticleByArt_id(Article newArticle) throws SQLException {

		int art_id = newArticle.getArt_id();	
		String sql = "update blog.article set  art_type=?,art_author =?,art_date =?,art_content =?,art_mark=? ,art_title=?  where art_id = ?";
		int type = newArticle.getArt_type();
		String author = newArticle.getArt_author();
		String date = newArticle.getArt_date();
		String content = newArticle.getArt_content();
		String mark = newArticle.getArt_mark();
		String title = newArticle.getArt_title();
		
		Object[] params = {type,author,date,content,mark,title,art_id};
			queryRunner.update(sql,params);
	}
	
	/*
	 * 4.根据文章的id来删除该文章
	 */
	public void deletOneArticle(String art_id) throws SQLException {
		String sql = "delete from blog.article where art_id = ?";
		Object[] params = {art_id};
		queryRunner.update(sql,params);
	}
	
	
	/*
	 * ---------------上面是管理员操作--------------------华丽的分割线-------------------下面是用户浏览操作-----------------------
	 * /	

	
	
	
	/*
	 * 3. 查询最新添加的文章，给新添加文章然后生成html使用
	 */
	  public Page<Article> findLast(){
		
		Page<Article> p = new Page<>();
		String sql = "select * from blog.article order by art_id desc limit 1 ";
		List<Article> list = new ArrayList<>();
		
		try {
			Article a = queryRunner.query(sql, new BeanHandler<Article>(Article.class));
			list.add(a);
			p.setList(list);
		} catch (SQLException e) {
			System.out.println("查询最新文章失败");
			e.printStackTrace();
		}
		String url = "ArticleServlet?method=getArticleOne";
		p.setUrl(url);
		return p;
	} 
	
	
	/**
	 * 
	 * 2.查询某一篇文章
	 * 这个是查询下一篇内容的语句
	 * SELECT * FROM blog.article WHERE art_id >2 limit 1;
	 * 这个是查询上一篇内容的语句
	 * SELECT * FROM blog.article WHERE art_id <2 limit 1;
	 * 每个方法前的注释就这这个方法的说明
	 */
	public Page<Article> findByArt_id(String art_id) throws SQLException{
		/*查询art_id文章*/
		Page<Article> page = new Page<Article>();
		String sql = "SELECT * FROM blog.article WHERE art_id  = ?  limit 1";
		Object[] params = {art_id}; 
		Article article = queryRunner.query(sql, new BeanHandler<Article>(Article.class),params);//获取一篇文章
		List<Article> list = new ArrayList<Article>();
		list.add(article);
		page.setList(list);
		return page;
	}
	public Page<Article> findByArt_id_next(String art_id) throws SQLException{
		/*查询art_id下一篇文章*/
		Page<Article> page = new Page<Article>();
		String sql = "SELECT * FROM blog.article WHERE art_id  > ?  limit 1";
		Object[] params = {art_id}; 
		Article article = queryRunner.query(sql, new BeanHandler<Article>(Article.class),params);//获取一篇文章
		List<Article> list = new ArrayList<Article>();
		list.add(article);
		page.setList(list);
		return page;
	}
	public Page<Article> findByArt_id_before(String art_id) throws SQLException{
				/*查询被art_id上一篇文章*/
		Page<Article> page = new Page<Article>();
		String sql_count = "select count(*) from blog.article where art_id<"+art_id;
		Number count = (Number)queryRunner.query(sql_count, new ScalarHandler());
		int c = count.intValue();
		c = c - 1;
		String sql = "SELECT * FROM blog.article  limit ?, 1 ";
		Object[] params = {c}; 
		Article article = queryRunner.query(sql, new BeanHandler<Article>(Article.class),params);//获取一篇文章
		List<Article> list = new ArrayList<Article>();
		list.add(article);
		page.setList(list);
		return page;
	}
	
	
	
	/*
	 * 1.第一个功能，查询某一页article功能
	 * */
	public Page<Article> findByPage_now(int page_now) throws SQLException{
		Page<Article> page = new Page<Article>();
		int PAGE_SIZE = Constant.PAGE_SIZE;
		String sql = "select count(*) from blog.article";
		Number number =   (Number)queryRunner.query(sql, new ScalarHandler());//获取数量
		int count_all = number.intValue();
		page.setCount_all(count_all);
		page.setPage_size(PAGE_SIZE);
		page.setPage_now(page_now);
		int page_all = count_all % PAGE_SIZE == 0 ? count_all / PAGE_SIZE : (count_all / PAGE_SIZE)+1;
		page.setPage_all(page_all);
		
		int start =  PAGE_SIZE * (page_now-1);
//		int end = (PAGE_SIZE * page_now) - 1;//因为limit ?,?   --> 	前一个？指的的是从第几个开始（下标是从0开始的），
															   // 	第二个？指的是读取几行
		int _page_size = PAGE_SIZE;
		if(count_all < (page_now * PAGE_SIZE) ){
			_page_size = count_all - PAGE_SIZE * (page_now - 1) - 1;
		}
		sql = "select * from blog.article order by art_id DESC limit ?,? ";
		Object[] params = {start,_page_size};
		List<Article> listArticle =   queryRunner.query(sql, new BeanListHandler<Article>(Article.class),params);//直接返回Article对象list
		page.setList(listArticle);
		return page;
	}


	
}
