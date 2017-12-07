<!DOCTYPE html><html>
<meta charset="UTF-8">
	<link href="/blog/js_css/style.css" rel="stylesheet" />
	<link href="/blog/js_css/editormd.css" rel="stylesheet" />
	<link href="/blog/js_css/from.css" rel="stylesheet" />
	<link href="/blog/js_css/bootstrap.min.css" rel="stylesheet">
	
	<script src="/blog/js_css/jquery.min.js"></script>
	<script src="/blog/js_css/bootstrap.min.js"></script>
	<script src="/blog/js_css/editormd.min.js"></script>
	<body style="color: olive; background-color: silver;">
	<!--这里是文章编辑回显的模板页面-->
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
	 <script type="text/javascript">

            $().ready(function() {
            	addEditormd();//加载editormd
            	
            	//getAllUpdataArticle();//获取修改的所有文章、这里不应该这么写，前台只需要发个请求，然后后台用模板来生成html返回
            });
            
            function addEditormd(){
				var testEditor;
                testEditor = editormd("test-editormd", {
                    width   : "100%",
                    height  : 640,
                    syncScrolling : "single",
                    path    : "/blog/js_css/lib/" ,
                    emoji:true,
                    saveHTMLToTextarea:true,
                    //下面是图片上传的设置
                    imageUpload : true,
                    imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
                    imageUploadURL : "/blog/adminServlet?method=uploadImage",
                    
                   
                });
               
                testEditor.getMarkdown();       // 获取 Markdown 源码，用于编辑功能
				testEditor.getHTML();           // 获取 Textarea 保存的 HTML 源码，用于直接显示
				
            }
            
            function getAllUpdataArticle() {
            	//这里直接向后台发送一个请求就行了
            	$(location).attr("href", "/blog/adminServlet?method=getAllUpdataArticle");
            	alert("已经向后台发送请求");
            
			}
            
            
           function saveHTMLAndMarkdown(){
           //这里写保存的功能< 把HTML和MARKDOWN内容都存进数据库>
           $("form").submit();
           alert("succec");
           };
           
           	//这里返回写文章界面
		function writeArticle(){
			$(location).attr("href","/blog/adminServlet?method=getArticle");
		};
            
		 </script>
					
		<div class="col-md-10 column">
					<!--分页卡-->
               <div class="tabbable" id="tabs-247145"> <!-- Only required for left/right tabs -->
                  <ul class="nav nav-tabs">
                    <li class=""><a href="#panel-700499" data-toggle="tab" contenteditable="false"  onclick="return writeArticle();">写文章</a></li>
                    <li class=""><a href="#panel-736245" data-toggle="tab" contenteditable="false" onclick="return getAllUpdataArticle();">编辑文章</a></li>
                    <li class=""><div align="right">
                    	  <button type="button" class="btn btn-success" onclick="return saveHTMLAndMarkdown();" >保存</button>
						  <button type="button" class="btn btn-danger"  >分割线之前内容为文章缩略内容,务必用分割线标识</button>
						  <button type="button" class="btn btn-info"  >用户：${loginName}</button>
                    </div></li>
                 </ul>
        
                  
				          <div class="tab-content">
				            <div class="tab-pane active" id="panel-700499" >
						              <!--在这里写 写文章的内容.-->
						              
				            
				             <!--0：技术文章  1.日记  2.杂记-->
					              <!--这里写markdowm编辑器editormd,然后用form提交-->
									    <#list list as article>
					             <form action="/blog/adminServlet"  method="POST"  target="tiDai" class="form-inline" accept-charset="utf-8">
					             	<div class="form-group" align="left" >
									    <label  for="exampleInputName2">文章标题
									    </label >
									    <input type="text" class="form-control" id="exampleInputName2" name="title" value="${article.art_title}"/>
									     
									    <label  for="exampleInputName2">文章类型
									    	<#assign art_type =  article.art_type>
									    	<#if art_type == 0 >
									        <input type="radio" name="type" value="0" checked="true" >技术文章
									        <input type="radio" name="type" value="1" >日记
									        <input type="radio" name="type" value="2" >杂记
									        <#elseif art_type == 1>
									        <input type="radio" name="type" value="0" >技术文章
									        <input type="radio" name="type" value="1" checked="true" >日记
									        <input type="radio" name="type" value="2" >杂记
									        <#else>
									        <input type="radio" name="type" value="0" >技术文章
									        <input type="radio" name="type" value="1" >日记
									        <input type="radio" name="type" value="2" checked="true" >杂记
									        
									   	 </#if>
									    </label >
									    
									  </div>
					             	<input type="hidden" name="method" value="saveArticle"> 
					                  <div class="editormd" id="test-editormd">
									  	  <textarea class="editormd-markdown-textarea" name="test-editormd-markdown-doc" >
									  	  ${article.art_mark}
									  	  </textarea>
									  	   <textarea class="editormd-html-textarea" name="test-editormd-html-code"></textarea>
									  </div>
									  <input type="hidden" name="art_id" value="${article.art_id}" />
									  <input type="submit" style="display:none"/>
					              </form>
									  </#list>
					              <iframe  style="display:none;" name="tiDai" >  </iframe>
				            
				             </div>
		        		</div>
			</div>
		</div>
		</div>
	<!--中间右侧结束-->	
	
	</div>
	
	</body>

</html>
