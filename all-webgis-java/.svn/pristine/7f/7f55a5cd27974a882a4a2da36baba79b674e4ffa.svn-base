<!DOCTYPE html> <html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>账户中心</title>
    <%@ include file="/assets/global.jsp"%>
    <link rel="stylesheet" href="${ctx}/assets/css/comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/account.css"/>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode" main="__/js/account"></script>
</head>
<body>
        <div class="mainCont col3 account_marg">
            <div class="account_set">
                    <ul >
                        <li class="gaikuo">概况</li>
                        <%--<li class="name">${account.name }</li>
                         <li class="level">${account.grade }级</li>
                        <li class="upgrade"><a href="#">升级</a> </li> --%>
                    </ul>
            </div>
            <div  class="bar_box">
                <ul>
                    <li>
                        <span>总空间<em>${account.defaultSpaceSize}MB</em></span>
                        <div class="bar">
                        </div>

                    </li>
                    <li>
                        <span>可创建专题数<em>${account.topicCount-account.all}</em></span>
                        <div class="bar">
                        </div>

                    </li>
                    <li>
                        <span><i></i>拥有金币<em>200</em></span>
                    </li>
                </ul>
                <div class="clear"></div>

            </div>
            <div class="project">
                <h3>专题</h3>
                <ul ms-controller="account">
                    <li>
                        <div class="bind-icon total"></div>
                        <span ><a href="${ctx}/topic/totopic" target="_parent" ms-click="removecur">总专题数</a><em>(${account.all})</em></span>
                    </li>
                    <li>
                        <div class="bind-icon share"></div>
                        <span ><a href="${ctx}/topic/totopic?open=1" target="_parent" ms-click="removecur">公开专题</a><em>(${account.yfx})</em></span>
                    </li>
                    <li>
                        <div class="bind-icon share_not"></div>
                        <span ><a href="${ctx}/topic/totopic?open=0" target="_parent" ms-click="removecur">私密专题</a><em>(${account.wfx})</em></span>
                    </li>
                </ul>
            </div>
            <div class="project">
                <h3>空间</h3>
                <ul>
                    <li>
                        <div class="bind-icon size_total"></div>
                        <span >总大小<em> ${account.defaultSpaceSize}M</em></span>
                    </li>
                    <li>
                        <div class="bind-icon user_has"></div>
                        <span>已使用<em> ${account.spaceSize}M</em></span>
                    </li>
                    <li>
                        <div class="bind-icon user_not"></div>
                        <span>未使用<em> ${account.defaultSpaceSize-account.spaceSize}M</em></span>
                    </li>
                </ul>
            </div>

        </div>

    </div>

</body>
</html>