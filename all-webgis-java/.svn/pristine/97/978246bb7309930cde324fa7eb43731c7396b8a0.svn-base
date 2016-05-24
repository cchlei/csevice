<!DOCTYPE html> <html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>我的日历</title>
    <%@ include file="/assets/global.jsp"%>
    <link rel="stylesheet" href="${ctx}/assets/css/comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/calendar.css"/>
    <link rel="stylesheet" href="${ctx}/assets/libs/openlayers/css/ol.css"/>
    <script src="${ctx}/assets/libs/openlayers/ol-debug.js"></script>
    <script src="${ctx}/assets/libs/openlayers/trmap_styles.js"></script>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode" main="__/js/calendar"></script>
    <script>
        var pagevars = {

            //设置选择状态
            setSelectStatus:"${ctx}/topic/status",

            //
            getColorlist:"",

            /**
             * 设置专题颜色
             */
            setTopicColor:"${ctx}/topic/color",

            /**
             * 获取专题列表
             */
            getTopicList:"${ctx}/topic/topicList",

            /**
             * 按月获取列表
             */
            getReclistByMonth:"${ctx}/record/month",

            /**
             * 获取某月临近月的列表（跳过空月）
             */
            getNeighborMonthReclist:"${ctx}/record/nextMonth"
        }
    </script>
</head>

