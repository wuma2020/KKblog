package TestServlet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupTest {

	
	/*获取网上的HTML文件,返回该文件的绝对路径*/
	
	public void getPersonKSONGHtml() throws IOException{
		BufferedReader bf = null ;
			String urlpath = "https://node.kg.qq.com/play?s=DDoFXODscw3edDUD&g_f=personal";
			String resultHtml = "";
		try {
			URL url = new URL(urlpath);//创建对应的url
			HttpURLConnection connection =   (HttpURLConnection) url.openConnection();//获取连接，返回HttpURLConnection 对象。
			//设置一些普通的头字段
			connection.setRequestProperty("accept", "*/*");  
			connection.setRequestProperty("connection", "Keep-Alive");  
			connection.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)"); 
			//connection.setConnectTimeout(timeout);
			//建立真正的连接
			connection.connect();
			bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			//byte[] b = new byte[1024];//因为只是获取html的内容，所以还是用字符来读，等后面用到下载歌曲，再用byte
			String readLine = "" ; 
			while((readLine = bf.readLine()) != null ){//一直读
				resultHtml = resultHtml + readLine + "\n" ;
			}
			
			
		} catch (MalformedURLException e) {
			System.out.println("获取连接失败");
			e.printStackTrace();
		}finally{
			if(bf != null ){
				bf.close();
				
			}
		}
		System.out.println(resultHtml);
		
//		int index = urlpath.indexOf("=");
//		String fileName = urlpath.substring(index+1);
//		String filePath =  "C:/KSONG/"+fileName+".html";
//		File file = new File(filePath);
//		if(!file.exists()){//如果不存在，则创路径和文件
//			file.getParentFile().mkdir();
//			file.createNewFile();
//		}
//		PrintWriter pw = new PrintWriter(new FileWriter(file));
//		pw.write(resultHtml);
//		pw.close();
//		return filePath;
	}
		
	/*写一个获取字节流并储存的方法*/
	public  static void saveSong(String url,String name){
		BufferedInputStream in = null ;
		try {
			URL urlcon = new URL(url);
			HttpURLConnection connection =  (HttpURLConnection) urlcon.openConnection();
			connection.connect();
			in = new BufferedInputStream(connection.getInputStream()); 
			System.out.println("获取字节流"+name);
			byte[] b = new byte[1024];
			int length = 0 ; 
			
			/*准备一下文件流的工具*/
//			String filePath =  "C:/KSONG/" + name + ".mp3";
//			File f = new File(filePath);
//			if(!f.exists()){
//				f.getParentFile().mkdir();
//				f.createNewFile();
//			}
//			FileOutputStream fileOut = new FileOutputStream(f);
//			System.out.println("读取字节流");
//			while((length = in.read(b)) != -1){
//				fileOut.write(b);
//			}
//			fileOut.close();
//			in.close();
//			connection.disconnect();
//			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	
	public static void main(String[] args) throws IOException {

		
		String urlpath = "https://node.kg.qq.com/personal?uid=639c9a81202d3f8b";
		String resultHtml = "";
		List<String> psonglist = new ArrayList<String>();//这里存放某个人的某首歌的链接-->根据这个链接获取具体歌曲文件
		
		
		JsoupTest jsoup =  new JsoupTest();
		
//		String path = jsoup.getPersonKSONGHtml(urlpath, resultHtml);
		
		Document doc =  Jsoup.connect(urlpath).timeout(30000).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
                //加上cookie信息
                .cookie("auth", "token").get();//这么强的jar包真的是牛逼，有空研究一下源码
//		System.out.println(d.toString());
		Elements elements =   doc.select("li.mod_playlist__item");
//		System.out.println(elements.toString());
		/* 这里得遍历elements元素
		 */
		for(Element e : elements){
			String e_a_href = e.select("a.mod_playlist__cover").attr("href");
//			System.out.println(e_a_href);//这里也是正确的
//			psonglist.add(e_a_href);
			//完全可以试着直接写获取该文件的内容啊
			
			URL pa = new URL(e_a_href);
			HttpURLConnection httpc =    (HttpURLConnection) pa.openConnection();
			httpc.connect();
			
			httpc.getInputStream();
			
			Document docOneSong =  Jsoup.connect(e_a_href).get();
			/*直接定位到audio感觉不行啊
			 * jsoup不能抓取js生成的内容所以这里自己后去html然后抓取
			 * */
			Elements elementone = docOneSong.select("div#mvplayer.play_swf");
			System.out.println(elementone.toString());
			
//			String name = docOneSong.select("#player").attr("title");
//			String songRealPath = docOneSong.select("#player").attr("src");
//			System.out.println(name + songRealPath);
//			saveSong(songRealPath,name);
		}
		
		
		
		
}
}
