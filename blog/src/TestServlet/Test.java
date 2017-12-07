package TestServlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blog.mkk.comment.domain.Comment;
import blog.mkk.commonUtils.Page;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Test
 */
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Configuration config = null;
	/*1.初始化*/
	@SuppressWarnings("deprecation")
	
	public void init(){
		/*创建一个configuration对象*/
		config = new Configuration();
		/*指定配置文件的位置*/
		config.setServletContextForTemplateLoading(getServletContext(), "WEB-INF/HTMLmuban");
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter("name");
		if(name.equals("kk") ){
			kk(req,resp);
		}
	}
	private void kk(HttpServletRequest req, HttpServletResponse resp) {
		Template  t  = null ;
		try {
			t = config.getTemplate("Test.ftl");
		} catch (IOException e) {
			System.out.println("获取Test.ftl文件异常");
			e.printStackTrace();
		}
		List<Comment> list = new ArrayList<>();
		String id = req.getParameter("id");
//		/*假设这里根据id查询到了很多的留言对象的集合
//		 * 实际上，自己虚构一下
//		 * template只能穿map或者单个对象吧，数组不行
//		 */
		for(int i = 0; i <= 5 ; i++){
			Comment c = new Comment();
			c.setCom_id(i);
			c.setCom_name( "name"+"|"+i+"|"+id);
			list.add(c);
		}
		
		Page<Comment> page  = new Page<>();
		page.setList(list);
		page.setPage_size(3);
		
		/*把list的内容换成json格式*/
//		JSONArray jsonArray = JSONArray.fromObject(list);
		
		JSONObject json = JSONObject.fromObject(page);
		/*现在是向模板文件里面写数据*/
		
		try {
			 
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			PrintWriter pio = new PrintWriter(new OutputStreamWriter(b));
			pio = resp.getWriter();
			
			File file = new File(getServletContext().getRealPath("/")+"ba.html");
			System.out.println(getServletContext().getRealPath("/")+"ba.html");
			FileWriter fwite  = new FileWriter(file);
			FileWriter fwite1 = fwite;
			t.process(json, fwite1);
			fwite.close();
			fwite1.close();
		} catch (TemplateException e) {
			System.out.println("向文件写数据四百");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}



	protected void service1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/*创建数据模型*/
		Map<String ,Object> map  =  new HashMap<String, Object>();
		map.put("name", "asd");
		map.put("age", "12");
		map.put("sex", "man");
		
		Comment com = new Comment();
		com.setCom_date("asd");
		com.setCom_name("liuyanmakai ");
		/*获取模板文件*/
		Template temp = config.getTemplate("Test.ftl");
		/*设置编码*/
		response.setContentType("text/html;charset="+temp.getEncoding());
		/*获得response的输出流*/
		PrintWriter out = response.getWriter();
		/*接下来就是把response的内容写到temp中，前面一个参数map，就是传到对应的ftl文件里的数据*/
		try {
			temp.process(com, out);
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			System.out.println("处理template静态文件异常");
			e.printStackTrace();
		}
		
	}



}
