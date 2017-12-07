package blog.mkk.admin.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import blog.mkk.comment.domain.Comment;
import blog.mkk.commonUtils.Image;
import blog.mkk.commonUtils.Page;
import blog.mkk.commonUtils.kkFreemarker;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class adminServlet
 */
public class adminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AdminService admin = new AdminService();
	public adminServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		
		switch(method){
			case "getArticle":
					getArticle(request,response);
					break;
			case "uploadImage" :
					uploadImage(request,response);
					break;
			case "getAllUpdataArticle" :
					getAllUpdataArticle(request,response);
					break;
			case "deletOneArticle":
				deletOneArticle(request,response);
				break;
			case "getAllComments":
				getAllComments(request,response);
				break;
			case "deleteComment" :
				deleteComment(request,response);
				break;
			case "saveArticle" : 
				try {
					saveArticle(request,response);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case "updataOneArticle" :
				try {
					updataOneArticle(request,response);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
		}
		
		
	}
	/**
	 *  删除指定com_id的留言
	 */
	private void deleteComment(HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		String com_id = request.getParameter("com_id");
		admin.deleteComment(com_id);
//		response.getWriter().print("javasciprt:alert('删除成功');");
		getAllComments(request,response);
		
	}


	/**
	 * -->2.1获取所有留言内容
	 */
	private void getAllComments(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		Page<Comment> page =  admin.getAllComments();
		request.setAttribute("page",page);
		new kkFreemarker(getServletContext(), "admin/comment/").creatAdminGetCommentList(request, response, "allComments.ftl");
	}

		/****************上面是管理员留言功能*****************/
	/**
	 * -->1.3删除文章
	 */
	private void deletOneArticle(HttpServletRequest request,
			HttpServletResponse response) {
		String art_id = request.getParameter("art_id");
		try {
			admin.deletOneArticle(art_id);
		} catch (SQLException e) {
			System.out.println("根据art_id删除文章失败");
			e.printStackTrace();
		}
		
		getAllUpdataArticle(request,response);//从新查询一遍，并获取文章列表
		
	}


	/*
	 * -->1.2修改文章
	 * 		-->1.2.2：根据传进来的art_id来获取该文章的详细信息，然后返回到写文章的界面，把相应的内容回写到相应的位置里
	 * */
	private void updataOneArticle(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		String art_id = request.getParameter("art_id");
		Page<Article> page = admin.findByArt_id(art_id);
		request.setAttribute("page",page);
		new kkFreemarker(getServletContext(), "admin/article/").creatAdminGetArticleOne(request, response, "updataArticleOne.ftl");
	}


	/*
	 * -->1.2修改文章
	 * 		-->1.2.1：获取所有的文章的列表，只需要获取文章的id和title即可
	 * */
	
	private void getAllUpdataArticle(HttpServletRequest request,
			HttpServletResponse response) {
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
		
		
//		String contextPathTemp = request.getServletContext().getRealPath("/");
//		int indexTemp = contextPathTemp.indexOf("workplace");
//		contextPathTemp = contextPathTemp.substring(0,indexTemp+9 );
		String path = "C:\\java\\tomcat\\apache-tomcat-9.0.1\\webapps\\blog\\image/" ;
		
		System.out.println("path:"+path);
		
		ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
		
		//开始遍历request里面的内容
		try {
			List<FileItem > items = upload.parseRequest(request);
			for(FileItem item : items){
				if(!(item.isFormField())){
					String value = item.getName();
					int index = value.lastIndexOf("/");
					String fileName = value.substring(index+1);
					request.setAttribute("fileName", fileName);
					String url = "/blog/image/" + fileName ;
					Image image = new Image();
					image.setUrl(url);
					image.addImage(url);
					InputStream input =  item.getInputStream();
					byte[] b = new byte[1024];
					OutputStream out = new FileOutputStream(new File(path+fileName));
					int length = 0;
					while((length = input.read(b)) != -1){
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
			HttpServletResponse response) throws IOException, SQLException {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
	
		
		String html = request.getParameter("test-editormd-html-code");
		System.out.println(html);
		String mark = request.getParameter("test-editormd-markdown-doc");
		String temptype = request.getParameter("type");
		int type = Integer.parseInt(temptype);
		String title = request.getParameter("title");
		String author = (String) request.getSession().getAttribute("loginName");//以后设置的之后再用这个
		
		Article newArticle =  new Article();
		newArticle.setArt_author(author);
		newArticle.setArt_title(title);
		newArticle.setArt_content(html);
		newArticle.setArt_mark(mark);
		newArticle.setArt_type(type);
		
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String allTime = format.format(date);
		newArticle.setArt_date(allTime);
		
		/*判断文章是否为更新*/
		String art_id = request.getParameter("art_id");
		if(art_id != null && art_id.length() > 0){//说明是更新某一篇文章
			int id = Integer.parseInt(art_id);
			newArticle.setArt_id(id);
			 admin.updataArticleByArt_id(newArticle);//这里更新成功后，应该重新生成静态的html文件
			 
			 ArticleDao artDao = new ArticleDao();
		     Page<Article> page = artDao.findByArt_id(art_id);	
		 	 String url = "ArticleServlet?method=getArticleOne";
			 page.setUrl(url);
			 request.setAttribute("page", page);
				/*这里直接创建一个该文章的静态的HTML，然后方便访问*/
			 new kkFreemarker(getServletContext(), "articles/").creatLastArticle(request, response, "article_mode.ftl");
			 
			 return ;
		}
		
		
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
	
		/**
		 * login的校验工作
		 */
		String loginName = request.getParameter("login");
		String password = request.getParameter("pwd");
		if(loginName != null && password != null && loginName.length()> 0 && password.length() >0 ){
			System.out.println(loginName + "||" + password);
			if(loginName.equals("mkk") && password.equals("pwd") 
					|| loginName.equals("myy") && password.equals("1996816")
					|| loginName.equals("Mr.WY") && password.equals("1132782480")
					){//先写死，用于测试
				request.getSession().setAttribute("loginName",loginName);
				//getArticle(request,response);
				Map<String ,String> map = new HashMap<>();
				map.put("pass","1");
				JSONObject json = JSONObject.fromObject(map);
				response.getWriter().println(json);
				
				return;
			}else{
				Map<String ,String> map = new HashMap<>();
				map.put("pass","0");
				JSONObject json = JSONObject.fromObject(map);
				response.getWriter().println(json);
				return;
			}
		}
		
		
		doGet(request, response);

	}

}
