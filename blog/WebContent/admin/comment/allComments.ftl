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
					  <a href="/blog/adminServlet?method=getArticle" class="list-group-item list-group-item-success">文章</a>
					  <a href="#" class="list-group-item list-group-item-info">相册</a>
					  <a href="#" class="list-group-item list-group-item-warning">留言</a>
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

	function deleteComment(com_id) {
		var r = confirm("queren");
		 if (r) {
			$(location).attr("href","/blog/adminServlet?method=deleteComment&com_id="+com_id);
		 	alert("删除成功！");
		 } else {
		  alert("你单击了取消按钮！");
		 }
	}

</script>

		<div class="col-md-10 column">
			<!-- 这里写提示语 -->
				<div class="alert alert-success" role="alert">这里是留言的删除页面····</div>
					<!-- 这里写留言的显示 -->		
					
					<#-- 这里写遍历留言内容 -->
			<#list list as comment>
			<#-- 实在是用freemarker写不好嵌套循环出来留言列表      以后再说吧 -->
					<div class="panel panel-danger" >
						<div class="panel-heading">
							<h3 class="panel-title">
							<div id="show_name" align="left">
							<span class="glyphicon glyphicon-user" aria-hidden="true"></span>昵称：${comment.com_name}
							<div style="float: right;" id ="show_time">Tiime:${comment.com_date}</div>
							</div>
							</h3>
						</div>
						
						<div class="panel-body" align="left" id="show_content"><div id="show_other"></div>
							${comment.com_content}
								
						</div>
						
						<div class="panel-footer" align="left">
							<div align="right">
							<span class="glyphicon glyphicon-hand-right" >com_id:${comment.com_id}</span>
						
							<button type="button" class="btn" 
							onclick="return deleteComment(${comment.com_id});">
								<span class="glyphicon glyphicon-trash"> 删除
							</button>
							</span>
							</div>
						</div>
					</div>
			</#list>
			
						<!-- 这里写留言的留言结束 -->		
							
								              
				             </div>
		        		</div>
			</div>
		</div>
		</div>
	<!--中间右侧结束-->	
	
	</div>
	
	</body>

</html>
