<!DOCTYPE html> <html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>所有专题</title>
    <%@ include file="/assets/global.jsp"%>
    <link rel="stylesheet" href="${ctx}/assets/css/comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/mythemes.css"/>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode" main="__/js/theme_list"></script>
    <script>

    var pageCfg = {
            /*专题数量*/
            themes_nubmer:"${ctx}/topic/countTopic",

            /*专题数据*/
            theme_list:"${ctx}/topic/List",

            /*删除返回*/
            theme_del:"${ctx}/topic/delete"

        };
    </script>
</head>
<body>
<i class="cl_loading page"></i>

<div class="mainCont col3" ms-controller="themes">
    <div class="inner">
        <h1 class="page_name">{{topictitle|html}}</h1>
        {{addthemeurl|html}}
        <div class="main" >
            <div class="inner">
                <div class="theme_grid type2" ms-each="object">
                    <span ms-attr-sid="el.id">
                        <span href="#" class="inner">
                            <i class="place_holder"></i>
                            <span class="label" ms-css-background-color="el.topiccolor">
                            	<i ms-css-border-top-color="el.topiccolor" ms-css-border-right-color="el.topiccolor"></i>
                            	<b ms-css-border-left-color="el.topiccolor"></b>{{el.name}}
                           	</span>
                            <a class="img" ms-attr-href="${ctx}/topic/toinfo/{{el.id}}?open={{el.shareflag}}" target=_parent ><img ms-attr-src="co.getThumb(el.coverurl,380,368)" alt=""> </a>
                            <span ms-class-1="true:el.shareflag" ms-class-2="flase:!el.shareflag"></span>
                            <span class="tx_block">
                                <table>
                                    <tr>
                                        <td class="l">{{el.creatime}}</td>
                                        <td class="r">
                                            <i class="iconlink list">{{el.record}}</i>
                                            <i class="iconlink start">{{el.collect}}</i>
                                            <i class="iconlink del" ms-click="dele(el)">&nbsp;</i>
                                        </td>
                                    </tr>
                                </table>
                            </span>
                        </span>
                    </span>
                </div>
            </div>
            <ul id="themepage"></ul>
        </div>
    </div>
</div>
</body>
</html>