<!doctype html><html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <%@ include file="/assets/global.jsp"%>
	<title>服务查询</title>
	<link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
	<link rel="stylesheet" href="${ctx}/assets/css/map_make.css"/>
	<script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode"  main="${ctx}/assets/js/map_make"></script>
    <script>
	    var map_make_ajax = ctx+"/entmap/getentmaplist";
 	    var release_url =ctx+"/entmap/toRelease";
	    var release_ajax =ctx+"/entmap/couldRelease";
        var release_dele_ajax =ctx+"/entmap/deleteEntMap";
        var map_create = ctx+"/entmap/addEntUserMap";
        </script>
</head>
<body>

<div class="navright">
    <div class="fwtop"><em>制作与发布</em><span>退出</span></div>
    <div class="map_make">
        <!--地图制作与发布开始-->
        <div class="mapmake_search">
            <div class="makemap"><a href="${ctx}/entmap/addEntUserMap"><i></i>创建地图</a></div>
            <div class="map_search_top">
                <span class="fbtime">
                    <select name="fbtime" id="sel">
                        <option>创建时间</option>
                        <option value=3>三个月内</option>
                        <option value=12>一年以内</option>
                        <option value=36>三年以内</option>
                        <option value=60>五年以内</option>
                        <option value=-1>全部</option>
                    </select>

                    <script>
                        use("$/selectBox",function(){
                            $("#sel").selectBox({
                                mobile: true,
                                menuSpeed: 'fast'
                            })
                        })
                    </script>
                </span>
                <input class="map_value" type="text" />
                <span class="map_search">搜索</span>
            </div>
            <div class="clear"></div>
        </div>
        <!--地图制作与发布结束-->

        <!--地图制作与发布内容显示开始-->
        <div class=" releasemap">
            <ul class="grid">

            </ul>
            <div class="clear"></div>
        </div>
        <div class="page"></div>
        <!--地图制作与发布内容显示结束-->
    </div>
</div>
</body>
</html>