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
    <title>服务获取</title>
    <link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/service_obtain.css"/>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode"  main="__/js/service_obtain"></script>
    <script>
        //获取列表
        var ajax_service_query = "${ctx}/entapp/getlist";
        //获取表单内容
        var service_apply_from_ajax="${ctx}/entapp/getObj";
        //撤销
        var ajax_revoke = "${ctx}/entapp/cancelApply";
        //续约
        var ajax_goon = "${ctx}/entapp/reApply"
        //删除
        var ajax_dele = "${ctx}/entapp/deleteApply"
    </script>
</head>
<body>
<div class="navright">
    <div class="fwtop"><em>服务获取</em><a href="${ctx}/entrelmap/toservicelist" target="_blank">服务大厅</a><span>退出</span></div>
    <div class="service_obtain">
        <!--服务获取开始-->
        <div class="service_search">
            <div class="approval">
                <span no="-1">全部</span>
                <span no="0">待批准</span>
                <span no="1">已批准</span>
                <span no="2">驳回</span>
                <span no="3">过期</span>
               <span no="4">已撤销</span>
                <span no="5">异常</span>
            </div>
            <div class="service_search_top">
                <i></i>
                <input class="fw_value" type="text" />
                <span class="fw_search">搜索</span>
            </div>
        </div>
        <div class="clear"></div>

        <div class="time">
            <select name="sqtime" class="sqtime">
                <option value=-1>申请时间</option>
                <option value=3>近三个月</option>
                <option value=12>近一年</option>
                <option  value=-1>全部</option>
            </select>

            <select name="jztime" class="jztime">
                <option value=-1>截止时间</option>
                <option value=3>三个月内</option>
                <option value=12>一年内</option>
                <option value=-1>全部</option>
            </select>
        </div>
        <!--服务获取结束-->

        <!--服务获取查询地图显示开始-->
        <div class="servicemap">
            <ul class="grid">

            </ul>
            <div class="clear"></div>
        </div>
        <div class="page"></div>
        <!--服务获取查询地图显示结束-->

        <!--服务获取订单详情开始-->
        <div class="obxq" ms-controller="obxq" style="display: none;">
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
                    <td>{{data.applymonth}}</td>
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
            <table class="row fwdz">
                <tr><td colspan="4" align="center" class="ordertitle" width="810">服务地址</td></tr>
                <tr>
                    <td  width="164" class="tit1 bg">REST</td>
                    <td colspan="3">{{data.shareurl}}</td>
                </tr>
                <!--  
                <tr>
                    <td  width="164" class="tit1 bg">KEY</td>
                    <td colspan="3"></td>
                </tr>
                -->
            </table>
        </div>
        <!--服务获取订单详情结束-->
    </div>
</div>
</body>
</html>