<!DOCTYPE html><html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <title>激活成功</title>
    <link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/prompt.css"/>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode"></script>
    <script type="text/javascript">
        $(function(){
            var h = $(window).height();
            $(".zc_success").height(h);
        })
    </script>
</head>
<body  onload="show()">  
<div class="top">
    <div class="mc">
        <a href="http://www.trmap.cn" class="logo"></a>
    </div>
</div>
<div class="zc_success">
    <div class="cloud">
        <i class="cloud1"></i>
        <i class="cloud2"></i>
    </div>
    <div class="zccenter">
        <i></i>
        <div class="tishi">
            <h3>恭喜您，企业账号激活成功！</h3>
            <p><span class="showbox"></span><span>秒后自动关闭，您也可以立即</span><a href="${qtenterprise}/logout">登录</a></p>    
        </div>
        <div class="clear"></div>   
    </div>
    <img src="${ctx}/assets/images/p_bg1.jpg" alt="">
</div>
<div class="foot">
    <p class="right">陕ICP备12000810-2号　版权所有：陕西天润科技股份有限公司</p>
</div>
</body>
</html>
<script type="text/javascript">
    //设置超时时间为10000秒钟
    var timeout = 10;
    function show() {
        var showbox = $(".showbox");
        showbox.html(timeout);
        timeout--;
        if (timeout == 0) {
            window.opener = null;
            window.close();
        }
        else {
            setTimeout("show()", 1000);
        }
    }

</script>