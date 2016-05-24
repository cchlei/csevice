<!doctype html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ include file="/global.jsp"%>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>频繁注册</title>
    <link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/prompt.css"/>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode" ></script>
    <script type="text/javascript">
        $(function(){
            var h = $(window).height();
            $(".zc_fail").height(h);
        })
    </script>
</head>
<body>  
<div class="top">
    <div class="mc">
        <a href="http://www.trmap.cn" class="logo"></a>
    </div>
</div>
<div class="zc_fail">
    <img src="${ctx}/assets/images/p_zcsb.jpg" alt="">
    <div class="zccenter">
        <div class="tishi">
            <h3>您的操作过于频繁!</h3>
            <p class="hy">欢迎您加入到天润云地图平台，您的操作过于频繁，请稍后再试。</p>  
            <p class="again"><span><a href="${ctx}/e/regist.jsp">重新注册</a></span><a href="${www}">返回首页</a></p>  
        </div> 
    </div>
</div>
 <div class="foot">
    <p class="right">陕ICP备12000810-2号　版权所有：陕西天润科技股份有限公司</p>
</div>
</body>
</html>
