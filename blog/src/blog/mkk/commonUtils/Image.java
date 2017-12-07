package blog.mkk.commonUtils;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.jdbc.TxQueryRunner;

public class Image {
	public String url;
	QueryRunner q = new TxQueryRunner();
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void addImage(String url) throws SQLException{
		/*先查询一下图片名称是否相同，如果相同就不添加新条目*/
		String sqll= "select count(*) from blog.image where url = ?";
		Object[] param = {url};
		Number n  = (Number) q.query(sqll,new ScalarHandler(), param);
		int num = n.intValue();
		if(num == 0){
			String sql = "insert into blog.image (url) values (?)";
			Object[] params = {url};
			try {
				q.update(sql, params);
			} catch (SQLException e) {
				System.out.println("插入image失败！");
				e.printStackTrace();
			}
		}
	}
}
