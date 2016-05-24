<!doctype html><html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <%@ include file="/assets/global.jsp"%>
    <title>服务预览</title>
    <link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/libs/openlayers/css/ol.css"/>
    <link rel="stylesheet" href="${ctx}/assets/libs/openlayers/css/trmap_control.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/service_getter.css"/>
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

        var view = "service_getter";

        var mapid=${relmap.id};

        //列表的接口
        var action_list = ctx+"/entrelgraphics/getlimitlist?mapid="+mapid;
        
        seajs.use("__/js/service_getter");
    </script>
</head>
<body>
	<div id="popup" class="ol-popup">
      <a href="#" id="popup-closer" class="ol-popup-closer"></a>
      <div id="popup-content"></div>
    </div>
    <div id="map" class="map"></div>


    <!--申请窗体-->
    <div class="apply_panel">
       <!-- <h3></h3> --> 
        <div class="img"><img src="${ctx}/assets/images/thumb_img.png" alt=""></div>
        <div class="attrs">
            <em>服务名称：</em>${relmap.mapname}<br>
            <em>发布单位：</em>${relmap.user.name} <br>
            <em>接口类型：</em>${relmap.serviceType} <br>
            <em>有效时间：</em><i class="date">${relmap.validitytime}</i><br>
            <em>创建时间：</em><i class="date">${relmap.umcreatetime}</i><br>
            <em>服务标签：</em>${relmap.tags} <br>
        </div>
        
        <ul class="ft_list">
    
        </ul>
        <ul class="page trypage"></ul>
    </div>

    <!--顶部-->
    <h2 class="pagetitle"><a href="#" class="logo"></a> <span>地图服务预览</span></h2>


    <!--搜索条-->
    <div class="search_bar input"><input style="display: none;"/>
    <em class="bt sure" style="margin-right: 1px;">查看前十条数据</em> <i class="bt clear">清除</i></div>

    <script src="${ctx}/assets/js/map/map.js"></script>


</body>
</html>