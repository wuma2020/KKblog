package blog.mkk.article.web.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blog.mkk.article.doamin.Article;
import blog.mkk.article.service.ArticleService;
import blog.mkk.commonUtils.Page;
import blog.mkk.commonUtils.kkFreemarker;

/**
 * Servlet implementation class ArticleServlet
 */
public class ArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArticleService aerticleService  = new ArticleService();
	
    public ArticleServlet() {
        super();
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	resp.setCharacterEncoding("utf-8");
    	req.setCharacterEncoding("utf-8");
    	doGet(req,resp);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	resp.setCharacterEncoding("utf-8");
    	req.setCharacterEncoding("utf-8");
    	String method = req.getParameter("method"); 
    	if("getArticleOne".equals(method)){
    		try {
				getArticleOne(req,resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}else if("getArticle".equals(method)){
    		try {
				getArticle(req,resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}else{
    		
    	}
    	
    	
    }
    
    
    /**
     * 2.获取具体的某篇文章
     * 		-->	根据art_id
     * 			public Page<Article> findByArt_id(Object o,Object art_id) throws SQLException{//o代表是< 还是> 还是=
     *			这样不好传<>=，用0,1,2来代替
     * 			Operators操作符
     */
    public void getArticleOne(HttpServletRequest req,HttpServletResponse resp ) throws IOException, ServletException, SQLException{
    	req.setCharacterEncoding("utf-8");
    	resp.setCharacterEncoding("utf-8");
    	
    	
    	String art_id = req.getParameter("art_id");
    	String oper = req.getParameter("oper");
    	if(art_id != null && oper == null ){
    		/*
    		 * 2.1先判断书否该文件已经存在
    		 * */
    		
    		String fileName = art_id + ".html"; 
    		String filePath = "C:\\java\\tomcat\\apache-tomcat-9.0.1\\webapps\\blog\\articles/" + fileName;
    		
    		String redirctPath = "/blog/articles/" + fileName; 
    		
    		File file = new File(filePath);
    			if(file.exists()){
    				System.out.println("文件存在，现在跳转");
    				System.out.println(redirctPath);
    				resp.sendRedirect(redirctPath);
    				return;
    		}
    		ServletContext servletContext =  getServletContext();
    		Page<Article> page = null ;
    		try {
    			page = aerticleService.findByArt_id( "1",art_id);
			} catch (Exception e) {
				 resp.getWriter().print("没有这篇文章，请返回");
				 return;
			}
    	
    		
    	}else if(art_id != null && oper != null){
    		
    		if(art_id.equals("1") && oper.equals("p")){
    			resp.getWriter().print("上一篇文章已经是第一篇文章了");
    			return;
    		}
    		Page<Article>   page = null;
    		int	 _art_id = 0 ;
    		try {
				
    			page = aerticleService.findByArt_id( oper,art_id);
    				 _art_id = page.getList().get(0).getArt_id();
			} catch (Exception e) {//文章最后一篇，且查询为下一篇，则直接返回
				 resp.getWriter().print("上一篇文章已经是最后一篇文章了");
				 return;
			}
    		String fileName = _art_id + ".html"; 
    		String filePath = "C:\\java\\tomcat\\apache-tomcat-9.0.1\\webapps\\blog\\articles/" + fileName;
    		String redirctPath = "\\blog\\articles/" + fileName;
    		File file = new File(filePath);
    			if(file.exists()){
    				System.out.println("文件存在，现在跳转");
    				resp.sendRedirect(redirctPath);
    				return;
    		}

    			/*
        		 *   下面的方法用不着了，因为每次写文章的时候都用模板生成好了新的html文件，这样更加方便访问，不需要用户请求然后来访问 
        		 */
        		
//    		ServletContext servletContext =  getServletContext();
//    		String url = "ArticleServlet?method=getArticleOne";
//    		page.setUrl(url);
//    		req.setAttribute("page",page);
//    		
//    		/*文件不存在，现在创建*/
//    		new CreateStaticHtml().create(req, resp, servletContext, fileName, redirctPath, filePath, "/articles/article.jsp");
    		
    	}
    
    	
    	
    	
    }
    
    
    
    /**
     * 1.获取某页article《一整页，5篇》
     * 		-->根据page_now
     * */
    public void getArticle(HttpServletRequest req,HttpServletResponse resp) throws IOException, SQLException, ServletException{
    	String page_now = req.getParameter("page_now");
    	 if(page_now!=null){//如果page_now不为空
    		 
    		 /*
    		  * 创建html路径，如果html存在，则直接转发，如果不存在则创建html文件完成转发
    		  * */
    		 String fileName = "page_"+page_now+".html";
    		 String temp =  this.getServletContext().getRealPath("/");
    		 
    		 int index1 = temp.indexOf("workplace");
    		 //D:\mkeclipse\workplace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\mkk\article_100.html
    		String filePathTemp =  temp.substring(0,index1+10)+"blog/WebContent/";//获取项目的路径D:\mkeclipse\workplace\
    		 
    		 String filePath =filePathTemp+"articles/"+fileName;//.html文件保存的路径filePath = D:\mkeclipse\workplace\blog\WebContent\articles\fileName
//    		 int tempIndex = filePath.indexOf("blog");
//    		 //这个跳转的路径 获取的有问题
//    		 String rd1 = filePath.substring(tempIndex-1);//\blog\WebContent\articles\fileName现在要把\WebContent去掉
//    		 String redirectPath = filePath.substring(tempIndex-1).replace("\\WebContent","");//\blog\articles\fileName
    		 
    		 /*这里截取到\articles\fileName，然后再前面加上项目名即可（更方便点）*/
    		 int tempIndex = filePath.indexOf("articles");
    		 String redirectPath = "/blog" + filePath.substring(tempIndex-1);
    		 
    		 /*这里是经常会变，所以需要动态的添加，不能写死<比如写成html>*/
//    		 //判断文件是否存在
//    		 File file = new File(filePath);
//    		 if(file.exists()){//存在则完成转发
////    			 System.out.println("有文件，现在跳转");
//    			 resp.sendRedirect(redirectPath);
//    			 return;
//    		 }
//    		 System.out.println("没有文件，现在创建");
    		 /*没有.html文件，创建文件,创建HTML之前先把查询到的结果放到request中，传给创建的方法做参数*/
    		 int page_now_int = Integer.parseInt(page_now);
    		 Page<Article> page =  aerticleService.findByPage_noe(page_now_int);
    		 
    		 /*这里要设置一下url到page里面，，ArticleServlet?method=getArticle&page_now=1*/
    		 String url = "ArticleServlet?method=getArticle";
    		 page.setUrl(url);
    		 req.setAttribute("page", page);
    		 
    		 /*这里开始用freemarker来做*/
    		 
    		 new kkFreemarker(getServletContext(), "articles/").creatPageList(req,resp,"pagelist.ftl");
    		 
    		 
    		 
    		 //调用创建函数，并在创建函数中完成转发
//    		 new CreateStaticHtml().create(req,resp,getServletContext(),fileName,redirectPath,filePath,"/articles/page.jsp");
    		 
    	 }
    	
    	
    }
    

    
    
}
