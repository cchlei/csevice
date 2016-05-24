<!doctype html><html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <%@ include file="/assets/global.jsp"%>
    <title>未审批</title>
    <link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/service_approval.css"/>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode"  main="__/js/service_approval"></script>
    <style type="text/css">
    	.navright .service_app table.servicetable td:nth-of-type(2),.navright .service_app table.servicetable td:nth-of-type(3){text-decoration: none;}
    </style>
    <script>
        //未审批表单
        var service_approval_ajax = "${ctx}/entapp/getunaudit";
        //未审批表单详情
        var service_approval_from_ajax="${ctx}/entapp/getObj"
        //审批
        var release_sp_ajax = "${ctx}/entapp/audiApply";

    </script>
</head>
<body>
<div class="navright">
    <div class="fwtop"><em>待审批</em><span>退出</span></div>
    <div class="service_app">
        <!--搜索开始-->
        <div class="service_search">
            <div class="sqsj">
                <em>申请时间</em>
                <select class="selectBox" name="sqtime">
                    <option value=-1>全部</option>
                    <option value=3>最近三个月的订单</option>
                    <option value=5>最近半年订单</option>
                    <option value=12>最近一年订单</option>
                </select>
            </div>
            <script>
                use("$/selectBox",function(){
                    $(".selectBox").selectBox({
                        mobile: true,
                        menuSpeed: 'fast'
                    })
                })
            </script>
            <div class="service_search_top">
                <input class="fw_value" type="text" />
                <span class="fw_search">搜索</span>
            </div>
        </div>
        <div class="clear"></div>
        <!--搜索结束-->

        <!--服务审批显示开始-->
        <table class="servicetable">

        </table>
        <div class="page"></div>
        <!--服务审批显示结束-->

        <!--服务获取订单详情开始-->
        <div class="ddxq" ms-controller="ddxq" style="display: none;">
            <div class="ddtop">
                订单编号：<em class="bianhao">{{data.number}}</em>
                订单状态：<em>{{dic_order_status[data.isapproved]}}</em>
                <a href="#" onClick=window.print() target="_blank"><span class="stamp">打&nbsp印</span></a><span class="close">关&nbsp闭</span>
                <div class="clear"></div>
            </div>
            <table class="dwxx">
                <tr>
                    <td colspan="6" align="center" class="ordertitle" width="810">单位信息</td>
                </tr>
                <tr>
                    <td rowspan="4" height="146" width="69" class="ordercenter"><p>服务申请方</p></td>
                    <td width="100" class="tit1">单位名称</td>
                    <td width="233">{{data.gettercomp}}</td>
                    <td rowspan="4" width="69" class="ordercenter"><p>服务发布方</p></td>
                    <td width="100" class="tit1">单位名称</td>
                    <td width="234">{{data.settercomp}}</td>
                </tr>
                <tr>
                    <td class="tit1">联系人</td><td>{{data.gusername}}</td>
                    <td class="tit1">联系人</td><td>{{data.susername}}</td>
                </tr>
                <tr>
                    <td class="tit1">邮箱</td><td>{{data.gemail}}</td>
                    <td class="tit1">邮箱</td><td>{{data.semail}}</td>
                </tr>
                <tr>
                    <td class="tit1">电话</td><td>{{data.gmobile}}</td>
                    <td class="tit1">电话</td><td>{{data.smobile}}</td>
                </tr>
            </table>
            <table class="row fwxx">
                <tr>
                    <td colspan="4" align="center" class="ordertitle" width="810">服务信息</td>
                </tr>
                <tr>
                    <td  width="164" class="tit1 bg">服务名称</td>
                    <td>{{data.mapname}}</td>
                    <td  width="164" class="tit1 bg">行政区划</td>
                    <td>{{data.area}}</td>
                </tr>
                <tr>
                    <td class="tit1 bg">发布时间</td>
                    <td>{{data.releasetime}}</td>
                    <td class="tit1 bg">更新时间</td>
                    <td>{{data.updateretime}}</td>
                </tr>
                <tr>
                    <td class="tit1 bg">截止时间</td>
                    <td>{{data.validitytime}}</td>
                    <td class="tit1 bg">要素数量</td>
                    <td>{{data.featurecount}}</td>
                </tr>
                <tr>
                    <td class="tit1 bg">范围</td>
                    <td>{{data.scope}}</td>
                    <td class="tit1 bg">坐标系</td>
                    <td>{{data.coordinate}}</td>
                </tr>
            </table>
            <table class="row sqxx">
                <tr>
                    <td colspan="4" align="center" class="ordertitle" width="810">申请信息</td>
                </tr>
                <tr>
                    <td  width="164" class="tit1 bg">申请试用时间</td>
                    <td>{{data.applymonth}}个月</td>
                    <td  width="164" class="tit1 bg">申请接口</td>
                    <td>{{data.serviceType}}</td>
                </tr>
                <tr>
                    <td class="tit1 bg">申请字段</td>
                    <td colspan="3">{{data.attributesName}}</td>
                </tr>
                <tr>
                    <td class="tit1 bg">申请时间</td>
                    <td colspan="3">{{data.applytime}}</td>
                </tr>
            </table>
        </div>
        <!--服务获取订单详情结束-->
    </div>
</div>
</body>
</html>