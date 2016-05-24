<!DOCTYPE html> <html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<meta charset="UTF-8">
<meta name="renderer" content="webkit" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<title>账户中心</title>
	<%@ include file="/assets/global.jsp"%>

	<link rel="stylesheet" href="${ctx}/assets/css/comm.css" />
<link rel="stylesheet" href="${ctx}/assets/css/account.css" />
<script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode"
	main="__/js/account"></script>
</head>
<body>
	<i class="cl_loading page"></i>
	<div class="root no_col_1 no_top" ms-controller="root">

		<div class="col2  account">
			<div class="account_name">
				<h2>账户管理</h2>
			</div>
			<ul>
				<li><a href="${ctx }/account/info" class="blue_account" target=account>概况</a></li>
				<li><a href="http://a.trmap.cn/p/toupdate#no_top"
					class="green_account" target=account>个人设置</a></li>
			</ul>
		</div>
		<div class="mainCont col3">
        	<iframe  src="${ctx }/account/info" frameborder="0" name=account id=account style="width:100%;height:100%;"></iframe>
   		 </div>
	</div>
</body>
</html>