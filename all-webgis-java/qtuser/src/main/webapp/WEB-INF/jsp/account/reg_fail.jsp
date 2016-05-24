<!doctype html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ include file="/static/global.jsp"%>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>注册失败</title>
    <link rel="stylesheet" href="${ctx}/static/css/tr_comm.css"/>
    <link rel="stylesheet" href="${ctx}/static/css/prompt.css"/>
    <script src="${ctx}/static/libs/cseajs/csea$.js" id="seajsnode" ></script>
    <link rel="stylesheet" href="http://www.trmap.cn/css/qtmap.css" />
    <link rel="stylesheet" href="http://www.trmap.cn/css/notcie_page_comm.css" />
    <script src="http://www.trmap.cn/js/cseajs/csea$.js" id="seajsnode"
	main="http://www.trmap.cn/js/notice_page_comm"></script>
    <script src="${ctx}/static/libs/cseajs/csea$.js" id="seajsnode" ></script>
    <script type="text/javascript">
        $(function(){
            var h = $(window).height();
            $(".zc_fail").height(h);
        })
    </script>
</head>
<body>  
<div class="navbar">
		<div class="mc">
			<a href="#" class="logo"></a> <span class="webname">天润云地图</span>
		</div>
	</div>
<div class="zc_fail">
    <img src="${ctx}/static/images/p_zcsb.jpg" alt="">
    <div class="zccenter">
        <div class="tishi">
            <h3>您的账号注册失败!</h3>
            <p class="yh">${user.username}</p>
            <p class="hy">欢迎您加入到天润云地图平台，由于您填写的信息有误，所以注册失败。</p>  
            <p class="again"><span><a href="${www}/register.html">重新注册</a></span><a href="${www}">返回首页</a></p>  
        </div> 
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
