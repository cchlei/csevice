<!doctype html><html class="loading">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.trgis.trmap.www.config.ConfigHolder"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ include file="/global.jsp"%>
<%-- <%
String path = request.getContextPath();
String ctx = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> --%>
<head>
	<!--[if lte IE 8]>
	<script> location.href = "index_ie.html"; </script>
	<![endif]-->
	<!--[if gte IE 9]>
	<script> location.href = "http://www.trmap.cn/"; </script>
	<![endif]-->
	<meta name="renderer" content="webkit" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="天润云地图，开放位置云服务，融合地理大数据。专业服务团队，降低使用门槛，提升企业服务，营造地理信息创新服务模式,助力‘地理信息+’全面推广" />
<meta name="keywords" content="地图,云地图,天润云,天润云地图,天润云平台,地理信息,地理信息系统,地理信息云服务,地理信息云平台,位置标注,位置云服务,位置服务,档案管理,移动GIS,移动位置服务,商业地图,商业地图服务,地图服务,企业地图服务,个人地图,个人地图服务,专题地图,地理信息+,一张图,智慧城市,智慧社区,智慧旅游" />
<meta property="qc:admins" content="4045466677642510636" />
<title>天润云地图-位置服务云平台,营造地理信息创新服务模式,助力"地理信息+"全面推广</title>
<link rel="stylesheet" href="css/animate.css">
<link rel="stylesheet" href="css/common.css"/>
<link rel="stylesheet" href="css/qtmap.css"/>
<link rel="stylesheet" href="css/css.css"/>
<link rel="stylesheet" href="css/index.css">

<script src="js/cseajs/csea$.js" id="seajsnode" main="_/qtmap"></script>
<script type="text/javascript" charset="utf-8" src="AN/edge_includes/edge.5.0.1.min.js"></script>
<!--page3-->
<script src="js/jquery.featureCarousel.js"></script>
<!--page4-->
<script src="js/jquery.adipoli.min.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="js/page5.js"></script>

</head>
<body class="_index">
	<i class="loading"></i>

	<em class="back_top"></em>

	<ul id="pagelist">
		<li class="section active" id="sec1">
			<div class="navbar">
				<div class="mc">
					<a href="#" class="logo"></a>
