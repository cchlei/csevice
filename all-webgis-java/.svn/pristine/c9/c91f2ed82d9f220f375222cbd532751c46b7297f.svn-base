<!DOCTYPE html> <html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <%@ page contentType="text/html; charset=UTF-8"%>
	<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
	<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
	<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
	<%@ include file="/assets/global.jsp"%>
    <title>分享给我</title>
    <link rel="stylesheet" href="${ctx}/assets/css/comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/mythemes.css"/>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode" main="${ctx}/assets/js/sharetome"></script>
    <script>
        var pageCfg = {
            /*专题数量*/
            themes_nubmer:"${ctx}/topic/countTopic",

            /*专题数据*/
            theme_collect_list:"${ctx}/topic/shareme",

            /*取消分享../data/success.json*/
            theme_collect_share:"${ctx}/collect/addAndDel"
			
        };
    </script>
</head>
<body>
    <i class="cl_loading page"></i>
    <div class="root no_top no_col_1_2">
          <jsp:include page="/assets/top.jsp"></jsp:include>
        <div class="col2">

            <ul class="sub_nav" ms-controller="number">
                <li>
                    <h3><i class="add"></i><i class="triangle"></i>我的专题 </h3>
                    <a href="${ctx}/login#"  	class="bg-color-1" >所有专题 <em>({{number.alltheme}})</em></a>
                    <a href="${ctx}/login#1"  	class="bg-color-2" >公开专题 <em>({{number.opentheme}})</em></a>
                    <a href="${ctx}/login#0" 	class="bg-color-3" >私密专题 <em>({{number.privatetheme}})</em></a>
                </li>

                <li>
                    <h3><i class="triangle"></i>发现 <i class="add"></i></h3>
                    <a href="${ctx}/topic/shareListView" class="bg-color-4">所有分享 <em></em></a>
                    <a href="#" class="bg-color-5">我的关注 <em></em></a>
                    <a href="${ctx}/collect/collectview" class="bg-color-6">我的收藏 <em></em></a>
                    <a href="#" class="bg-color-7">分享给我 <em></em></a>
                </li>
            </ul>
        </div>
        <div class="mainCont col3">
            <div class="inner" ms-controller="mycollect">
                <h1 class="page_name"><i class="ico_sharetome"></i>分享给我({{total}})</h1>
                <div class="main">
                    <div class="inner">
                        <div class="theme_grid type2" ms-each="collentdata">

                            <span ms-attr-sid="el.topic.id">
                            	<div class="inner">
                                    <i class="place_holder"></i>
                                    <span class="label" ms-css-background-color="el.topic.topiccolor"><i ms-css-border-top-color="el.topic.topiccolor" ms-css-border-right-color="el.topic.topiccolor"></i><b ms-css-border-left-color="el.topic.topiccolor"></b>{{el.topic.name}}</span>
                                    <a class="img"  target=_parent  ms-attr-href="${ctx}/topic/topulicinfo/{{el.topic.id}}" ><img ms-attr-src="co.getThumb(el.topic.coverurl,380,368,default_img)"></a>

                                    <span class="tx_block">
                                        <table>
                                            <tr>
                                                <td class="l user_el">
                                                    <i> <img ms-attr-src="co.getThumb(el.topic.user.headimg,26,26,default_headimg)"> </i>
                                                    <b>{{el.topic.user.username}}</b>
                                                </td>
                                                <td class="r" >
                                                	<i class="iconlink list">{{el.record}}</i>
                                                    <i class="iconlink start" ms-class-1="gray_star:!el.iscollect" ms-click="click_qxcollect(el)">{{el.collect}}</i>
                                                </td>
                                            </tr>
                                        </table>
                                    </span>
                                  </div>
                            </span>

                        </div>
                        <div id="theme_collect_page"></div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</body>
</html>