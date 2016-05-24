<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ include file="/assets/global.jsp"%>
<script src="${ctx}/js/jquery.js"  ></script>
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>请重新发送激活邮件</title>
    <script src="${ctx}/js/jquery.js"  ></script>
    <link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/prompt.css"/>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode" ></script>
        <script type="text/javascript">
        $(function(){
            var h = $(window).height();
            $(".email_activation").height(h);
        })
    </script>
</head>
<body  onload="show()">  
<div class="email_activation">
    <img src="${ctx}/assets/images/p_yxjh.jpg" alt="">
    <div class="zccenter">
        <div class="tishi">
            <h3>账号激活邮件发送失败！</h3>
            <p>${user.username}</p>
            <p class="again"><a id='resend_link' href="#" onclick="javascript:resend('${userId}')">重新发送</a><span>剩余<em class="showbox">120</em>秒</span></p>  
        </div> 
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    //设置超时时间为120秒钟
    var timeout = 120;
    function show() {
        var showbox = $(".showbox");
        showbox.html(timeout);
        timeout--;
        if (timeout == 0) {
            window.opener = null;
            window.location.href = "index.html";
        }
        else {
            setTimeout("show()", 1000);
        }
    }

</script>
<script type="text/javascript">
var resend;//重发邮件方法
var toggle;//60s重试
var resend_link;
var djs_sec=10;
var djsFun;

$(function(){
	resend=function(id){
		$.post('${ctx}/entaccount/resendmail',{
			'userId':id
		});
		toggle();
	}
	toggle=function(){
		resend_link = $('#resend_link').attr("onclick");
		$('#resend_link').attr("onclick","javascript:void(0);")
		//开启倒计时
		setTimeout(djsFun,1000,djs_sec,resend_link); 
	}
	djsFun = function(djs_sec,resend_link){
		if(--djs_sec>0){
			$('#resend_link').html('');
			$('#resend_link').html(djs_sec+'s后重新发送');
			setTimeout(djsFun,1000,djs_sec,resend_link);
		} else {
			$('#resend_link').html('重新发送');
			$('#resend_link').attr("onclick",resend_link);
		}
	}
})
</script>
