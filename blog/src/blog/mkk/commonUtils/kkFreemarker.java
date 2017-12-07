package blog.mkk.commonUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blog.mkk.article.doamin.Article;
import blog.mkk.comment.domain.Comment;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class kkFreemarker {

	Configuration cfg = null;
	public kkFreemarker(ServletContext parent,String child){
		cfg = new Configuration();
		cfg.setServletContextForTemplateLoading(parent, child);//获取配置路径的位置
	}

	
	
	
	

	/**
	 * 6.管理员获取所有留言列表
	 */
	public void creatAdminGetCommentList(HttpServletRequest request,
			HttpServletResponse response, String modePath) {
		Template t = null ;
		try {
			t = cfg.getTemplate(modePath,response.getCharacterEncoding());//指定一下编码
		} catch (IOException e) {
			System.out.println("creatAdminGetCommentList方法失败");
			e.printStackTrace();
		}
		
		String loginName = (String) request.getSession().getAttribute("loginName");
		
		if(loginName == null || loginName.length() <= 0){
			try {
				response.getWriter().println("请先登录，别瞎整，搞死你。。。。");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		@SuppressWarnings("unchecked")
		Page<Comment> page = (Page<Comment>) request.getAttribute("page");
		page.setLoginName(loginName);
		try {
			t.process(page, response.getWriter());
		} catch (TemplateException | IOException e) {
			System.out.println("t.process方法失败6");
			e.printStackTrace();
		}
		
		
	}

	
	/**************下面是**************/	
	
	
	
	/**
	 * 5.创建管理员编辑具体文章的页面内容
	 */
	public void creatAdminGetArticleOne(HttpServletRequest request,
			HttpServletResponse response, String modePath) throws IOException {
		Template t = null;
		try {
			t = cfg.getTemplate(modePath,response.getCharacterEncoding());//指定一下编码
		} catch (IOException e1) {
			System.out.println("creatAdminGetArticleOne方法失败");
			e1.printStackTrace();
		}
		
		String loginName = (String) request.getSession().getAttribute("loginName");
		
		if(loginName == null || loginName.length() <= 0 ){
			response.getWriter().println("请先登录，别瞎整，搞死你。。。。");
			return;
		}
		
		Page<Article> page = (Page<Article>) request.getAttribute("page");
		page.setLoginName(loginName);
		try {
			t.process(page, response.getWriter());
		} catch (TemplateException e) {
			System.out.println("t.process方法失败5");
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 4.创建管理员编辑文章列表的页面内容
	 */
	public void creatAdminGetArticleList(HttpServletRequest request,
			HttpServletResponse response, String modePath) {
		Template t = null ;
		try {
			t = cfg.getTemplate(modePath,response.getCharacterEncoding());//获取模板内容，并制定编码
		} catch (Exception e) {
			System.out.println("创建creatAdminUpdataArticle模板失败！");
			e.printStackTrace();
		}
		
		String loginName = (String) request.getSession().getAttribute("loginName");
		
		if(loginName == null || loginName.length() <= 0){
			try {
				response.getWriter().println("请先登录，别瞎整，搞死你。。。。");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		/*取出来数据库查询到的内容*/
		Page<Article> page = (Page<Article>) request.getAttribute("page");
		page.setLoginName(loginName);
		
		try {
			//把request的内容加载到模板里面，并写到response的printwrite流中
			t.process(page, response.getWriter());
		} catch (Exception e) {
			System.out.println("t.process方法失败4");
			e.printStackTrace();
		}
		
		
	}	
	
	/**
	 *   3.这里是创建管理员写新文章的界面内容，用于写新的文章
	 */
	public void creatAdmin(HttpServletRequest req,HttpServletResponse resp ,String modePath){
		Template t = null;
		try {
			t = cfg.getTemplate(modePath,resp.getCharacterEncoding());//指定一下编码
		} catch (IOException e) {
			System.out.println("获取template实例时报");
			e.printStackTrace();
		}
		String loginName = (String) req.getSession().getAttribute("loginName");
		
		if(loginName == null || loginName.length() <= 0){
			try {
				resp.getWriter().println("请先登录，别瞎整，搞死你。。。。");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Map<String,Object> map = new HashMap<>();
		map.put("loginName", loginName);
		
		try {
			t.process(map, resp.getWriter());
		} catch (TemplateException | IOException e) {
			System.out.println("t.process()方法出错1");
			e.printStackTrace();
		}
		
	}
	
	/**
	 *   2.这里是创建用户浏览界面的最新一篇文章对应的html，
	 *   	-->所有的文章在创建时生成，用户访问直接返回该静态的html页面
	 *     	-->在管理员创建最新文章时生成
	 */
	
	public void creatLastArticle(HttpServletRequest req,HttpServletResponse resp ,String modePath) throws IOException{
		Template t = null;
		try {
			t = cfg.getTemplate(modePath,resp.getCharacterEncoding());//指定一下编码
		} catch (IOException e) {
			System.out.println("获取template实例时报");
			e.printStackTrace();
		}
		
		@SuppressWarnings("unchecked")
		Page<Article> page = (Page<Article>) req.getAttribute("page");
		String fileName = page.getList().get(0).getArt_id()+".html";
		System.out.println("fileName:" + fileName);
		String realPath = "C:\\java\\tomcat\\apache-tomcat-9.0.1\\webapps\\blog\\articles/" + fileName;
		File file = new File(realPath); 
		
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),"utf-8");
		try {
			t.process(page,outputStreamWriter);
			outputStreamWriter.close();
		} catch (TemplateException | IOException e) {
			System.out.println("t.process()方法出错2");
			e.printStackTrace();
		}
		
	}


		/**
		 *   1.这里是创建用户浏览界面的文章列表的缩略内容显示
		 */
	public void creatPageList(HttpServletRequest req, HttpServletResponse resp,
			String modePath) throws IOException {
		Template t = null;
		try {
			t = cfg.getTemplate(modePath,resp.getCharacterEncoding());//指定一下编码
		} catch (IOException e) {
			System.out.println("获取template实例失败2");
			e.printStackTrace();
		}
		
		Page<Article> page = (Page<Article>) req.getAttribute("page");
//		String fileName = "page_"+page.getPage_now()+".html";
//		String contextPathTemp = req.getServletContext().getRealPath("/");
//		int indexTemp = contextPathTemp.indexOf("workplace");
//		contextPathTemp = contextPathTemp.substring(0,indexTemp+9 );
//		String realPath = contextPathTemp + req.getContextPath()+"/WebContent/articles/" + fileName;
		/*这里是动态的显示所以不需要创建html*/
		try {
			t.process(page,resp.getWriter());
		} catch (TemplateException | IOException e) {
			System.out.println("t.process()方法出错3");
			e.printStackTrace();
		}
	}




}
