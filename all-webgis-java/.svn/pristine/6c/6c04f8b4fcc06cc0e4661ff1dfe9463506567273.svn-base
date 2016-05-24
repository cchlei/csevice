<!doctype html><html>
<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
	<%@ include file="/assets/global.jsp"%>
    <title>我的企业信息</title>
    <style>
	#filePicker > div[id]{opacity:0;}
	</style>
    <link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/enterprise_summ.css"/>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode" main="${ctx}/assets/js/enterprise_frame"></script>
	<script type="text/javascript" src="${ctx}/assets/libs/webuploader/webuploader.js"></script>
    <script>
        var  nav_data_url = "${ctx}/assets/data/enterprise_summ_list_data.json";
        
        /**
         * 认证撤销,电话号码和邮件的修改controller
         * @type {string}
         */
        var profile_modify_ajax = "";
        var cancel_url = "${ctx}/entcainfo/revoke";
        var service_information_ajax ="${ctx}/entamount/getamount";
    </script>

</head>
<body>
<div class="navright">
    <div class="top"><i></i><span>退出</span></div>
    <!-- 基本信息 -->
    <div class="conter" style="margin-top: 132px;">
        <h3 typename="jbxx"><i></i>基本信息</h3>
        <div class="basic">
            <div class="basicleft">
		            <div class="images"><img src="http://oss.trmap.cn/rs/download/${user.headimg}"></div>
		            <span id="filePicker">修改企业LOGO</span>
            </div>
            <div class="basicright">
                <ul>
                    <li name="name">企业名称：${user.name}</li>
                    <li>账号名称：${user.username}</li>
                     <c:if test="${empty cainfo.castatus}">
                    	<li>账户状态：<span class="putin" _href="${ctx}/entcainfo/cainfo">提交申请</li>
					</c:if>
                    <!-- 账户再次申请 -->
					<c:if test="${cainfo.castatus == '3'}">
					<input type="hidden" id="remarks" value="${cainfo.remarks}"/>
                    	<li>账户状态：<span class="reject">认证未通过！</span> <span _href="${ctx}/entcainfo/update" class="againputin">再次申请</span></li>
					</c:if>
					<!-- 待审核 -->					
					<c:if test="${cainfo.castatus == '0'}">
                    	<li>账户状态：<span class="rztrue" style="padding-left: 0px;padding-right: 0px;">待审核</span> <span style="color:#4c78d5"  class="cancel">取消</span></li>
					</c:if>
                    <!-- 已取消 -->
					<c:if test="${cainfo.castatus == '1'}">
						<li>账户状态：<span class="rztrue" style="padding-left: 0px;padding-right: 0px;">已取消！</span> <span _href="${ctx}/entcainfo/update" class="againputin">再次申请</span></li>
					</c:if>
                    <!-- 已认证 -->
                    <c:if test="${cainfo.castatus == '2'}">
                    	<li>账户状态：<span class="rztrue">已认证</span></li>
					</c:if>
                    <li>安全操作：<span class="resetpass">重置登录密码</span></li>
                </ul>
                <ul>
                    <li>企业邮箱：<span style="color:#333" class="tx" name="email" >${user.email}</span> </li>
                    <li>企业电话：<span style="color:#333" class="tx" name="mobile">${user.mobile}</span> <span class="input"><input name="phone" type="text" ajax_valid="本电话已经在该平台注册过" placeholder="请输入企业电话"/></span><span class="revisephone" style="color:#333">修改</span></li>
                </ul>
                <div class="clear"></div>
            </div> 
        </div>
    </div>
    <!-- 认证信息 -->
    <div class="conter">
        <h3 typename="rzxx"><i></i>认证信息</h3>
        <div class="authentica">
            <ul>
                <li>
                    <p style="min-width: 444px;">企业名称：<span>${cainfo.enterpriseName}</span></p>
                    <p>执照注册号：<span>${cainfo.enterpriseId}</span></p>
                    <p>机构代码：<span>${cainfo.orgId}</span></p>
                </li>
				<li>
                    <p style="width: 444px;">法人代表姓名：<span>${cainfo.bossName}</span></p>
                    <p>法人证件号码：<span>${cainfo.bossIdentifyId}</span></p>
                    <p>证件有效期：<span>${cainfo.bossIdentifyDate}</span></p>
                </li>
				<li>
                    <p style="width: 444px;">运维联系人：<span>${cainfo.managerName}</span></p>
                    <p>联系人证件号码：<span>${cainfo.managerIdentifyId}</span></p>
                    <p class="cade">证件有效期：<span>${cainfo.managerIdentifyDate}</span></p><p>联系方式：<span>${cainfo.managerPhone}</span></p>
                    </li>
            </ul>
        </div>
    </div>
    <!-- 服务信息 -->
    <div class="conter">
        <h3 typename="fwxx"><i></i>服务信息</h3>
        <div class="service" ms-controller="wrap">
            <ul>
                <li class="fbfw">发布服务<p>{{data.publish}}</p></li>
                <li class="hqfw">获取服务<p>{{data.obtain}}</p></li>
                <li class="zyjs">租用计算资源<p>{{data.rent}}</p></li>
                <li class="dspq">待审批请求<p>{{data.approval}}</p></li>
            </ul>
        </div>
    </div>
</div>
<div class="clear"></div>
</body>
</html>