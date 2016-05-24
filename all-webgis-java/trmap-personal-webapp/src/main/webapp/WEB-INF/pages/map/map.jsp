<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/assets/libs/openlayers/css/ol.css"/>
<link rel="stylesheet" href="${ctx}/assets/css/map/map.css"/>
<title>地图管理</title>
    <%@ include file="/assets/global.jsp"%>
</head>
<body>

	<div id="map" class="map"></div>
	<div id="popup" class="ol-popup">
      <a href="#" id="popup-closer" class="ol-popup-closer"></a>
      <div id="popup-content"></div>
    </div>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode"></script>
	<script type="text/javascript" src="${ctx}/assets/libs/openlayers/ol.js"></script>
	<script type="text/javascript" src="${ctx}/assets/js/map/map.js"></script>
	
</body>
</html>