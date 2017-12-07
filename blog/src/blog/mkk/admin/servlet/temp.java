package blog.mkk.admin.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import blog.mkk.admin.service.AdminService;
import blog.mkk.article.dao.ArticleDao;
import blog.mkk.article.doamin.Article;
import blog.mkk.commonUtils.Image;
import blog.mkk.commonUtils.Page;
import blog.mkk.commonUtils.kkFreemarker;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class adminServlet
 */
public class temp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AdminService admin = new AdminService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		
//		switch(method){
//			case "getArticle":
//					getArticle(request,response);
//					System.out.println("switch1");
//					break;
//		}
		
		if(method.equals("getArticle")){//这个方法用于查询后台文章列表
			System.out.println("getArticle");
			getArticle(request,response);
		}else
			if(method.equals("saveArticle")){//这个方法用于新建文章或修改文章内容
				System.out.println("sav");
			saveArticle(request,response);
		}else if(method.equals("uploadImage")){
			uploadImage(request,response);
		}else if(method.equals("getAllUpdataArticle")){
			getAllUpdataArticle(request,response);
		}else{
			System.out.println("这里是else");
		}
	}
	
	
	
	/*
	 * -->1.2修改文章
	 * 		-->1.2.1：获取所有的文章的列表，只需要获取文章的id和title即可
	 * */
	
	private void getAllUpdataArticle(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("123");
		Page<Article> page = admin.getAllUpdataArticle();
		/*把page放到request中，用于后面的创建模板使用*/
		request.setAttribute("page",page);
		/*现在加载模板*/
		new kkFreemarker(getServletContext(), "admin/article/").creatAdminGetArticleList(request,response,"article_updata.ftl");
		
	}
	
	/*
	 * -->1.1.2对文章中上传的图片做处理    <先上传到指定的文件夹的路径并把图片的路径返还给浏览器用于回显>
	 * */
	
	private   void uploadImage(HttpServletRequest request,
			HttpServletResponse response) {
		 try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//这里写图片上传相关的代码
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();//获取磁盘条目路径
		
		
		String contextPathTemp = request.getServletContext().getRealPath("/");
		int indexTemp = contextPathTemp.indexOf("workplace");
		contextPathTemp = contextPathTemp.substring(0,indexTemp+9 );
		String path = contextPathTemp + request.getContextPath()+"/WebContent/image/" ;
		
		System.out.println("path:"+path);
		
		ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
		
		//开始遍历request里面的内容
		try {
			List<FileItem > items = upload.parseRequest(request);
			for(FileItem item : items){//遍历items
				if(!(item.isFormField())){//对非文本内容做处理（比如，视屏音乐图片等，二进制内容）
					String value = item.getName();
					int index = value.lastIndexOf("/");
					String fileName = value.substring(index+1);
					request.setAttribute("fileName", fileName);
					String url = "/blog/image/" + fileName ;
					Image image = new Image();
					image.setUrl(url);
					image.addImage(url);
					//现在开始写二进制流写到文件里
					InputStream input =  item.getInputStream();
					byte[] b = new byte[1024];
					OutputStream out = new FileOutputStream(new File(path+fileName));
					int length = 0;
					while((length = input.read(b)) != -1){//没有读取完全
						out.write(b);
					}
					input.close();
					out.close();
					JSONObject json = new JSONObject();
					json.put("success",1);
					json.put("message", "上传成功");
					json.put("url",url);
					response.getWriter().println(json.toString());
				}
			}
			
		} catch (Exception e) {
			//这里就是上传失败
			try {
				JSONObject json = new JSONObject();
				json.put("success",0);
				json.put("message", "上传失败");
				response.getWriter().println(json.toString());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/*
	 * -->1.1.1写文章用markdown来写
	 * */
	
	private void saveArticle(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String html = request.getParameter("test-editormd-html-code");
		System.out.println(html);
		String mark = request.getParameter("test-editormd-markdown-doc");
		String temptype = request.getParameter("type");
		int type = Integer.parseInt(temptype);
		String title = request.getParameter("title");
		String author = (String) request.getSession().getAttribute("author");//以后设置的之后再用这个
		
		Article newArticle =  new Article();
		newArticle.setArt_title(title);
		newArticle.setArt_author("马凯凯");
		newArticle.setArt_content(html);
		newArticle.setArt_mark(mark);
		newArticle.setArt_type(type);
		
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String allTime = format.format(date);
		newArticle.setArt_date(allTime);
		/*文章对象准备好了，项数据库中添加*/
		admin.insertArticleToDB(newArticle);
		
		/*把文章的内容再查询出来，因为art_id不知道，所以写一个方法用于查询最新的文章，也就是art-id最大的那个*/
		ArticleDao artDao = new ArticleDao();
		Page<Article> page = artDao.findLast();		
		request.setAttribute("page", page);
		/*这里直接创建一个该文章的静态的HTML，然后方便访问*/
		new kkFreemarker(getServletContext(), "articles/").creatLastArticle(request, response, "article_mode.ftl");
	
		
	}
	/*1.管理员文章的管理界面
	 * 		-->1.1 写文章       <现在的想法是，每写一篇文章，就生成一个html，然后别人访问的时候就直接把这个文件给他>
	 * 			-->1.1.1写文章用markdown来写
	 * 			-->1.1.2对文章中上传的图片做处理    <先上传到指定的文件夹的路径并把图片的路径返还给浏览器用于回显>
	 * 		-->1.2修改文章
	 * 		-->1.3删除文章
	 * "暂时就这些  "
	 * */
	
	/*用模板来生成相应的html*/
	private void getArticle(HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		System.out.println("SSSSSSS");
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new kkFreemarker(getServletContext(), "admin/").creatAdmin(request, response, "admin_index.ftl");;
		
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}
