<!DOCTYPE html><html class="_register _enterprise_reg">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/global.jsp"%>
<head lang="en">
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>天润云地图-注册</title>
    <link rel="stylesheet" href="css/common.css"/>
    <link rel="stylesheet" href="css/qtmap.css"/>
    <script src="js/cseajs/csea$.js" id="seajsnode" main="_/register_enterprise"></script>
   
</head>
<body>
    <div class="root">
        <div class="navbar">
            <div class="mc">
                <a href="http://www.trmap.cn" class="logo"></a>
            </div>
        </div>

        <div class="page_content">
            <div class="mc">
                <h3 class="page_title">注册天润云地图账号 助理企业创新服务</h3>
                <p class="type_switch">
                    <a href="register.html"> <i>&#xe625;</i> 个人用户注册</a>
                    <a  href="register.jsp" class="cur"><i>&#xe626;</i> 企业用户注册</a>
                </p>

                <form action="${qtenterprise}/entaccount/register" method="post">
                    <ul>
                        <li class="item">
                            <em class="label">用户名:</em>
                            <span class="input"><i></i><input type="text" name="username" ajax_valid="用户名已经被占用" placeholder="长度6-20个字符"/></span>
                        </li>
                        <li class="item">
                            <em class="label">企业名称:</em>
                            <span class="input"><i></i><input type="text" name="enterprisename"  placeholder="长度6-20个字符"/></span>
                        </li>
                        <li class="item">
                            <em class="label">企业邮箱:</em>
                            <span class="input"><i></i><input name="email" type="text" ajax_valid="邮箱已存在" placeholder="请输入邮箱地址"/></span>
                        </li>
                        <li class="item">
                            <em class="label">企业电话:</em>
                            <span class="input"><i></i><input name="phone" type="text" ajax_valid="本电话已经在该平台注册过" placeholder="请输入企业电话"/></span>
                        </li>

                        <li class="item safecode">
                            <em class="label">验证码:</em>
                            <span class="input"><i></i><input name="safecode" ajax_valid="验证码不正确"   placeholder="请输入验证码"/></span><i class="inputip" style="display: inline;  padding-left: 0em; padding-right: 1em;"></i>
                            <img  id="randImg" src="${qtenterprise}/entaccount/random" >
                            <a onclick="reRand();">看不清？点击换一张</a>
                        </li>
                        <li class="item">
                            <em class="label">密码:</em>
                            <span class="input"><i></i><input name="password" type="password" placeholder="长度6-20个字符"/></span>
                        </li>
                        <li class="item qiangdu">
                            <em class="label">密码强度:</em>
                            <span class="input"> <span class="bar"></span> <b>强</b> </span>
                        </li>
                        <li class="item">
                            <em class="label">密码确认:</em>
                            <span class="input"><i>&#xe603;</i><input name="repassword" recheck="password" type="password" placeholder="请再一次输入密码"/></span>
                        </li>

                        <li class="item">
                            <em class="label">&nbsp;</em>
                            <input type="checkbox" checked="true"  id="argument_confirm">
                            <label for="argument_confirm">我已阅读并接受 </label><a target="_blank" href="${qtenterprise}/assets/pages/service_protocols.html">天润云地图服务协议</a>
                        </li>

                        <li class="item">
                            <em class="label"></em>
                            <span class="btn"><i class="i_plane"></i>&nbsp;&nbsp;提交</span>
                        </li>
                    </ul>
                </form>
            </div>
        </div>

        <div class="bot">
            <p>陕ICP备12000810-2号　版权所有：陕西天润科技股份有限公司</p>
        </div>


    </div>

</body>
</html>