<!-- 					<span class="webname">天润云地图</span> -->
					<span class="nav"> 
						<a href="#">个人</a> 
						<a href="#">企业</a> 
						<a href="#">行业</a>
						<a href="#">云服务</a> 
						<a href="#">开发者</a> 
						<a href="#">联系我们</a>
					</span> 
					<span class="btns">
						<shiro:authenticated>
							<%
								// 根据用户的类型直接跳进个人中心
								String url = ConfigHolder.getCenterUrl();
								response.sendRedirect(url);
							%>
						</shiro:authenticated>
						<shiro:notAuthenticated>
							<a href="<%=ConfigHolder.getLoginUrl()%>" target="_self">登录</a>
							<a href="http://a.trmap.cn/p/regist.jsp">注册</a>
						</shiro:notAuthenticated>
					</span>
				</div>
			</div>

			<div class="IndexPage1"></div>
		</li>

		<li class="section" id="sec2">
			<div class="stage">
				<div class="view">
					<img _src="images/index_sec2_bg.png" />
				</div>
			</div>
			<div class="padCont">
				<div class="ipad">
					<img src="images/ipad1.png">
					<div id="ipad_screen">
						<div class="view">
							<img _src="images/index_sec2_bg.png" />
						</div>
					</div>
				</div>
			</div>

			<div class="box">
				<div class="gryy">
					<img src="images/page2_1.png">
				</div>
				<div class="gryy_x">
					<img src="images/page2_2.png">
				</div>
			</div>
		</li>


		<li class="section" id="sec3">
			<h3>
				<em></em><i></i>
			</h3> <i class="lightWheel"></i> <i class="round_white"></i>
			<ul class="cring">
				<li class="wanshan">
					<div class="tb">
						<div class="td">
							<i></i>
							<h5>完善的地理信息服务，融合创新应用模式</h5>
							<h4>完善</h4>
						</div>
					</div>
				</li>
				<li class="zhuanye">
					<div class="tb">
						<div class="td">
							<i></i>
							<h5>专业地理信息厂商，提供专业服务支持</h5>
							<h4>专业</h4>
						</div>
					</div>
				</li>
				<li class="jiandan">
					<div class="tb">
						<div class="td">
							<i></i>
							<h5>无需基础建设、直接使用服务</h5>
							<h4>简单</h4>
						</div>
					</div>
				</li>
			</ul>

			<div class="carousel-container">



				<div class="qtim1">
					<i class="qtimg1 qtbottom"></i>
				</div>
				<div class="qtim2">
					<i class="qtimg2 qtleft"></i>
				</div>
				<div class="qtim3">
					<i class="qtimg3 qtleft"></i>
				</div>
				<div class="qtim4">
					<i class="qtimg4 qtleft"></i>
				</div>
				<div class="qtim5">
					<i class="qtimg5 qtbottom"></i>
				</div>
				<div class="qtim6">
					<i class="qtimg6 qtbottom"></i>
				</div>
				<div class="qtim7">
					<i class="qtimg7 qtleft"></i>
				</div>
				<div class="qtim8">
					<i class="qtimg8 qtleft"></i>
				</div>
				<div class="qtim9">
					<i class="qtimg9 qtleft"></i>
				</div>
				<div class="qtim10">
					<i class="qtimg10 qtbottom"></i>
				</div>
			</div>
		</li>
		<li class="section" id="sec4">
			<div class="gryy">
				<img src="images/page4_1.png">
			</div>
			<div class="effect-container">
				<ul>
					<li><a href="theme_history.html"><img class="img-style row1"
							src="images/page4_2.png" /><span>历史文化</span></a><img
						src="images/zhezhao.png" class="abb" style="display: none;" /></li>
					<li><a href="theme_community.html"><img class="img-style row2"
							src="images/page4_3.png" /><span>数字社区</span></a></li>
					<li><a href="theme_travel.html"><img class="img-style row3"
							src="images/page4_4.png" /><span>智慧旅游</span></a></li>
				</ul>
				<div class="clear"></div>
			</div>
			<div class="gdhy">
				<p>
					<a href="#">更多行业 >></a>
				</p>
			</div>
		</li>
		<li class="section" id="sec5">
			<div class="gryy">
				<img src="images/page5_1.png">
			</div>
			<div id="lou">
				<div id="cloud1" class="cloud"></div>

			</div>
			<div class="page5">
				<ul class="page5_left">
					<li><div class="pag5">
							<a href="#"><img src="images/icon1.png"><span>开放服务</span></a>
						</div></li>
					<li><div class="pag5">
							<a href="#"><img src="images/icon2.png"><span>数据存储</span></a>
						</div></li>
					<li><div class="pag5">
							<a href="#"><img src="images/icon3.png"></a>
						</div></li>
					<li><div class="pag5">
							<a href="#"><p>
									<img src="images/icon4.png">
								</p>
								<p>
									<span>企业应用二次开发</span>
								</p></a>
						</div></li>
					<div class="clear"></div>
				</ul>
				<ul class="page5_right">
					<li><div class="pag5">
							<a href="#"><img src="images/icon5.png"><span>地图服务</span></a>
						</div></li>
					<li><div class="pag5">
							<a href="#"><img src="images/icon6.png"><span>兴趣点服务</span></a>
						</div></li>
					<li><div class="pag5">
							<a href="#"><img src="images/icon7.png"><span>路网分析服务</span></a>
						</div></li>
					<li><div class="pag5">
							<a href="#"><img src="images/icon8.png"><span>拓扑分析</span></a>
						</div></li>
					<li><div class="pag5">
							<a href="#"><img src="images/icon9.png"><span>几何服务</span></a>
						</div></li>
					<li><div class="pag5">
							<a href="#"><img src="images/icon10.png"><span>地址编码服务</span></a>
						</div></li>
					<div class="clear"></div>
				</ul>
				<div class="clear"></div>
			</div>
		</li>
		<li class="section fp-auto-height" id="sec6">
			<img class="bg" src="images/index_bot_bg.png" alt="">
			<div class="mc">
				<table>
					<tr>
						<td style="width: 540px;">
							<!--<h3>天润云地图</h3>-->
							<img src="images/index_bot_tx.png" alt="" class="index_bot_tx">
							<!--<img src="images/hehua.png" alt="" class="hehua" style="margin-top: -50px; margin-left: 90px;">-->
						</td>
						<td style="width: 137px;">
							<h3>常见问题</h3>
							<p><a href="#">使用帮助</a></p>
							<p><a href="#">服务协议</a></p>
							<p><a href="#">关于我们</a></p>
							<p><a href="#">商务合作</a></p>
						</td>
						<td style="width: 225px;">
							<h3><a href="#" onclick="contactUs()" style="color: #A0D4F9;" class="weibo">联系我们</a></h3>
							<p><a href="http://weibo.com/trgis" target="_blank" class="weibo"><i></i>天润科技微博</a></p>
							<p><a href="#" class="qq"><i></i>178607699</a></p>
							<p><a href="mailto:contact@trmap.cn" class="mail"><i></i>contact@trmap.cn</a></p>
							<p><a class="yijian" href="javascript:;"> <i></i>意见反馈</a></p>
						</td>
						<td class="sqcode">
							<i class="sqcode"><img src="images/qtmap_sqcode.jpg" alt=""></i>
						</td>
					</tr>
				</table>
			</div>

			<h2 class="cr"><p>陕ICP备12000810号 版权所有：<a href="//trgis.com" target="_blank">陕西天润科技股份有限公司</a> <i></i></p></h2>
		</li>

	</ul>
	
</body>
</html>