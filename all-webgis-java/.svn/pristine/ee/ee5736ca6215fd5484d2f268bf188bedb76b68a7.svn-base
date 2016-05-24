<!doctype html><html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <%@ include file="/assets/global.jsp"%>
    <title>服务申请</title>
    <link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/service_apply.css"/>
    <link rel="stylesheet" href="${ctx}/assets/libs/jqueryui/jquery-ui.css">
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode"></script>
    <script>
    
    	var service_apply_type_ajax = "${ctx}/entrelmap/getApplyInfo/${mapid}";
        /*表单内容生成*/
        var service_apply_from_ajax = "${ctx}/entrelmap/getApplyForm/${mapid}";

        /*申请提交*/
        var apply_time_ajax = "${ctx}/entrelmap/applyMap/${mapid}/${applicationid}";
        
        use("__/js/service_apply");
    </script>
</head>
<body>
<div class="hall">
    <div class="fwtop">
        <img src="${ctx}/assets/images/fw_logo.png" alt="" class="fwlogo">
        <i class="wx"></i>
        <img src="${ctx}/assets/images/fw_sq1.png" alt="" class="fwsq">
        <i class="topbj"></i>
        <div class="user">
<%--             <img src="${ctx}/assets/images/fw_people.png" alt=""> --%>
<!--             <span class="name">用户名:潇湘雨</span> -->
            <span class="fwquit">退出</span>
        </div>
    </div>
    <div class="order" ms-controller="order" style="display: none;">
        <img src="${ctx}/assets/images/qpply1.jpg" class="jdimg">
        <div class="applyusetime">
            <div>
                <em>申请使用时间</em>
                <p class="sqtime">
                    <span class="input"><input  type="text" name="usetime" class="usetime" id="spinner"/></span>
                    <span class="info">个月<span style="margin-left: 22px;">服务截止：{{data.validitytime}}</span></span>
                </p>
            </div>
            <div>
                <em>选择接口类型</em>
                <p class="jktype"><span ms-repeat="data.jktype"  ms-click="oncheck(el)"><i ms-class-1="ed:el.checked"></i>{{el.name}}</span></p>
            </div>
            <div>

                <em>选择图层字段</em>
                <p ms-repeat="data.tclayer" ms-attr-class="el.class">
                    <span ms-repeat="el.data" ms-attr-code="el.code" ms-click="oncheck(el)"><i ms-class-1="ed:el.checked"></i>{{el.name}}</span>
                </p>

            </div>

            <p class="xieyi"><i class="ed"></i><span>我已阅读并接受<a href="service_protocols.html" target="_blank">《天地云地图协议》</a></span></p>
            <p class="next">下一步</p>
            <div class="clear"></div>
        </div>
        <div class="form">
            <table class="dwxx">
                <tr>
                    <td colspan="6" align="center" class="ordertitle" width="810">单位信息</td>
                </tr>
                <tr>
                    <td rowspan="4" height="146" width="70" class="ordercenter"><p>服务申请方</p></td>
                    <td width="100" class="tit1">单位名称</td>
                    <td width="235">{{tbdata.sqcompany}}{{tbdata.sqauthentication}}</td>
                    <td rowspan="4" width="70" class="ordercenter"><p>服务发布方</p></td>
                    <td width="100" class="tit1">单位名称</td>
                    <td width="235">{{tbdata.fbcompany}}{{tbdata.fbauthentication}}</td>
                </tr>
                <tr>
                    <td class="tit1">联系人</td>
                    <td>{{tbdata.sqcontacts}}</td>
                    <td class="tit1">联系人</td>
                    <td>{{tbdata.fbcontacts}}</td>
                </tr>
                <tr>
                    <td class="tit1">邮箱</td>
                    <td>{{tbdata.sqemail}}</td>
                    <td class="tit1">邮箱</td>
                    <td>{{tbdata.fbemail}}</td>
                </tr>
                <tr>
                    <td class="tit1">电话</td>
                    <td>{{tbdata.sqphone}}</td>
                    <td class="tit1">电话</td>
                    <td>{{tbdata.fbphone}}</td>
                </tr>
            </table>
            <table class="row fwxx">
                <tr><td colspan="4" align="center" class="ordertitle" width="810">服务信息</td></tr>
                <tr>
                    <td class="tit1 bg" width="100">服务名称</td>
                    <td width="304">{{tbdata.servicename}}</td>
                    <td class="tit1 bg" width="100">行政区划</td>
                    <td>{{tbdata.area}}</td>
                </tr>
                <tr>
                    <td class="tit1 bg">发布时间</td>
                    <td>{{tbdata.releasetime}}</td>
                    <td class="tit1 bg">更新时间</td>
                    <td>{{tbdata.refreshtime}}</td>
                </tr>
                <tr>
                    <td class="tit1 bg">截止时间</td>
                    <td>{{tbdata.enddate}}</td>
                    <td class="tit1 bg">要素数量</td>
                    <td>{{tbdata.ysnumber}}</td>
                </tr>
                <tr>
                    <td class="tit1 bg">范围</td>
                    <td>{{tbdata.range}}</td>
                    <td class="tit1 bg">坐标系</td>
                    <td>{{tbdata.coordinate}}</td>
                </tr>
            </table>
            <table class="row sysj">
                <tr>
                    <td colspan="4" align="center" class="ordertitle" width="810">申请信息</td>
                </tr>
                <tr>
                    <td class="tit1 bg" width="100">申请使用时间</td>
                    <td width="304" class="stime"></td>
                    <td class="tit1 bg" width="100">申请接口</td>
                    <td>
                        <span ms-repeat="data.jktype"  ms-click="oncheck(el)"><i ms-class-1="ed:el.checked"></i>{{el.name}}</span>
                    </td>
                </tr>
                <tr>
                    <td class="tit1 bg">申请字段</td>
                    <td colspan="3" class="sqsytime">
                    	<span ms-repeat-ee="data.tclayer" ms-attr-class="el.class">
                            <span ms-repeat="ee.data" ms-visible="ee.class=='mrtype' || el.checked">{{el.name}} </span>
                        </span>
                    </td>
                </tr>
            </table>
            <p class="ddxq"><span class="back">上一步</span><span class="sure">确定</span></p>
            <div class="clear"></div>
        </div>
        <div class="sqsucess">
            <h3>服务申请提交成功</h3>
            <div class="sucess">
                <img src="${ctx}/assets/images/fwsuccess.png">
                <p>恭喜您，您的服务申请已提交成功！进入<a href="${ctx}/entrelmap/toservicelist">服务大厅</a>。</p>
                <div class="clear"></div>
            </div>
        </div>

        <div class="sqfail">
            <h3>服务申请提交失败</h3>
            <div class="sucess">
                <img src="${ctx}/assets/images/fwfail.png">
                <p>对不起，您的服务申请失败<span class=reason></span>！<a href="${ctx}/entrelmap/toApply/${mapid}/${applicationid}">重新申请</a></p>
                <div class="clear"></div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
