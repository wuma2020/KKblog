<!DOCTYPE html><html>
<meta charset="UTF-8">
	<link rel="shortcut icon" href="https://pandao.github.io/editor.md/favicon.ico" type="image/x-icon" />
	<link href="/blog/js_css/style.css" rel="stylesheet" />
	<link href="/blog/js_css/editormd.css" rel="stylesheet" />
	<link href="/blog/js_css/from.css" rel="stylesheet" />
	<link href="/blog/js_css/bootstrap.min.css" rel="stylesheet">
	
	<script src="/blog/js_css/jquery.min.js"></script>
	<script src="/blog/js_css/bootstrap.min.js"></script>
	<script src="/blog/js_css/editormd.min.js"></script>
	<body style="color: olive; background-color: silver;">
	<!--头部分-->
		<div class="container">
			<div class="row clearfix">
				<div class="col-md-12 column">
				<blockquote>
				<p>
					哎呦歪，花了好多时间的呢。。。。
				</p> <small>是谁 敲打<cite>我床</cite></small>
			</blockquote>
				</div>
			</div>
	<!--中间大块-->
			<div class="row clearfix">
	<!--中间左侧-->
				<div class="col-md-2 column" >
					<div class="list-group">
					  <a href="#" class="list-group-item list-group-item-success">文章</a>
					  <a href="#" class="list-group-item list-group-item-info">相册</a>
					  <a href="/blog/adminServlet?method=getAllComments" class="list-group-item list-group-item-warning">留言</a>
					  <a href="#" class="list-group-item list-group-item-danger">K·Song</a>
					  <a href="#" class="list-group-item list-group-item-success">小明吃饭</a>
					  <a href="#" class="list-group-item list-group-item-info">小明吃饭</a>
					  <a href="#" class="list-group-item list-group-item-warning">小明吃饭</a>
					  <a href="#" class="list-group-item list-group-item-danger">小明吃饭</a>
					</div>
				
				</div>	
	<!--中间左侧结束-->
	<!--中间右侧开始-->	
	
	<script type="text/javascript" >
	
		//这里返回写文章界面
		function writeArticle(){
			$(location).attr("href","/blog/adminServlet?method=getArticle");
		};
		//这里写编辑的函数
		function updataOneArticle(art_id){
			$(location).attr("href","/blog/adminServlet?method=updataOneArticle&art_id="+art_id);
		}
		//这里写删除的函数
		function deleteOneArticle(art_id){
			$(location).attr("href","/blog/adminServlet?method=deletOneArticle&art_id="+art_id);
		}
		
		
		
	</script>
	
		<div class="col-md-10 column">
					<!--分页卡-->
               <div class="tabbable" id="tabs-247145"> <!-- Only required for left/right tabs -->
                  <ul class="nav nav-tabs">
                    <li class=""><a href="#panel-700499" data-toggle="tab" contenteditable="false" onclick="return writeArticle();">写文章</a></li>
                    <li class=""><a href="#panel-736245" data-toggle="tab" contenteditable="false">编辑文章</a></li>
                    <li class=""><div align="right">
                    	  <button type="button" class="btn btn-success" onclick="return saveHTMLAndMarkdown();" >保存</button>
						  <button type="button" class="btn btn-danger">分割线之前内容为文章缩略内容,务必用分割线标识</button>
						  <button type="button" class="btn btn-info"  >用户：${loginName}</button>
                    </div></li>
                 </ul>
        
                  
				          <div class="tab-content">
				            <div class="tab-pane" id="panel-700499"  >
						              <# --在这里写 写文章的内容.  每个分块都分开，所以这里现在是空的，给一个点击事件，
						              		如果点击这返回到写文章的页面
						              -->
				           
				             </div>
				             
				             
		 <#-- 这里写文章的编辑和删除的功能
		 		^^获取所有文章的内容，在这里只显示文章标题和  两个超链接 1.编辑 2.删除
		 				^^1.编辑：从数据库获取所有的文章，并且只查id，title，等字段，不需要全部都查询出来，然后用于显示
		 					^^1.1当点击编辑某一篇文章后再根据文章id查询文章的对应的字段，比如，title，id，mark内容，type等需要修改的内容
		 					^^1.2把获取的信息再回填到写文章的div（就是上一个div的form中）
	 					^^2.1删除：直接根据该id来删除该文章
		 -->
		 
				            <div class="tab-pane active" id="panel-736245" >
				            <#-- 这是freemarker的注释，你看不到 -->
				            <#-- 这里要循环遍历 -->
				            <#-- 换颜色显示一下 -->
			            <#list list as article>
			            
			            	<#if article?index % 4 == 0 >
				            <div class="alert alert-success" role="alert" align="left"  ><cite>Title:</cite> ${article.art_title}
					          		 <button type="button" class="btn btn-sm btn-success" style="right:80px;position: absolute;"
							         onclick="return updataOneArticle(${article.art_id});" >编辑</button>
							 		 <button type="button" class="btn btn-sm btn-danger" style="right: 30px;position: absolute;" 
							 		 onclick="return deleteOneArticle(${article.art_id});" >删除</button>
				           	 </div>
				           
				          
				            <#elseif  article?index % 4 == 1 >
							<div class="alert alert-info" role="alert" align="left" ><span><cite>Title:</cite></span>  ${article.art_title}
 									 <button type="button" class="btn btn-sm btn-success" style="right:80px;position: absolute;"
							         onclick="return updataOneArticle(${article.art_id});" >编辑</button>
							 		 <button type="button" class="btn btn-sm btn-danger" style="right: 30px;position: absolute;" 
							 		 onclick="return deleteOneArticle(${article.art_id});" >删除</button>
							</div>
				            <#elseif article?index % 4 == 2 >
							<div class="alert alert-warning" role="alert" align="left" ><span><cite>Title:</cite></span>  ${article.art_title}
									 <button type="button" class="btn btn-sm btn-success" style="right:80px;position: absolute;"
							         onclick="return updataOneArticle(${article.art_id});" >编辑</button>
							 		 <button type="button" class="btn btn-sm btn-danger" style="right: 30px;position: absolute;" 
							 		 onclick="return deleteOneArticle(${article.art_id});" >删除</button>
							 </div>
				            <#else>
							<div class="alert alert-danger" role="alert" align="left" ><span><cite>Title:</cite></span>  ${article.art_title}
									 <button type="button" class="btn btn-sm btn-success" style="right:80px;position: absolute;"
							         onclick="return updataOneArticle(${article.art_id});" >编辑</button>
							 		 <button type="button" class="btn btn-sm btn-danger" style="right: 30px;position: absolute;" 
							 		 onclick="return deleteOneArticle(${article.art_id});" >删除</button>
							 </div>
							</#if>
							
						</#list>
							
				            </div>
				            
				            <!--编辑peanl结束-->
		            		</div>
		        	</div>
			</div>
		</div>
		</div>
	<!--中间右侧结束-->	
	
	</div>
	
	</body>

</html>
