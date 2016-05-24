<!doctype html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ include file="/assets/global.jsp"%>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>重置密码</title>
    <link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/prompt.css"/>
  <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode" ></script>
    <script type="text/javascript">
        $(function(){
            var h = $(window).height();
            $(".repassword").height(h);
        })
    </script>
</head>
<body>  
<div class="repassword">
    <div class="zccenter">
        <h3><span>${user.username}</span>，您好！</h3>
        <p>您申请重置密码，请在72小时内完成设置。如果未做任何操作，系统将保留原密码。</p>
        <p><a href="${ctx}/entaccount/toresetpass">立即重置密码</a></p>
        <p>如果点击无效，请复制下面网页地址到浏览器地址栏中打开：</p>
    </div>
    <img src="${ctx}/assets/images/p_bg4.jpg" alt="">
</div>
</body>
</html>
