<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>
<%@ include file="/assets/global.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>消息管理</title>
<link rel="stylesheet" type="text/css"
	href="${ctx }/assets/libs/jquery-easyui-1.4.5/themes/metro/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx }/assets/libs/jquery-easyui-1.4.5/themes/icon.css" />
		<script type="text/javascript">
		var contextPath='${ctx}';
	</script>
<script type="text/javascript"
	src="${ctx }/assets/libs/jquery-easyui-1.4.5/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx }/assets/libs/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx }/assets/libs/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${ctx }/assets/message/js/message.js"></script>
<body>
	<style type="text/css">
html, body {
	height: 98.6%
}

a.viewInfo {
	color: #fff;
	background-color: #428BCA;
	padding: 2px 9px;
	text-decoration: none;
	display: inline-block;
	border-radius: 6px;
}
</style>
	<form id="fm" method="post" style="height: 100%">
		<div style="width: 100%; height: 100%;">
			<table id="dg"></table>
		</div>
		<div id="toolbar" style="width: 100%;">

			<input type="hidden" id="searchValhidden" />
		</div>
		<input id="searchVal" />
		<div id="mm" style="width: 120px"></div>
	</form>
	<div id="dialog" style="display: none;">
		<form id="addfm" method="post" ,style="height: 100%">
			<div class="fitem" style="margin: 20px 30px">
				<label>消息标题</label> 
				<p></p>
				<input name="title"  class="easyui-textbox" data-options="required:true,validType:'length[1,15]'"
					style="width: 150px" />
			</div>
			<div class="fitem" style="margin: 10px 30px">
				<label>消息内容</label>		
			</div>
			<div class="fitem" style="margin: 10px 30px">
				<textarea id="contentvalue" class="easyui-validatebox" name="content" data-options="required:true,validType:'length[1,100]'"
					wrap="virtual" style="width: 100%; height:100%"></textarea>
			</div>
		</form>
	</div>
</body>
</html>