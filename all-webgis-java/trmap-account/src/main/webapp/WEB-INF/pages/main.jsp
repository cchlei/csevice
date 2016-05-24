<!doctype html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="renderer" content="webkit" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ include file="/assets/global.jsp"%>
<title>企业信息</title>
<link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css" />
<link rel="stylesheet" href="${ctx}/assets/css/enterprise_summ.css" />
<script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode" main="${ctx}/assets/js/enterprise_frame"></script>
<script type="text/javascript" src="${ctx}/assets/libs/webuploader/webuploader.js"></script>
<script>
	var nav_data_url = ctx+"/menu.action";
</script>

</head>
<body>
	<div class="navleft">
		<div class="top">
			<i></i><em></em>
		</div>
	</div>
	<div class="navright">
		<div class="top"><i></i><span>退出</span></div>
		<iframe frameborder="0" id="main" src="about:blank"></iframe>
	</div>
	<div class="clear"></div>
</body>
</html>