package blog.mkk.commonUtils;

import java.util.List;

public class Page<T>{//分页类

	public int page_size;//每一篇显示pagesize条信息
	public int count_all;//总共有多少条信息
	public int page_now;//当前页
	public int page_all;//总共有多少页
	public String url;//返回pagenow对应的url，设置前台a标签的href
	/*携带某分类下的对象集合。如：blog/article/index.jsp中，要有从数据库查询到的article对象*/
	/*因为含有的对象不同，所以用泛型*/
	public List<T> list;//携带的内容 
	
	public String loginName;//用于存储session中的loginname在创建模板中使用
	
	
	public int getPage_size() {
		return page_size;
	}
	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}
	public int getCount_all() {
		return count_all;
	}
	public void setCount_all(int count_all) {
		this.count_all = count_all;
	}
	public int getPage_now() {
		return page_now;
	}
	public void setPage_now(int page_now) {
		this.page_now = page_now;
	}
	public int getPage_all() {
		return page_all;
	}
	public void setPage_all(int page_all) {
		this.page_all = page_all;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	
}
