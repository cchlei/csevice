<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html><html>
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title></title>
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
                <span class="uimg"><img src="http://oss.trmap.cn/rs/download/2034c24d-ca61-446b-9adf-e82f236490ec-d1442538290056" alt=""></span>
                <span class="uname"><shiro:principal property="username"></shiro:principal></span>
                <i>&#xe615;</i>

                <span class="menu">
                    <span class="inner">
                        <a href="${ctx}/logout"><b>&#xe616;</b>注销</a>
                        <a href="#"><b>&#xe617;</b>用户信息</a>
                        <a href="#"><b>&#xe610;</b>其他操作</a>
                        <a href="#"><b>&#xe610;</b>其他</a>
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