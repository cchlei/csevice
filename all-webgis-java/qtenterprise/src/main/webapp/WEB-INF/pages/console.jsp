<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ include file="/assets/global.jsp"%>
 <div class="top"><i></i><span>退出</span></div>
    <!-- 基本信息 -->
    <div class="conter">
        <h3 typename="jbxx"><i></i>基本信息</h3>
        <div class="basic">
            <div class="basicleft"><img src="${ctx}/assets/images/uimg.png"><span>修改企业LOGO</span></div>
            <div class="basicright">
                <ul>
                    <li>企业名称：陕西天润科技</li>
                    <li>账号名称：XXXXXXX</li>
                    <li>账户状态 <span class="putin">提交申请</span> <span class="cancel">取消</span></li>
                    <li>账户状态 <span class="reject">驳回？</span> <span class="againputin">再次申请</span></li>
                    <li>安全操作 <span class="resetpass">重置登录密码</span></li>
                </ul>
                <ul>
                    <li>企业邮箱：tianrun@tianrun.com</li>
                    <li>企业电话：029-87654321</li>
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
                <li><p>企业名称执：<span>陕西天润科技</span></p>  <p>照注册号：<span>1234566  </span></p>  <p>机构代码：<span>3245678</span> </p></li>
                <li><p>法人代表姓名：<span>天润科技</span></p> <p>法人证件号码：<span>123456  </span></p>  <p>证件有效期：<span>2015.8.19</span></p> </li>
                <li><p>运维联系人：<span>天润科技 </span></p> <p>联系人证件号码：<span>123456 </span></p>  <p>证件有效期   ：<span>123456 </span></p>  <p>联系方式：<span>029-87654321</span></p> </li>
            </ul>
        </div>
    </div>
    <!-- 服务信息 -->
    <div class="conter">
        <h3 typename="fwxx"><i></i>服务信息</h3>
        <div class="service">
            <ul>
                <li class="fbfw">发布服务<p>7,534</p></li>
                <li class="hqfw">获取服务<p>7,534</p></li>
                <li class="zyjs">租用计算资源<p>7,534</p></li>
                <li class="dspq">待审批请求<p>7,534</p></li>
            </ul>
        </div>
    </div>