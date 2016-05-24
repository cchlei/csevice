<!doctype html><html class="_map_search">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
  <head>
    <title>批量导入矢量点数据页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<%@ include file="/assets/global.jsp"%>
    
	<!-- 上传勿删 -->
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/webuploader/webuploader.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/webuploader/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/webuploader/style.css">
	<script type="text/javascript" src="${ctx}/assets/webuploader/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/webuploader/webuploader.js"></script>
	<script type="text/javascript" src="${ctx}/assets/webuploader/getting-started.js"></script>
	<script type="text/javascript" src="${ctx}/assets/webuploader/bootstrap.min.js"></script>

  </head>
<body>

<div id="uploader" class="wu-example">
    <!--用来存放文件信息-->
    <div id="thelist" class="uploader-list"></div>
    <div class="btns">
        <div id="picker">选择文件</div>
        <button id="ctlBtn" class="btn btn-default">开始上传</button>
    </div>
</div>

<!--dom结构部分-->
<div id="uploader-demo">
    <!--用来存放item-->
    <div id="fileList" class="uploader-list"></div>
    <div id="filePicker">选择图片</div>
</div>
</body>
</html>