<!DOCTYPE HTML><html class="framePage">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<head>
    <meta charset="UTF-8"/>
    <meta loca="首页|这里可自定义"/>
	<%@ include file="/assets/global.jsp"%>
    <link rel="stylesheet" href="${ctx}/assets/libs/ztree/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/index.css"/>
    <script type="text/javascript" src="${ctx }/assets/libs/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script src="${ctx}/assets/libs/ztree/jquery.ztree.core-3.5.min.js"></script>
    <script>
        //给左侧权限树赋值
// 		var zNodes = ${resultString};
//	目前先这样写，加上角色权限可改为从数据库读取
		var zNodes = [
                       {
                           "name": "账户管理",
                           "open": true,
                           "href": null,
                           "children": [
                               {
                                   "name": "用户的冻结、封停",
                                   "open": false,
                                   "href": ctx+"/user/userlist",
                                   "children": null
                               },
                               {
                                   "name": "企业用户的认证",
                                   "open": false,
                                   "href": ctx+"/entcainfo/list",
                                   "children": null
                               },
                               {
                                   "name": "资料重置",
                                   "open": false,
                                   "href": ctx+"/user/usermodify",
                                   "children": null
                               }
                           ]
                       },
                       {
                           "name": "系统管理",
                           "open": true,
                           "href": null,
                           "children": [
                               {
                                   "name": "专题标签维护",
                                   "open": false,
                                   "href": ctx+"/topic/topiclist",
                                   "children": null
                               },
                               {
                                   "name": "默认图库",
                                   "open": false,
                                   "href": ctx+"/imagestorage/imgstoragelist",
                                   "children": null
                               },
                               {
                                   "name": "敏感词管理",
                                   "open": false,
                                   "href": "",
                                   "children": null
                               }
                           ]
                       },
                       {
                           "name": "会员管理",
                           "open": false,
                           "href": null,
                           "children": [
                               {
                                   "name": "个人会员",
                                   "open": false,
                                   "href": "",
                                   "children": null
                               },
                               {
                                   "name": "企业会员",
                                   "open": false,
                                   "href": "",
                                   "children": null
                               }
                           ]
                       },
                       {
                           "name": "数据管理",
                           "open": false,
                           "href": null,
                           "children": [
                               {
                                   "name": "个人：违规专题处理",
                                   "open": false,
                                   "href": "",
                                   "children": null
                               },
                               {
                                   "name": "企业：地图服务管理",
                                   "open": true,
                                   "href": "",
                                   "children": null
                               }
                           ]
                       },
                       {
                           "name": "统计管理",
                           "open": true,
                           "href": null,
                           "children": [
                               {
                                   "name": "用户注册数量",
                                   "open": false,
                                   "href": ctx+"/count/sumregister",
                                   "children": null
                               }
                           ]
                       },
                       {
                           "name": "消息管理",
                           "open": true,
                           "href": null,
                           "children": [
                               {
                                   "name": "系统消息",
                                   "open": false,
                                   "href": ctx+"/message/list",
                                   "children": null
                               },
                               {
                                   "name": "邮件",
                                   "open": false,
                                   "href": "",
                                   "children": null
                               },
                               {
                                   "name": "微信",
                                   "open": false,
                                   "href": "",
                                   "children": null
                               }
                           ]
                       },
                       {
                           "name": "服务管理",
                           "open": false,
                           "href": null,
                           "children": [
                               {
                                   "name": "路径分析",
                                   "open": false,
                                   "href": "",
                                   "children": null
                               }
                           ]
                       }
                   ];
    </script>
    <script src="${ctx}/assets/js/cco.js"></script>

</head>
<body>
    <div id="header">
        <i class="sname" style="vertical-align: middle;color: white;"> <h3>天润云地图运维管理系统</h3></i>
        <div class="tr">
           	 欢迎您:<shiro:principal property="username"></shiro:principal>
            <a href="javascript:void(0)" onclick="openPwd()" class="zhuce">修改密码</a>
            <a href="${ctx}/logout" class="leave">安全退出</a>
        </div>
    </div>


    <table id="main">
        <tr>
            <td class="lnav">
                <div class="nvCont"> <ul id="navi" class="ztree"></ul> </div>
            </td>
            <td class="spacer"></td>
            <td class="cont">
                <div id="infoDiv"></div>
                <div id="ifmCont"></div>
            </td>
        </tr>
    </table>
</body>
</html>