<body>
<i class="cl_loading page"></i>
<div class="root no_col_1 no_top" ms-controller="root">


    <div class="col2" ms-controller="topiclist">
        <h2 class="page_name">日历</h2>
        <div class="calendar_nav_cont">
            <ul class="calendar_nav">
                <li>
                    <h3 class="color-4"><i></i>我的专题[{{d.myTopic.length}}]</h3>
                    <a
                            ms-click="toggleLight($event,el)"
                            ms-repeat="d.myTopic"
                            ms-attr-tid="{{el.id}}"
                    >
                        <i ms-visible="!el.selectStatus"></i>
                        <i
                                ms-css-background-color="el.topiccolor || '#08c'"
                                ms-visible="el.selectStatus"
                                ms-click="change_cl($event,el)"
                        ></i> <em>{{el.name || "无名专题"}}</em>
                    </a>
                </li>
                <li>
                    <h3 class="color-6"><b></b><i></i>收藏专题[{{d.collectTopic.length}}]</h3>
                    <a
                            ms-click="toggleLight($event,el)"
                            ms-repeat="d.collectTopic"
                            ms-attr-tid="{{el.id}}"
                    >
                        <i ms-visible="!el.selectStatus"></i>
                        <i
                                ms-css-background-color="el.topiccolor || '#08c'"
                                ms-click="change_cl($event,el)"
                                ms-visible="el.selectStatus"
                        ></i> <em>{{el.name || "无名专题"}}</em>
                    </a>
                </li>
            </ul>
        </div>

    </div>
    <div class="mainCont col3 no_bb" ms-controller="main">
        <div class="inner">
            <h1 class="page_name">
                    <span class="rr">
                        <a class="cl_label rd0" ms-class-1="bg-color-6:view==1" ms-class-2="bg-color-4:view!=1" ms-click="view_toggle(1)">日历</a>
                        <a class="cl_label rd0" ms-class-1="bg-color-6:view==2" ms-class-2="bg-color-4:view!=2" ms-click="view_toggle(2)">地图</a>
                    </span>
                <b class="cl_label bc-3">{{now_str}}</b>
                <!-- <em class="today">　{{now_str}}　{{now_str_lunar}}</em> -->
                &nbsp;
                &nbsp;
                <a class="date_arrow date_prev" ms-click="month_prev()">&lt;</a>
                <!--<a class="date_picker" id="date_picker"> 2016年03月 <i></i> </a>-->
                <a class="date_picker"><b id="date_picker">{{yyyy_mm_dd}}</b><span>{{year_month}} <i></i></span></a>
                <!--<a class="date_picker" id="date_picker"></a>-->
                <a class="date_arrow date_next" ms-click="month_next()">&gt;</a>

            </h1>
            <div class="main no_scroll">
                <div class="main_table_cont">
                    <table>
                        <tr>
                            <td class="ll">
                                <div class="cont">
                                    <div ms-class-1="showing:view==1" class="calendar" ms-class-2="more_day:datelist.length<35">
                                        <div class="week_cont"><em>一</em> <em>二</em> <em>三</em> <em>四</em> <em>五</em> <em>六</em> <em>日</em> </div>
                                        <div class="day_view" ms-controller="day_view" ms-class-2="more_day:datelist.length>35">
                                            <div
                                                    class="day_wrapper"
                                                    ms-repeat="datelist"
                                                    ms-class-1="diabled:el.outMonth!=0"
                                                    ms-class-2="today:el.ymd == today"
                                                    ms-class-3="cur:el.ymd==ymd"
                                                    ms-click="day_select(el)"
                                                    ms-attr-a="today"
                                                    ms-attr-b="el.ymd"
                                            >
                                                <div>
                                                    <i class="g"></i>
                                                    <span class="day">{{el.label}}</span>
                                                    <span class="yinli">{{el.ch_date}}</span>
                                                    <div class="list">
                                                        <a
                                                                class="item"
                                                                ms-repeat-ell="el.reclist"
                                                                ms-attr-index = "$index"
                                                                ms-visible="$index<2"
                                                                ms-class-1="loc:ell.geom"
                                                                ms-css-background-color="ell.topiccolor"
                                                        >{{ell.title}}</a>

                                                        <div class="addtion_shower">
                                                            <em ms-visible="el.reclist.length>2">……</em>
                                                            <a ms-if="el.reclist.length == 1 && el.reclist[0].geom" class="cl-6"><i></i>{{el.reclist[0]?el.reclist[0].addrname:''}}</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div ms-class-1="showing:view==2" id="mapCont"></div>
                                </div>
                            </td>
                            <td class="rr td_rr">
                                <div class="recoder_shower" ms-controller="rec_shower">

                                    <div class="blank_box" ms-visible="!day_rec_list.length">
                                        <div class="date">2016年3月21日 <br> 星期二 </div>

                                        <i class="ele e1"></i>


                                        <div class="talk">
                                            飞呀飞呀！飞过整个宇宙，很遗憾今日没有留下记录。您可以选择一个专题，并创建一条记录。
                                        </div>

                                        <a href="${ctx}/topic/totopic" class="lk" ms-click="removecur">进入专题</a>

                                        <i class="ele e2"></i>
                                    </div>

                                    <div class="item" ms-visible="day_rec_list.length">
                                        <table>
                                            <tr>
                                                <td class="user">
                                                    <a>
                                                        <i class="header-img"><img ms-attr-src="rec.headimg?(p+rec.headimg):blankimg"></i>
                                                        <em>{{rec.username}}</em><!--用户名称头像-->
                                                    </a>
                                                </td>
                                                <td class="date">
                                                    <h3>{{rec.occurtime|date("yyyy年MM月dd日")}}</h3>
                                                    <p>{{rec.occurtime|date("EEEE")}}</p>
                                                    <a>{{rec.addrname||"地址未标注"}}</a>
                                                </td>
                                            </tr>
                                        </table>
                                        <h2>{{rec.title}}</h2>

                                        <div class="illu" ms-visible="rec.coverturl"><img ms-if="rec && rec.coverturl" ms-attr-src="co.getThumb(rec.coverturl,295)"></div>
                                        <p class="desc">
                                            {{rec.description}}
                                            <br>
                                            <a ms-attr-href="${ctx}/record/toRecord/{{rec.id}}" class="lk" ms-click="removecur">详情</a>
                                        </p>
                                    </div>

                                    <div class="ctrl"  ms-visible="day_rec_list.length">
                                        <em class="arrow left"  ms-click="prev()">&lt;</em>
                                        <em class="arrow right" ms-click="next()">&gt;</em>

                                        <a
                                                ms-repeat="day_rec_list"
                                                ms-class-1="cur:$index==current_rec_index"
                                                ms-click="change_index($index)"
                                        ></a>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
    <div id="popup" class="ol-popup-nowrap">
      <a href="#" id="popup-closer" class="ol-popup-closer"></a>
      <div id="popup-content"></div>
    </div>
</body>
</html>