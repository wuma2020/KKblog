package blog.mkk.commonUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CreateStaticHtml {

	public void create
	(HttpServletRequest req,HttpServletResponse resp,ServletContext servletContext,String fileName,String redirectPath,String filePath,String JspPath) throws ServletException, IOException{
	/*这里执行创建html页面的任务*/
		
		//1.设置一下编码
		resp.setContentType("text/html;charset=UTF-8");
		//获取分发器
		RequestDispatcher rd = servletContext.getRequestDispatcher(JspPath);//获取jsp资源
		
		//创建bytearrayoutputstream和ServletOutputStream输出流
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();//可以从servletoutputstram中获取字节数据
		//servletoutputstram是字节输出流
		ServletOutputStream servletOutputStream = new ServletOutputStream() {//可以从httpservletresponse中获取数据
			
			@Override
			public void write(int b) throws IOException {
				// TODO Auto-generated method stub
				arrayOutputStream.write(b);
			}
			public void write(byte[] b,int off,int len) throws IOException {
				// TODO Auto-generated method stub
				arrayOutputStream.write(b, off, len);
			}
			@Override
			public void setWriteListener(WriteListener listener) {
				// TODO Auto-generated method stub
			}
			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}
		};
		//字符输出流需要一个putputstreadwrite，然后再
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(arrayOutputStream));
		
		//现在获取httpresponse的内容
		HttpServletResponse httpServletResponse = new HttpServletResponseWrapper(resp){
			//覆盖了HttpServletResponseWrapper父类的ServletResponseWrapper的getOutputStream（）和 getWriter()方法，
			//目的是response写到servletOutputStream和printWriter中
			 public ServletOutputStream getOutputStream() throws IOException {
			        return servletOutputStream;//把response中的内容全部写到了servletOutputStream中，字节输出流
			    }
			    public PrintWriter getWriter() throws IOException {
			        return printWriter;//把response中的内容全部写到了servletOutputStream中，字符输出流
			    }
			
		};
		rd.include(req, httpServletResponse);//发送结果
		//不能同时使用printWriter和servletOutputStream这两个方法相互排斥，只能调用其一，如果要用，则要在换方法之前调用flush(),将缓冲区数据冲掉。
		printWriter.flush();//把字符流写到了对应的jsp
		
		/*用字节输出流arrayOutputStream来创建一个html，并完成转发*/
		File file = new  File(filePath);
		/*
		 * FileOutputStream(File file)
		 * 创建文件输出流以写入由指定File对象表示的文件。
		 * FileOutputStream用于写入诸如图像数据的原始字节流。对于写入字符流，请考虑使用 FileWriter。
		 * */
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		arrayOutputStream.writeTo(fileOutputStream);//把arrayOutputStream里面的字节数据，写到文件输出流fileOutputStream中
		fileOutputStream.close();//写完文件后关闭文件输出流
//		System.out.println(redirectPath);
		
		/*不知道为什么这里的resp和httpServletResponse用来重定向都不能成功,,,,过了N  hours，有神奇的可以了*/
		 resp.sendRedirect(redirectPath);
		//https://www.javatpoint.com/requestdispatcher-in-servlet关于include的用法

		/*
		 * httpServletResponse用了response作为参数构建，所以httpServletResponse包含response的所有信息
		 * 又因为RequestDispatcher.include(req,httpServletResponse),
		 * */
		
	}
	
}
