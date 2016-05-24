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
	<title>系统消息</title>
	<link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
	<link rel="stylesheet" href="${ctx}/assets/css/service_query.css"/>
	<script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode"  main="${ctx}/assets/js/service_query"></script>
    <script>
        var service_query_ajax = ctx+"/entrelmap/getreleaselist";
    </script>
</head>
<body>
<div class="navright">
    <div class="fwtop"><em>系统消息</em><span>退出</span></div>
    <div class="service_query">
       	<h1>敬请期待!!!</h1>
    </div>
</div>
</body>
</html>