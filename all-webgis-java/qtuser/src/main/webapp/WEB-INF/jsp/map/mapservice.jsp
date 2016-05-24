<!DOCTYPE HTML><html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<head>
	<title>个人地图服务展示界面</title>
	<meta name="renderer" content="webkit" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="/static/global.jsp" %>
	<base href="${ctx}/">
	<link rel="stylesheet" href="${ctx}/static/map/css/mapservice.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/qtmap/style.css" />
	<script src="${ctx}/static/libs/cseajs/csea$.js" id=seajsnode></script>
	<script src="${ctx}/static/js/layer.js"></script>
	<script src="${ctx}/qtmap/qtmap.min.js"></script>
	<script src="${ctx}/static/map/js/mapservice.js"></script>
</head>
<body>
	<div id="qtmap" class="map"></div>
	<input type="hidden" id="mapid"  value="${mapid}"/>
	<div class="data-list">
	   <div class="list-title">
	      <div class="list-title-left">
	        <div class="left"><a class="list-logo" onclick="javascript:void(0)"></a></div>
	        <span id="mapName" class="center show-map"></span>
	        <div class="right"> <a id="userName" class="list-user"></a></div>
	        <div class="clear"></div>
	      </div>
	      <div class="list-title-right" onclick="listChange()">
	        <a class="list_menu" onclick="javascript:void(0)"></a>
	      </div>
	      <div class="clear"></div>
	   </div>
	   <div class="list-content">
	      <div class="hidden info-content"><span id="map_info"></span></div>	      
	      <div id="features" class="show feature-content"></div>
	   </div>	
	</div>
</body>
</html>
