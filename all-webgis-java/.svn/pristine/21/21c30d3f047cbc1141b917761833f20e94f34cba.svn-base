<!DOCTYPE html> <html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>添加记录</title>
    <%@ include file="/assets/global.jsp"%>
    <link rel="stylesheet" href="${ctx}/assets/css/comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/record_edit.css"/>
    <link rel="stylesheet" href="${ctx}/assets/libs/openlayers/css/ol.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/map/map.css"/>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode"></script>
    <script src="${ctx}/assets/libs/openlayers/ol-debug.js"></script>
    <script src="${ctx}/assets/libs/openlayers/trmap_styles.js"></script>
    <script src="${ctx}/assets/js/map/map_topic_edit.js"></script>
    <script>
        var mode =  "__/js/record_add";
        var pageCfg = {

            /*专题颜色*/
            theme_color:"${ctx}/assets/data/co_subject.json",
            /*图片请求地址*/
            coverurl:"${ctx}/attach/upload",
            /*图片删除*/
            filedelete:"${ctx}/attach/deleteFile"
        };
        var topicid = ${topicId};
        var isPrivateTopic = ${flag};
    </script>
</head>
<body>
<i class="cl_loading page"></i>
<div class="root no_top no_col_1_2">
    <jsp:include page="/assets/top.jsp"></jsp:include>
    <div class="mainCont col3">
        <div class="inner">
            <h1 class="page_name white"><a href="javascript:history.go(-1)"><i class="ico_record"></i></a>添加记录</h1>

            <form action="${ctx}/record/add" class="record_add">
                <table>
                    <tr>
                        <td>
                            <em>标题</em>
                            <input type="text" name="title">
                        </td>
                        <td ms-controller="isopen">
                            <em>权限</em>
                            <div ms-click='toggle' class="jurisdiction">
                                <div class="switch blue" ms-if="isopen">
                                    <span class="open"></span><span>公开</span>
                                </div>
                                <div class="switch" ms-if="!isopen">
                                    <span>私密</span><span class="close"></span>
                                </div>
                            </div>
                            <input type="hidden" name="shareflag" ms-duplex-number="isopen">
                            <script>
                                /*if(!isPrivateTopic){
                                 $("tr:last").hide();
                                 }*/
                            </script>
                        </td>
                        <td>
                            <em>日期</em>
                            <input name="occurtimes_showing" readonly="readonly" onclick="laydate({format: 'YYYY年MM月DD',choose:on_date_picker_choose})"  class="laydate-icon occurtime"/>
                            <input name="occurtimes" readonly="readonly" type="hidden"/>
                            <script>
                                use("dateFormat",function(dd){
                                    var showing = $("input[name=occurtimes_showing]");
                                    showing.val(new Date().format("%Y年%m月%d日"));

                                    var hid = $("input[name=occurtimes]")
                                    hid.val(new Date().format("%Y-%m-%d %H:%M:%S:%N"));

                                    window.on_date_picker_choose = function(){
                                        var val = showing.val();


                                        var d = dd(
                                                val.replace(/[^\d]/g,"-")
                                        );

                                        var now = new Date();

                                        d.setHours(
                                                now.getHours(),
                                                now.getMinutes(),
                                                now.getSeconds(),
                                                now.getMilliseconds()
                                        );

                                        hid.val(d.format("%Y-%m-%d %H:%M:%S:%N"));
                                        hid.attr("value",d.format("%Y-%m-%d %H:%M:%S:%N"));

                                    }
                                })
                            </script>
                        </td>
                        <td>
                            <em>描述</em>
                            <textarea name="description" placeholder="请输入描述"></textarea>
                        </td>
                        <td class="webup">
                            <em><span style="font-weight: 700;">+</span> 添加附件</em>
                            <div class="upload uploadthing" ms-controller="file_man">
                                    <span ms-repeat="list" class="file" ms-class-1="img:el.type=='image/jpeg'">
                                        <b ms-attr-title="ibpath +el.id"><img ms-attr-src="el.thumb" alt=""></b>
                                        <!--<b ms-if="el.type!='image/jpeg'"><a target="_blank" ms-attr-href="ibpath + (el.id +'.'+ el.ext)"><img ms-attr-src="el.thumb" alt=""></a></b>-->
                                        <i ms-click="del(el)">删除</i>
                                        <p>{{el.name}}</p>
                                    </span>
                                    <span class="file uploadclick">
                                        <img alt="" src="${ctx}/assets/images/jiahao.png">
                                    </span>
                            </div>

                        </td>
                    </tr>
                    <tr>
                        <td  ms-controller="linkadress" class="linkadress">
                            <em>地点</em>
                            <input type="text" name="addrname" id="addrname">
                            <input type="hidden" name="geom" id="geom"/>
                            <input type="text" style="display:none">
                        </td>
                        <td>
                            <div class="map_wrapper">
                                <div class="btn_bar">
                                    <a class="dot"  event="Point"><i></i>点</a>
                                    <a class="line" event="LineString"><i></i>线</a>
                                    <a class="area" event="Polygon"><i></i>面</a>
                                    <a class="del"  event="Clear"><i></i>清除</a>
                                </div>
                                <div id="mapel"></div>
                            </div>
                        </td>
                    </tr>

                    <tr class="bth">
                        <td><a href="javascript:history.go(-1)"><span class="sub_cancel">取消</span></a><span class="sub_save">保存</span></td>
                    </tr>
                </table>

                <input type="hidden" name="coverurl" id="coverurl">
                <input type="hidden" name="topicid" value="${topicId}" >


            </form>
        </div>
    </div>
</div>
</body>

</html>