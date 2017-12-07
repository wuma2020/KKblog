<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>吃饭最有趣</title>
<script src="./js_css/jquery-3.2.1.js"></script>
</head>
<body style="color: olive;background-color: silver;">
<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<!-- 这里是导航栏 -->
			<div class="row clearfix">
				<div class="col-md-12 column">
					<nav class="navbar navbar-default navbar-fixed-top navbar-inverse" role="navigation">
						<div class="navbar-header">
							 <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"> 
							 <span class="sr-only">Toggle navigation</span>
							 <span class="icon-bar"></span>
							 <span class="icon-bar"></span>
							 <span class="icon-bar"></span></button> 
							 <a class="navbar-brand" href="./">小马的窝</a>
						</div>
						
						<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
							<ul class="nav navbar-nav">
								<li class="active">
									 <a href="./">文章</a>
								</li>
								<li>
									 <a href="javascript:alert('没有相册');">相册</a>
								</li>
								<li class="dropdown">
									 <a href="#" class="dropdown-toggle" data-toggle="dropdown">想对我说什么<strong class="caret"></strong></a>
									<ul class="dropdown-menu">
											<li><a href="comment_tome/xiaoxue/">小学同学区</a></li>
											<li><a href="comment_tome/middleschool/">初中同学区</a></li>
											<li><a href="comment_tome/highschool/">高中同学区</a></li>
											<li><a href="comment_tome/colleage/">大学同学区</a></li>
											<li><a href="comment_tome/others/">others</a></li>
											<li class="divider"></li>

											<li><a href="javascript:alert('没上过幼儿园和研究生');">幼儿园已经研究生同学区</a>
											</li>
										
									</ul>
								</li>
								<li class="dropdown">
									 <a href="#" class="dropdown-toggle" data-toggle="dropdown">其他玩意<strong class="caret"></strong></a>
									<ul class="dropdown-menu">
										<li>
											 <a href="#">全民K歌</a>
										</li>
										<li>
											 <a href="#">音乐</a>
										</li>
										<li>
											 <a href="#">Something else here</a>
										</li>
										<li class="divider">
										</li>
										<li>
											 <a href="#">分割线下</a>
										</li>
										
									</ul>
								</li>
							</ul>
							<form class="navbar-form navbar-left" role="search">
								<div class="form-group">
									<input type="text" class="form-control" value="我给你去百度搜"/>
								</div> <button type="submit" class="btn btn-default">Submit</button>
							</form>
							<!-- 下面是导航栏右侧的内容 -->
							<ul class="nav navbar-nav navbar-right">
								<li>
									 <a href="#">login</a>
								</li>
								<li class="dropdown">
									 <a href="#" class="dropdown-toggle" data-toggle="dropdown">无用处<strong class="caret"></strong></a>
									<ul class="dropdown-menu">
										<li>
											 <a href="#">一号无用</a>
										</li>
										<li>
											 <a href="#">一号无用</a>
										</li>
										<li>
											 <a href="#">一号无用</a>
										</li>
										<li class="divider">
										</li>
										<li>
											 <a href="#">Separated link</a>
										</li>
									</ul>
								</li>
							</ul>
						</div>
						
					</nav>
				</div>
			</div>
				<!-- 一个引言  放一张背景图-->
			<div style="height:200px;weight:100%;background-image:url('/blog/image/html_hengfu3.png');margin-top: 50px">
				<!--  这里暂时不加文字了，之后再处理*****************？
				<blockquote class="pull-right">
				<p>
					胆小人生,不易相处.　　　　　　　　　 　　  --><!-- 此处加了空格，输入法满月状态下加 -->
				<!--
				</p> 
				<p>
					年轻无为，卖马为生。  
				</p> 
				</blockquote>
				  -->
			</div>
		</div>
	</div>
	
	
	
	
	<div class="row clearfix" style="margin-top: 40px"><!--这是大的DIV包括body和rigth  -->
	<!-- 文章区域 -->
		<div class="col-md-8 column" >
		
		<!-- 这里开始写从后台拿到的request脸面的page内容 -->
		<!--这里用freemarker的语法来写遍历文章list-->
		<#list list as article>		
			<div class="jumbotron">
				<h3 style="" align="left">
					标题：${article.art_title}
				</h3>
			<hr/>
				<p>
						${article.art_content?keep_before("<hr>")}
				</p>
				<p>
					 <a class="btn btn-primary btn-large"  href="/blog/ArticleServlet?method=getArticleOne&art_id=${article.art_id}">Learn more</a>
				</p>
			</div>
		
		</#list>		
			
			
			<!-- 文章内容结束 -->
			
			
			
			
			<!-- 分页区开始 -->
			
			
			<!-- 如果page_all<5则全部显示
				 如果page_all>5 则显示5个
			 -->
			<#if (page_all < 5 && page_all > 0) >
			 <#assign begin=1 end = page_all >
				<#else>
						<#assign begin=(page_now-2) end = (page_now +2)>
						<#if (begin<1) >
							<#assign begin = 1 end = 5>
						</#if> 
						<#if (end >page_all) >
							<#assign begin = (page_all - 4) end = page_all>
						</#if>
				
			</#if>
			
			<#-- 现在开始分页 -->
			
			<ul class="pagination pagination-lg">
				<!-- 上一页 -->
					<#if ( page_now > 1 ) >
							<li>
								 <a href="./${url}&page_now=${page_now-1}">上一页</a>
							</li>
					</#if>
					<!-- 上一页 结束-->
					<!--中间循环-->
					<#list begin..end as x >
							<#if x == page_now>
								<li>
							 		<a href="#">${x }</a>
								</li>
							<#else>
								<li>
									 <a href="./${url}&page_now=${x}">${x }</a>
								</li>
							</#if>
					</#list>
					<!--中间循环结束-->
					<!--下一页 -->
					<#if (page_now < page_all) >
						<li>
							 <a href="./${url}&page_now=${page_now+1}">下一页</a>
						</li>
					</#if>
					<!--下一页 结束-->
			</ul>
			
			<#-- 分页结束 -->
				<!-- 分页区结束-->
		</div>
		
		<!-- 右侧内容开始 -->
		<div class="col-md-4 column" >

					<div id="page2"></div>
					<div id="page1"></div>
					<div id="page3"></div>
					 <script>
		          $("#page1").load("/blog/common/youlian.html");
		          $("#page2").load("/blog/common/fenyeka.html");
		          $("#page3").load("/blog/common/guidang.html");
   					 </script>


		</div>
	</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<blockquote class="pull-right">
						<p>
							天空飘来五个字　　　　　　　　　　　　　　　
						</p> <small>ｓｔａｔｉｃ＿ｍｋｋ</small>
					</blockquote>
				</div>
			</div>
		</div>

</div>

</body>
<link href="./js_css/from.css"  rel="stylesheet" />
<link href="./js_css/editormd.css"  rel="stylesheet" />
<link href="./js_css/bootstrap.min.css" rel="stylesheet">
<script src="./js_css/jquery.min.js"></script>
<script src="./js_css/bootstrap.min.js"></script>
</html>