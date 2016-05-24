<!doctype html><html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <c:set var="mapid" value="${enterpriseMap.id}"/> --%>
<%-- <c:set var="mapname" value="${enterpriseMap.mapname}"/> --%>
<%-- <c:set var="mapid" value="998998"/> --%>
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <%@ include file="/assets/global.jsp"%>
    <title>地图发布</title>
    <link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/libs/openlayers/css/ol.css"/>
    <link rel="stylesheet" href="${ctx}/assets/libs/openlayers/css/trmap_control.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/map_creater.css"/>
<style type="text/css">
.ol-popup {
  position: absolute;
  background-color: white;
  -webkit-filter: drop-shadow(0 1px 4px rgba(0,0,0,0.2));
  filter: drop-shadow(0 1px 4px rgba(0,0,0,0.2));
  padding: 15px;
  border-radius: 10px;
  border: 1px solid #cccccc;
  bottom: 12px;
  left: -50px;
  min-width: 280px;
}
.ol-popup:after, .ol-popup:before {
  top: 100%;
  border: solid transparent;
  content: " ";
  height: 0;
  width: 0;
  position: absolute;
  pointer-events: none;
}
.ol-popup:after {
  border-top-color: white;
  border-width: 10px;
  left: 48px;
  margin-left: -10px;
}
.ol-popup:before {
  border-top-color: #cccccc;
  border-width: 11px;
  left: 48px;
  margin-left: -11px;
}
.ol-popup-closer {
  text-decoration: none;
  position: absolute;
  top: 2px;
  right: 8px;
}
.ol-popup-closer:after {
  content: "✖";
}
</style>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode"></script>
    <script src="${ctx}/assets/libs/openlayers/ol.js"></script>
    <script src="${ctx}/assets/libs/openlayers/trmap_styles.js"></script>
    <script src="${ctx}/assets/libs/openlayers/trmap_control.js"></script>
    <script>
    	var mapname="${enterpriseMap.mapname}"//创建地图时返回的地图名称
    	var mapid = "${enterpriseMap.id}";//创建地图时返回的地图id
        seajs.use("__/js/map_creater");
    </script>
<title>地图发布</title>
</head>
<body>
  <div id="map" class="map"></div>
   <div id="popup" class="ol-popup">
      <a href="#" id="popup-closer" class="ol-popup-closer"></a>
      <div id="popup-content"></div>
    </div>
   <script src="${ctx}/assets/js/map/map.js"></script>
</body>
</html>