package blog.mkk.comment.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import blog.mkk.comment.domain.Comment;
import blog.mkk.commonUtils.Page;
import cn.itcast.jdbc.TxQueryRunner;

public class CommentDao {

	/**
	 * 评论相关的持久层代码
	 */

	QueryRunner queryRunner = new TxQueryRunner();
	
	
	/**
	 * 2.根据指定id删除留言
	 */
	public void deleteComment(String com_id) {
		String sql = "delete from blog.comment where com_id =" +com_id;
		try {
			queryRunner.update(sql);
		} catch (SQLException e) {
			System.out.println("commentDao的deleteComment()失败");
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 1.获取所有的留言
	 */
	public Page<Comment> getAllComment(){
		String sql = "SELECT * FROM blog.comment order by com_id desc;";
		Page<Comment> page = new Page<>();
		List<Comment> list = null ;
		try {
			list = queryRunner.query(sql, new BeanListHandler<Comment>(Comment.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		page.setList(list);
		return page;
	}
	
	
	/*****************上面是admin界面的操作留言数据库的内容*****************/
	
	/**
	 * 添加新的回复
	 * @m 留言对象
	 */
	public void updataComment(Comment m) throws SQLException{
	String sql = "insert into blog.comment (com_name,com_date,com_content,art_id,com_type,com_pid) values(?,?,?,?,?,?)";
	Object[] params = {m.getCom_name(),m.getCom_date(),m.getCom_content(),m.getArt_id(),m.getCom_type(),m.getCom_pid()}; 
	queryRunner.update(sql,params);
	}
	
	
	
	/**
	 * 1.根据com_type查询所有评论内容
	 * @throws SQLException 
	 */
	public Page<Comment> findByCom_typeAndPage_now(String com_type,String page_now) throws SQLException{
		Page<Comment> page = new Page<>();
		int page_size = 10;
		String sql = "select count(*) from blog.comment "; 
		Number  n = (Number)queryRunner.query(sql, new ScalarHandler());
		int count = n.intValue();
		int page_all = count % page_size == 0 ? count / page_size :(count / page_size) + 1; 
		page.setPage_all(page_all);
		page.setCount_all(count);
		page.setPage_size(10);
		int _page_now = Integer.parseInt(page_now);
		page.setPage_now(_page_now);
		int count_now = page_size * ( _page_now - 1 );
		sql = "SELECT * FROM blog.comment WHERE com_type = ?  order by com_id desc limit ?, ?;";
		int _com_type = Integer.parseInt(com_type);
		Object[]  params = {_com_type,count_now,page_size};

//		Object[]  params = {_com_type};
		List<Comment> list = queryRunner.query(sql, new BeanListHandler<Comment>(Comment.class),params);
		
		page.setList(list);
		return page;
	}


	
}
