<!doctype html><html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title></title>
    <%@ include file="/static/global.jsp"%>
    <link rel="stylesheet" href="${ctx}/static/css/qtucenter.css"/>
    <script src="${ctx}/static/libs/cseajs/csea$.js" id="seajsnode"></script>
    <script>
        seajs.usep("rt/qtucenter");
    </script>

</head>
<body>
<body>
    <div class="root">
        <div class="top_ban">
            <a href="#" class="logo"></a>
            <span class="user_opt">
            	<span class="uimg"><img src="${headimg}" alt="<shiro:principal></shiro:principal>"></span>
                <span class="uname"><shiro:principal></shiro:principal></span>
                <i>&#xe615;</i>

                <span class="menu">
                    <span class="inner">
                        <a href="#"><b>&#xe617;</b>用户信息</a>
                        <a href="${ctx}/logout"><b>&#xe616;</b>注销</a>
                    </span>
                </span>
            </span>
        </div>
        
        <div class="left_ban">
            <ul class="menu_show"></ul>
            <ul class="menu_collpase"></ul>

        </div>
        <div class="main"> <div class="fc"> <iframe id="frame" src="about:blank" frameborder="0"></iframe> </div> </div> </div>
</body>

</html>