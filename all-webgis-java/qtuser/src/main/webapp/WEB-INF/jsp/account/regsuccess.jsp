<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/global.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="renderer" content="webkit" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<link rel="stylesheet" href="http://www.trmap.cn/css/qtmap.css" />
<link rel="stylesheet"
	href="http://www.trmap.cn/css/notcie_page_comm.css" />
<script src="http://www.trmap.cn/js/cseajs/csea$.js" id="seajsnode"
	main="http://www.trmap.cn/js/notice_page_comm"></script>
<title>天润云地图-注册成功</title>
</head>
<body>
	<div class="navbar">
		<div class="mc">
			<a href="#" class="logo"></a> <span class="webname">天润云地图</span>
		</div>
	</div>


	<div id="page_cont">
		<div class="midcont mc">
			<table>
				<tr>
					<td style="padding-right: 8em; width: 30%;"><img
						src="http://www.trmap.cn/images/illu_103.png" alt=""></td>
					<td style="line-height: 2.5em;" class="lay_a">
						<h3>恭喜您，注册成功！</h3>

						<p class="gap"></p>
						<p class="gap"></p>
						<p class="gap"></p> 我们已经向您的注册邮箱发送了一封激活邮件，请点击邮件中的激活链接完成帐号激活！ <br>
						<a style="color: #FF8F5B; margin: 0.5em 0;">您的注册邮箱是：${user.email}</a>

						<p class="gap"></p>
						<p class="gap"></p>
						<p class="gap"></p> <a href="${ctx}/logout" class="btn" >登录</a>
						&nbsp;&nbsp;&nbsp;

						<p class="gap"></p>
						<p class="gap"></p>
						<p class="gap"></p>

					</td>
				</tr>
			</table>
			<br />

		</div>
	</div>

	<div class="bott">
		<p class="mc botmenu">
			<a href="#">关于天润云</a> <i></i> <a href="#">常见问题</a> <i></i> <a href="#">服务保障</a>
			<i></i> <a href="#">加入我们</a>
		</p>

		<p class="copyright">
			陕ICP备12000810-2号 版权所有： <a href="//www.trgis.com" target="_blank">陕西天润科技股份有限公司</a>
			&nbsp;&nbsp; 地址:西安市雁塔北路8号 &nbsp;&nbsp; 邮箱<a
				href="mailto:contact@trgis.com">contact@trgis.com</a>
		</p>

	</div>

</body>
</html>
