<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>
<%@ include file="/assets/global.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>默认图库管理</title>
<link rel="stylesheet" type="text/css" href="${ctx }/assets/libs/jquery-easyui-1.4.5/themes/metro/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${ctx }/assets/libs/jquery-easyui-1.4.5/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="${ctx }/assets/libs/webuploader/webuploader.css" />
<link rel="stylesheet" type="text/css" href="${ctx }/assets/libs/webuploader/style.css" />
<link rel="stylesheet" type="text/css" href="${ctx }/assets/libs/webuploader/demo.css" />
<link rel="stylesheet" type="text/css" href="${ctx }/assets/libs/fancy/css/jquery.fancybox.css" />
	<script type="text/javascript">
	var contextPath='${ctx}';
	</script>
<script type="text/javascript"
	src="${ctx }/assets/libs/jquery-easyui-1.4.5/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx }/assets/libs/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx }/assets/libs/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/assets/libs/webuploader/webuploader.js"></script>
<script type="text/javascript" src="${ctx}/assets/libs/fancy/js/jquery.fancybox.pack.js"></script>
<script type="text/javascript" src="${ctx}/assets/libs/fancy/js/jquery.mousewheel-3.0.6.pack.js"></script>
<script type="text/javascript" src="${ctx}/assets/imagestorage/js/imagestorage.js"></script>
</head>
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
		<label id="radioH" class="radio"><input name="imageTypeCheck" type="radio" value="0" onchange="radioCheck(this);"/>头像 </label>
		<label id="radioP" class="radio"><input name="imageTypeCheck" type="radio" value="1" onchange="radioCheck(this);"/>专题图片 </label>
	</form>
	<div id="dialog" style="display: none;" width="500" height="500">
			<form id="addfm" method="post">
			<div class="fitem" style="margin: 10px 30px">
				<label>图片类型</label> 
				<select id="imagetype" name="imageTypeValue" class="easyui-combobox" data-options="required:true" >	
				</select> 
			</div>
			</form>
			<div class="fitem" style="margin: 10px 30px" >
					<div id="uploader" class="wu-example">
						<div class="queueList">
							<div id="dndArea" class="placeholder">
								<div id="filePicker"></div>
								<p>或将照片拖到这里，单次最多可选10张</p>
							</div>
						</div>
						<div class="statusBar" style="display: block;">
							<div class="progress">
								<span class="text">0%</span> <span class="percentage"></span>
							</div>
							<div class="info"></div>
							<div class="btns">
								<div id="filePicker2"></div>
								<div class="uploadBtn">开始上传</div>
							</div>
						</div>
					</div>
			</div>
	</div>
	<div id="images"  style="display: none"></div>
</body>
</html>