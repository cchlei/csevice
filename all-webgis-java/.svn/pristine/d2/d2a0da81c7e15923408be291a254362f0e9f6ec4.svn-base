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
    <title>忘记密码</title>
    <link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/prompt.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/qtmap.css"/>
    <script src="${ctx}/assets/libs/cseajs/csea$.js"  main="${ctx}/assets/js/forgotpassword"></script>
    <script type="text/javascript">
        $(function(){
            var h = $(window).height();
            $("#prompt_center,#procenter,.zc_success").height(h-215);
        })
    </script>
</head> 
<body>  
<div class="top">
    <div class="mc">
        <a href="http://www.trmap.cn" class="logo"></a>
    </div>
</div>
<div id="prompt_center">
    <div class="resetpassword">
    	<div class="topp1"></div>
        <form class="zccenter1" action="${ctx}/findUser" method="post">
            <ul>
                <li><span>请输入邮箱地址：</span><input id="email" name="email"  class="ipt email" placeholder="请输入邮箱地址"  value="" size="25" autocomplete="off"></li>
                <li class="item safecode">
                            <span>验证码:</span>
                            <i class="vali"></i>
                            <input id="validcode" name="validcode" ajax_valid="验证码错误" placeholder="请输入验证码" style="width:79px"/>
                            <i class="inputip" style="display: inline;  padding-left: 0em; padding-right: 0.1em;width:79px;" ></i>
                            <img  id="randImg" src="${ctx}/random" style="width:79px;">
                            <a onclick="reRand();" style="cursor: pointer;">看不清？点击换一张</a>
                </li>
                <li class="next">下一步</li>
            </ul>
        </form>
    </div>
</div>
<div class="foot">
    <p class="right">陕ICP备12000810-2号　版权所有：陕西天润科技股份有限公司</p>
</div>
</body>
</html>
