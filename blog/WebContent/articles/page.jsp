<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>吃饭最有趣</title>

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
							 <a class="navbar-brand" href="#">小马的窝</a>
						</div>
						
						<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
							<ul class="nav navbar-nav">
								<li class="active">
									 <a href="#">文章</a>
								</li>
								<li>
									 <a href="#">相册</a>
								</li>
								<li class="dropdown">
									 <a href="#" class="dropdown-toggle" data-toggle="dropdown">想对我说什么<strong class="caret"></strong></a>
									<ul class="dropdown-menu">
											<li><a href="../comment_tome/xiaoxue/">小学同学区</a></li>
											<li><a href="../comment_tome/middleschool/">初中同学区</a></li>
											<li><a href="../comment_tome/highschool/">高中同学区</a></li>
											<li><a href="../comment_tome/colleage/">大学同学区</a></li>
											<li><a href="../comment_tome/others/">others</a></li>
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
		
		<c:forEach items="${page.list}" var="articles">
		
			<div class="jumbotron">
				<h3 style="" align="left">
					${articles.art_title}
				</h3>
			<hr/>
				<p>
					${articles.art_content}
				</p>
				<p>
					 <a class="btn btn-primary btn-large" href="#">Learn more</a>
				</p>
			</div>
		
		</c:forEach>
		
			
			
			<!-- 文章内容结束 -->
			
			
			
			
			<!-- 分页区开始 -->
			
			
			<!-- 如果page_all<5则全部显示
				 如果page_all>5 则显示5个
			 -->
			<c:choose>
				<c:when test="${page.page_all<5 && page.page_all>0 }">
					  <c:set var="begin" value="1"></c:set>
					  <c:set var="end" value="${page.page_all}"></c:set>
				</c:when>			
				<c:otherwise>
					  <c:set var="begin" value="${page.page_now-2 }"></c:set>
					  <c:set var="end" value="${page.page_now+2 }"></c:set>
					  	<c:if test="${begin < 1 }">
					  		<c:set var="begin" value="1"></c:set>
					  		<c:set var="end" value="5"></c:set>
					  	</c:if>
					  	<c:if test="${end > page.page_all }">
					  		<c:set var="begin" value="${page.page_all -4 }"></c:set>
					  		<c:set var="end" value="${page.page_all }"></c:set>
					  	</c:if>
					  	
				</c:otherwise>
			</c:choose>
			
			
			<ul class="pagination pagination-lg">
			<!-- 上一页 -->
			<c:choose>
					<c:when test="${page.page_now > 1}">
							<li>
								 <a href="../${page.url}&page_now="${page.page_now-1}>上一页</a>
							</li>
					</c:when>
			</c:choose>
			<!-- foreach循环输出 -->
			<c:forEach begin="${begin}" end="${end}" var="forEach_now"  >
				<c:choose>
					<c:when test="${forEach_now eq page.page_now }">
							<li>
						 		<a href="#">${forEach_now }</a>
							</li>
					</c:when>
					<c:otherwise>
							<li>
								 <a href="../${page.url}&page_now=${forEach_now}">${forEach_now }</a>
							</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			
				<!--下一页 -->
			<c:choose>
					<c:when test="${page.page_now < page.page_all}">
							<li>
								 <a href="../${page.url}&page_now=${page.page_now+1}">下一页</a>
							</li>
					</c:when>
			</c:choose>
			
			
			</ul>
			
	
				<!-- 分页区结束-->
		</div>
		
		<!-- 右侧内容开始 -->
		<div class="col-md-4 column" >
		
			<div class="tabbable" id="tabs-63550" style="margin-top: 5px"><!-- 这个是右边选项卡内容 -->
				<ul class="nav nav-tabs">
					<li class="active">
						 <a href="#panel-736778" data-toggle="tab">公告</a>
					</li>
					<li>
						 <a href="#panel-991937" data-toggle="tab">about</a>
					</li>
				</ul>
				<div class="tab-content" >
					<div class="tab-pane active" id="panel-736778" style="height:100px">
						<p>
							公告里面的内容
						</p>
					</div>
					<div class="tab-pane" id="panel-991937" style="height:100px">
						<p>
							这里是我的自我介绍吧应该
						</p>
					</div>
				</div>
			</div>
			
			
			<div class="panel panel-primary" style="margin-top: 80px"><!-- 这里是右边下册的panel的内容 -->
				<div class="panel-heading">
					<h3 class="panel-title">
						友链
					</h3>
				</div>
				<div  class="panel-body">
						<ul class="nav nav-pills nav-stacked">
						  <li >
						    <a href="#">我是马凯的朋友</a>
						  </li>
						  <li><a href="#">马凯是我的朋友</a></li>
						  <li><a href="#">我是马凯的朋友的朋友</a></li>
						  <li><a href="#">马凯的朋友的朋友酶朋友</a></li>
						</ul>
				</div>
				</div>
			
			<div class="panel panel-primary" >
				<div class="panel-heading">
					<h3 class="panel-title">
					文档归类
					</h3>
				</div>
				<div class="panel-body">
						<ul class="nav nav-pills nav-stacked">
						  <li >
						    <a href="#">2017年9月</a>
						  </li>
						  <li><a href="#">2017年10月</a></li>
						  <li><a href="#">2017年11月</a></li>
						  <li><a href="#">2017年12月</a></li>
						  <li><a href="#">2017年13月</a></li>
						</ul>
				</div>
			</div>
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
<link href="../js_css/from.css"  rel="stylesheet" />
<link href="../js_css/bootstrap.min.css" rel="stylesheet">
<script src="../js_css/jquery.min.js"></script>
<script src="../js_css/bootstrap.min.js"></script>
</html>