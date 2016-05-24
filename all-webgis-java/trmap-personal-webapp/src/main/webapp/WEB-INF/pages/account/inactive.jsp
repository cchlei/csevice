<!DOCTYPE html><html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head lang="en">
<meta charset="UTF-8">
<title>账号未激活</title>
<%@ include file="/static/global.jsp"%>
<link rel="stylesheet" href="//cdn.staticfile.org/twitter-bootstrap/3.3.1/css/bootstrap.min.css" />
<script type="text/javascript" src="//cdn.staticfile.org/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="//cdn.staticfile.org/twitter-bootstrap/3.3.1/js/bootstrap.min.js"></script>
<style>
body{
	font-family: "微软雅黑"
}
</style>
</head>
<body>
	<div class="topbar">
		<div class="mc">
			<a href="#" class="logo"></a>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid text-center">
			<h2>亲爱的用户您的账号已注册成功，但还未激活，请前往${email}邮箱激活!</h2>
		</div>
		<div class="row-fluid text-center">
			<div class="col-xs-4"></div>
			<div class="col-xs-4">
				<p>
					收不到邮件？
					<ul>
						<li><a id='resend_link' href="#" onclick="javascript:resend('${userId}')">重新发送</a></li>
						<li>换一个邮箱试试?</li>
					</ul>
				</p>
			</div>
			<div class="col-xs-4"></div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
var resend;//重发邮件方法
var toggle;//60s重试
var resend_link;
var djs_sec=10;
var djsFun;
$(function(){
	resend=function(id){
		$.post('${ctx}/account/resendmail',{
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