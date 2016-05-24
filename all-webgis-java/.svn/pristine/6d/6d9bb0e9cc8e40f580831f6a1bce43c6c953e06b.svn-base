<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<title>天润云地图 -- 运维管理系统</title>
<meta name="description" content="天润云地图后台运维管理系统" />
<link rel="stylesheet" href="${ctx}/assets/libs/ace/1.3.2/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/assets/libs/ace/1.3.2/css/font-awesome.min.css" />
<link rel="stylesheet" href="${ctx}/assets/libs/ace/1.3.2/css/ace-fonts.min.css" />
<link rel="stylesheet" href="${ctx}/assets/libs/ace/1.3.2/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />
<!--[if lte IE 9]>
  <link rel="stylesheet" href="${ctx}/assets/libs/ace/1.3.2/css/ace-part2.min.css" class="ace-main-stylesheet" />
<![endif]-->
<!--[if lte IE 9]>
  <link rel="stylesheet" href="${ctx}/assets/libs/ace/1.3.2/css/ace-ie.min.css" />
<![endif]-->
<script src="${ctx}/assets/libs/ace/1.3.2/js/ace-extra.min.js"></script>
<!--[if lte IE 8]>
<script src="${ctx}/assets/libs/ace/1.3.2/js/html5shiv.min.js"></script>
<script src="${ctx}/assets/libs/ace/1.3.2/js/respond.min.js"></script>
<![endif]-->
</head>

<body class="no-skin">
	<div id="navbar" class="navbar navbar-fixed-top navbar-default">
		<script type="text/javascript">
  			try{ace.settings.check('navbar' , 'fixed')}catch(e){}
  		</script>
		<div id="navbar-container" class="navbar-container">

			<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
			      <span class="sr-only">Toggle sidebar</span>
			      <span class="icon-bar"></span>
			      <span class="icon-bar"></span>
			      <span class="icon-bar"></span>
		    </button>

			<div class="navbar-header pull-left">
				<a href="#" class="navbar-brand">
					<small> 
						<i class=""></i>
						天润云地图运维管理系统
					</small>
				</a>
			</div>
			
			<div class="navbar-buttons navbar-header pull-right  collapse navbar-collapse" role="navigation">
		    	<ul class="nav ace-nav">
		    	<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle" aria-expanded="false">
								<img class="nav-user-photo" src="${ctx}/assets/libs/ace/1.3.2/avatars/user.jpg" alt="头像">
								<span class="user-info">
									<small>欢迎您,</small>
									<shiro:principal property="username"></shiro:principal>
								</span>
								<i class="ace-icon fa fa-caret-down"></i>
							</a>

							<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="#">
										<i class="ace-icon fa fa-cog"></i>
										设置
									</a>
								</li>

								<li class="divider"></li>
								
								<li>
									<a href="#">
										<i class="ace-icon fa fa-power-off"></i>
										注销
									</a>
								</li>
							</ul>
						</li>
		    	</ul>
		  	</div>

		</div>
	</div>
	<!-- /.navbar -->

	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {ace.settings.check('main-container', 'fixed')} catch (e) {}
		</script>
		<div class="sidebar sidebar-fixed responsive" id="sidebar">
			<!-- sidebar goes here -->
			<script type="text/javascript">
				try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
			</script>
			
			<ul class="nav nav-list">
				<li>
					<a data-url="user/list" href="#user/list">
						<i class="menu-icon fa fa-leaf"></i>
						<span class="menu-text">
							用户管理
						</span>
					</a>
				</li>
			</ul>
			
		</div>

		<div class="main-content">
			<div class="breadcrumbs breadcrumbs-fixed">
				<!-- breadcrumbs goes here -->
				<script type="text/javascript">
					try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
				</script>
			</div>

			<div class="page-content">
				<div id="page-content-area">
					 <!-- ajax content goes here -->
				</div>
			</div>
			<!-- /.page-content -->
		</div>
		<!-- /.main-content -->


	</div>
	<!-- /.main-container -->

	<!-- list of script files -->
	<!--[if !IE]> -->
		<script src="${ctx}/assets/libs/ace/1.3.2/js/jquery.min.js"></script>
	<!-- <![endif]-->
	<!--[if lte IE 9]>
   		<script src="${ctx}/assets/libs/ace/1.3.2/js/jquery1x.min.js"></script>
  	<![endif]-->
	<script src="${ctx}/assets/libs/ace/1.3.2/js/bootstrap.min.js"></script>
	<script src="${ctx}/assets/libs/ace/1.3.2/js/ace-elements.min.js"></script>
	<script src="${ctx}/assets/libs/ace/1.3.2/js/ace.min.js"></script>

	<script type="text/javascript">
	jQuery(function ($) {
		$('#page-content-area').ace_ajax({
			content_url:function(url){
				return url;
			},
			default_url:'user/list'
		});
	})
	</script>
</body>
</html>