<!DOCTYPE html><html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head lang="en">
<meta charset="UTF-8">
<title>注册失败</title>
<%@ include file="/static/global.jsp"%>
<base href="${ctx}/">
<link rel="stylesheet" href="http://www.trmap.cn/html/css/qtcss.css"/>
<link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" />
<script src="${ctx}/static/js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="${ctx}/static/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx}/static/js/check.js"></script>
<style>
body{
	font-family: "微软雅黑"
}

#userTips, #emailTips, #pass1Tips, #pass2Tips {
	margin-left: 5px;
	font-size: 12px;
}
</style>
</head>
<body>
	<div class="topbar">
		<div class="mc">
			<a href="#" class="logo"></a>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<h2>注册失败。原因：${msg}</h2>
			<a href="${ctx}/account/register">重新注册</a>
		</div>
	</div>
</body>
</html>