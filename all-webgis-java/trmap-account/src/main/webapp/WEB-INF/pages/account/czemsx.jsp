<!doctype html><html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ include file="/global.jsp"%>
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>链接失效！</title>
    <link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/prompt.css"/>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode" ></script>
    <script type="text/javascript">
        $(function(){
            var h = $(window).height();
            $(".qyzh_activation").height(h);
        })
    </script>
</head>
<body >  
<div class="top">
    <div class="mc">
        <a href="http://www.trmap.cn" class="logo"></a>
    </div>
</div>
<div class="qyzh_activation">
    <div class="zccenter">
        <i></i>
        <div class="tishi">
            <h3>对不起，您的邮件链接已失效！</h3>
        </div>
        <div class="clear"></div>   
    </div>
    <img src="${ctx}/assets/images/p_bg3.jpg" alt="" class="passchangerr">
</div>
<div class="foot">
    <p class="right">陕ICP备12000810-2号　版权所有：陕西天润科技股份有限公司</p>
</div>
</body>
</html>