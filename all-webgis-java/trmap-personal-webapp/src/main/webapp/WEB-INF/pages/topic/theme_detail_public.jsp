<!DOCTYPE html> <html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>专题详情</title>
    <%@ include file="/assets/global.jsp"%>
    <link rel="stylesheet" href="${ctx}/assets/css/comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/theme_detail.css"/>
    <link rel="stylesheet" href="${ctx}/assets/libs/openlayers/css/ol.css"/>
	<link rel="stylesheet" href="${ctx}/assets/css/map/map.css"/>
    <script src="${ctx}/assets/libs/avalon.min.js"></script>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode"></script>
    <script>
    	var pagevars={
            //记录列表
            reclist:"${ctx}/record/records/${userTopic.id}",
            //专题信息
            topic:"${ctx}/topic/info/${userTopic.id}",

            //专题收藏与取消收藏
            do_collect:"${ctx}/collect/addAndDel"
        };
    	var topicid = ${userTopic.id};
    </script>
</head>
<body>
    <i class="cl_loading page"></i>
    <div class="root no_top no_col_1_2">
     <jsp:include page="/assets/top.jsp"></jsp:include>

        <div class="mainCont col3" ms-controller="main" ms-class-1="no_gap:view==1">
            <div class="inner">
                <h1 class="page_name">
                    <table>
                        <tr>
                            <td class="back"><a href="javascript:" ms-click="refreshback"></a></td>
                            <td class="info">
                                <p>{{topic.name}}<label> <em>({{topic.num_of_public}})</em>  </label></p>
                            </td>
                            <td class="search_td">
                                <span class="search_span"> <input ms-duplex="f.key" name="serch"/> <i class="iclear" ms-click="clear_key()"></i> <em ms-click="dosearch()">搜索</em> </span>
                            </td>
                            <td class="btns">
                                <a href="#" class="location"    ms-click="view_toggle(1)"   ms-visible="view==0">地图模式</a>
                                <a href="#" class="clock"       ms-click="view_toggle(0)"   ms-visible="view==1">时间轴模式</a>



                            </td>
                        </tr>
                    </table>
                </h1>

                <!--用来控制a的-->
                <div class="main_scroller"><div class="place_holder"></div></div>

                <!--当前记录的年月-->
                <span class="date_box" ms-visible="date_visible"></span>



                <div class="main">
                    <!--地图模式-->
                    <div class="mapCont" ms-visible="view==1" ms-controller="map_rec_list">
                        <div class="map_rec_list">
                            <a>
                                未标注记录
                                <i></i>
                                <ul class="list_cont" ms-each="list">
                                    <li ms-if="!!!el.geom" ms-click="itemclick(el)"><em>{{el.name}}</em></li>

                                </ul>
                            </a>
                            <a>
                                标注记录
                                <i></i>
                                <ul class="list_cont"  ms-each="list">
                                    <li ms-if="!!el.geom"  ms-click="itemclick(el)"><em>{{el.name}}</em></li>
                                </ul>
                            </a>
                        </div>
                        <div id="map" class="map"></div>
                    </div>
                    <!--时间轴模式 -->
                    <div class="cont" ms-visible="view==0">
                        <div class="cell-1">
                            <div class="reclist" ms-controller="reclist">
                                <b class="line"></b>
                                <div class="cardCont" ms-visilbe="list.length">
                                    <div class="card" ms-repeat="list" ms-attr-date="el.occurtime" ms-click="cardClick($event,el)">
                                        <b class="triangle"></b> <b class="dot"></b>
                                        <div class="inner">
                                            <h2>
                                                <p class="r">
                                                    <em class="msg">({{el.num_msg}})</em>


                                                </p>
                                                <em class="date">{{el.datef}}</em>
                                                <a ms-attr-href="${ctx}/record/toRecord/{{el.id}}">{{el.name}}</a>
                                            </h2>

                                            <p class="desc">{{el.description | truncate(131)}}</p>
                                            <p class="thumb" ms-visible="!el.piclist || el.piclist.length">
                                                <span ms-repeat="el.piclist"><img ms-attr-src="co.getThumb(el,225,230)"></span>
                                            </p>
                                            <p class="info">
                                                <em class="loca break" ms-class-1="link1:el.geom">{{el.addrname || "未关联地图"}}</em>
                                            </p>
                                        </div>
                                    </div>
                                        </div>

                                <div class="blank_tip" ms-visible="!list.length">
                                    <div class="inner">
                                        空空如也 <br>
                                        本专题下暂无记录!
                                    </div>

                                </div>
                            </div>
                        </div>
                        <div class="cell-2">
                            <!--专题信息-->
                            <div class="theme_info">
                                <div class="faceCont">
                                    <div class="inner">
                                        <span class="img"> <img ms-if="topic.coverurl" ms-attr-src="co.getThumb(topic.coverurl,170)"></span>
                                        <div class="desc" style="width: 154px;">
                                            <h4>{{topic.name}}</h4>
                                            <p>{{topic.description}}</p>
                                            <div class="quanxian"><a ms-class-1="no_fav:!topic.iscollect" ms-click="do_collect(topic)" style="margin-left: 130px;">{{topic.num_of_collect}}</a></div>
                                        </div>
                                    </div>
                                </div>

                                <div class="minimapCont">
                                	<div id="map2" class="map"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
	<div id="popup" class="ol-popup-des">
      <a href="#" id="popup-closer" class="ol-popup-closer"></a>
      <div id="popup-content"></div>
    </div>
    <div id="popup2" >
      <a href="#" id="popup-closer2" ></a>
      <div id="popup-content2"></div>
    </div>
<script type="text/javascript" src="${ctx}/assets/libs/openlayers/ol.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/map/map.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/map/map2.js"></script>
</body>
</html>