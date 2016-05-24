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
    <title>对不起，您的企业账号激活失败！</title>
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
<body  onload="show()">  
<div class="qyzh_activation">
    <div class="zccenter">
        <div class="cloud">
        <i class="cloud1"></i>
        <i class="cloud2"></i>
    </div>
        <i></i>
        <div class="tishi">
            <h3>对不起，您的企业账号激活失败！</h3>
            <p><span>您可能需要：重新激活</span><span class="showbox">10000</span><span>5秒后自动关闭</span></p>    
        </div>
        <div class="clear"></div>   
    </div>
    <img src="${ctx}/assets/images/p_bg2.jpg" alt="">
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
           // window.location.href = "index.html";
            window.close();
        }
        else {
            setTimeout("show()", 1000);
        }
    }

</script>