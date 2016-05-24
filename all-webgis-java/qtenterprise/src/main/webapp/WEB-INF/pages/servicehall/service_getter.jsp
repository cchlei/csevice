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
    <title>服务申请</title>
    <link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/libs/openlayers/css/ol.css"/>
    <link rel="stylesheet" href="${ctx}/assets/libs/openlayers/css/trmap_control.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/service_getter.css"/>

    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode"></script>
    <script src="${ctx}/assets/libs/openlayers/ol.js"></script>
    <script src="${ctx}/assets/libs/openlayers/trmap_styles.js"></script>
    <script src="${ctx}/assets/libs/openlayers/trmap_control.js"></script>
    <script>

        var view = "service_getter";

        var mapid=${relmap.id};

        //列表的接口
        var action_list = ctx+"/entrelgraphics/getlist?mapid="+mapid;
        //申请的判断
        var applic_action = ctx+"/entrelmap/isApply/${relmap.id}";
        
        seajs.use("__/js/service_getter");
    </script>
</head>
<body>
    <div class="test">
        <div id="popup"></div>
        <div id="popup-content"></div>
        <div id="popup-closer"></div>
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
        
        <ul class="ft_list"></ul>
        <ul class="page trypage"></ul>
        <a class=applic_btn>立即申请</a>
    </div>

    <!--顶部-->
    <h2 class="pagetitle"><a href="#" class="logo"></a> <span>地图服务预览</span></h2>


    <!--搜索条-->
    <div class="search_bar input"><input/><em class="bt sure">搜索</em> <i class="cls">×</i></div>

    <script src="${ctx}/assets/js/map/map.js"></script>


</body>
</html>