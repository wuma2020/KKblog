package blog.mkk.comment.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blog.mkk.comment.domain.Comment;
import blog.mkk.comment.service.CommentService;
import blog.mkk.commonUtils.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;

/**
 * Servlet implementation class CommentServlet
 */
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	CommentService commentService = new CommentService(); 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		/*
		 * 这里调用相应的方法来解决问题
		 * */
		String method1 = request.getParameter("method");
		if(method1 != null && method1.equals("getComment") ){
			try {
				getComment(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}else if(method1 != null && method1.equals("updataComment")){
			try {
				updataComment(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	
	}

	/**
	 * 2.更新留言的内容
	 * @throws SQLException 
	 */
	 public void updataComment(HttpServletRequest request,HttpServletResponse response) throws SQLException{
		 //这里开始获取comment的内容
		 //http://localhost:8080/blog/CommentServlet?method=updataComment&name=srg&com_content=sdgsgsdgsd&com_pid=no&com_type=3
		 String com_pid = request.getParameter("com_pid");
		 String com_content = request.getParameter("com_content");
		 String com_name = request.getParameter("name");
		 
		 if(com_content == "" ||com_name == "" ){
			 return;
		 }
		 
		 String com_type = request.getParameter("com_type");
		 System.out.println("com_content:"+com_content);
		 Comment m = new Comment();
		 if(com_pid.equals("no")){//判断pid是否为“no”,如果是，这该内容为新的留言，并不是回复的内容
			 m.setCom_pid("");
		 }else{
			 m.setCom_pid(com_pid);
		 }
		 m.setCom_content(com_content);
		 
	 	Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        int month=cal.get(Calendar.MONTH)+1;//获取月份 
        int day=cal.get(Calendar.DATE);//获取日 
        int hour=cal.get(Calendar.HOUR);//小时 
        int minute=cal.get(Calendar.MINUTE);//分            
        int second=cal.get(Calendar.SECOND);//秒 
        int r = cal.get(Calendar.AM_PM);//询上午还是下午
        String AMorPM = "";
        if(r!=cal.get(Calendar.AM)){//如果上午
        	AMorPM = "AM";
        }else {
        	AMorPM = "PM";
        }
		 String date = year+"/"+month+"/"+day+"　　"+hour+":"+minute+":"+second+"　"+AMorPM;
		 m.setCom_date(date);
		 m.setCom_type(com_type);
		 m.setCom_name(com_name);
		 m.setArt_id("0");
		 
		 //更新
		 commentService.updataComment(m);
		 
	 }
	
	
	/**
	 * 1.获取评论内容
	 * @throws SQLException 
	 * @throws JSONException 
	 */
	protected void getComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String com_type = request.getParameter("com_type");
		String page_now = request.getParameter("page_now");
		
		Page<Comment> page = commentService.findByCom_typeAndPage_now(com_type, page_now);
		String url = "CommentServlet?method=getComment&com_type=3";
		page.setUrl(url);
		
		JSONArray json = JSONArray.fromObject(page);
		
		PrintWriter   pw = response.getWriter();
		json.write(pw);
		System.out.println(json.toString());
		pw.flush();
		pw.close();
	
		
		
		
		
		
		/*
		 * 现在用ajax来传送数据，暂时不需要新建该html文件了
		 */
//		if(com_type != null && page_now != null){
//			String fileName = page_now + ".html";
//			String path = this.getServletContext().getRealPath("/");
//			int index = path.indexOf("workplace");
//			String filePath = "";
//			if(com_type.equals("0")){
//				 filePath = path.substring(0, index+10) + "\\blog\\WebContent\\comment_tome/xiaoxue/" + fileName;
//			}else if(com_type.equals("1")){
//				 filePath = path.substring(0, index+10) + "\\blog\\WebContent\\comment_tome/middleschool/" + fileName;
//			}else if(com_type.equals("2")){
//				 filePath = path.substring(0, index+10) + "\\blog\\WebContent\\comment_tome/highschool/" + fileName;
//			}else if(com_type.equals("3")){
//				 filePath = path.substring(0, index+10) + "\\blog\\WebContent\\comment_tome/colleage/" + fileName;
//			}else{
//				 filePath = path.substring(0, index+10) + "\\blog\\WebContent\\comment_tome/others/" + fileName;
//			}
//			String redirectPath = "";
//			int redirect  = filePath.indexOf("/comment_tome");
//			redirectPath = "/blog" + filePath.substring(redirect);
//			File file = new File(filePath);
//			if(file.exists()){
//				//html文件已经存在，现在跳转
//				response.sendRedirect(redirectPath);
//				return;
//			}
//			//文件不存在现在创建
//			Page<Comment> page = commentService.findByCom_typeAndPage_now(com_type, page_now);
//			String url = "CommentServlet?method=getComment";
//			page.setUrl(url);
//			new CreateStaticHtml().create(request, response, getServletContext(), fileName, redirectPath, filePath, "comment_tome/comment.jsp");
			
//		}
	}
	
	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
