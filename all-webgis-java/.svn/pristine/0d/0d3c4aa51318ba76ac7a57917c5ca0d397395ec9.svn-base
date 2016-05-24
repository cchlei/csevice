<!DOCTYPE html> <html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>记录浏览</title>
    <%@ include file="/assets/global.jsp"%>
    <link rel="stylesheet" href="${ctx}/assets/css/comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/record_browse.css"/>
    <link rel="stylesheet" href="${ctx}/assets/libs/openlayers/css/ol.css"/>
	<link rel="stylesheet" href="${ctx}/assets/css/map/map.css"/>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode" main="__/js/record_browse"></script>
    <script>
        var pageCfg = {
            /*附件图片展示*/
            imgs_data:"${ctx}/record/attachs/${recordId}",
            recode_brows:"${ctx}/record/getRecord/${recordId}",
            recode_comments:"${ctx}/comment/getList/${recordId}",
            recode_addcomment:"${ctx}/comment/save",
            del_comment:"${ctx}/comment/del"
        };
        var recordId = ${recordId};
        var cuserId = ${cuserId};
        var buser_id =${buser_id};
        var busername = "${busername}";
        var current_user_img = "${current_user_img}";
        var current_user_name = "${current_user_name}";
    </script>
</head>
<body>
    <i class="cl_loading page"></i>
    <div class="root no_top no_col_1_2">
        
        <div class="mainCont col3">
            <div class="inner">
                <h1 class="page_name white"><a href="javascript:history.go(-1)"><i class="ico_record"></i></a>记录浏览</h1>
                <a href="${ctx }/record/toedit/${recordId}" class="edit_recordico"> 编辑记录</a>
                <div class="main">
                    <form  class="record_browse" ms-controller="record_browse">
                        <table>
                            <tr>
                                <td><em>标题:</em></td>
                                <td>
                                    {{data.name}}
                                    <i ms-if="data.shareflag" class="open"></i><span ms-if="data.shareflag" class="open">公开</span>
                                    <i ms-if="!data.shareflag" class="close"></i><span ms-if="!data.shareflag" class="open">私密</span>
                                    <input type="hidden" ms-duplex-number="data.shareflag" name="shareflag">
                                </td>
                            </tr>
                            <tr>
                                <td><em>日期:</em></td>
                                <td>{{data.occurtime|date("yyyy年MM月dd日")}}</td>
                            </tr>

                            <tr>
                                <td><em>描述:</em></td>
                                <td><p class="descri">{{data.description}}</p></td>
                            </tr>
                            <tr>
                                <td><em>地点:</em></td>
                                <td class="adressmap">{{data.addrname}}<i ms-class-1="link1:data.geom"></i>
                                    <div class="map3">
                                        <div id="map" class="map"></div>
                                    </div>
                                </td>
                            </tr>
                            <tr class="r_filemess">
                                <td class="filerecord">
                                    <em class="uploadclick">附件</em>
                                    <div class="uploadthing" ms-controller="file_man">
                                    <span ms-repeat="list" class="file" ms-class-1="img:el.type=='image/jpeg'  || el.isimg">
                                        <b  ms-attr-title="ibpath +el.id" ms-if="el.type=='image/jpeg' || el.isimg"><img ms-attr-src="el.thumb" alt=""></b>
                                        <b ms-if="!el.type=='image/jpeg' || !el.isimg"><a ms-attr-href="ibpath + (el.id +'.'+ el.ext)"><img ms-attr-src="el.thumb" alt=""></a></b>
                                        <p>{{el.name}}</p>
                                    </span>
                                    </div>
                                    <em style="text-align: left;">留言</em>
                                    <div  ms-controller="message" class="comments_box">
                                        <div class="replayto" ms-visible="msg.buser_id">回复给：{{msg.busername}} <i ms-click="clear_replay_to()">×</i></div>
                                        <!--恢复谁谁-->
                                        <div class="text-box">
                                            <textarea ms-duplex="msg.comcontent" class="comment-area" placeholder="留个言~~~~"  id="comcontent"></textarea>
                                            <span class="btn" ms-click="publish">发表</span>
                                        </div>

                                        <ul class="comments-main" ms-each="list" ms-visible="list.length">
                                            <li class="comments-item" ms-attr-commid="el.id">
                                                <div class="comments-item-bd">
                                                    <div class="ui-avatar">
                                                        <a><img ms-attr-src="el.cuser_headimg? imgpath+el.cuser_headimg : moren  "></a>
                                                    </div>
                                                    <div class="comments-content">
                                                        <a  href="#" class="comments-name">{{el.cusername}}</a>: {{el.comcontent}}
                                                        <div class="comments-op" >
                                                            <span class="comments_time"> {{el.comtime}}</span>
                                                        <span class="comments_reply">
                                                            <a class="act-reply" ms-click="onReplay(el)">回复</a>
                                                            <a class="act-reply"  ms-click="del(el)" ms-visible = "el.cuser_id == static_info.c_user_id || static_info.rec_user_id==static_info.c_user_id">| 删除</a>
                                                        </span>
                                                            <div class="clear"></div>
                                                        </div>
                                                        <div class="comments-list ">
                                                            <ul ms-if="el.child && el.child.length" ms-each="el.child">
                                                                <li ms-attr-commid="el.id">
                                                                    <div class="comments-item-bd">
                                                                        <div class="ui-avatar">
                                                                            <a target="_blank">
                                                                                <img  ms-attr-src="el.cuser_headimg ? imgpath+el.cuser_headimg : moren || moren ">
                                                                            </a>
                                                                        </div>
                                                                        <div class="comments-content">
                                                                            <em class="comments-name">{{el.cusername}}</em> 回复
                                                                            <em  href="#" class="comments-name">{{el.busername}}</em> : {{el.comcontent}}
                                                                            <div class="comments-op">
                                                                                <span class="comments_time">{{el.comtime}}</span>
                                                                            <span class="comments_reply">
                                                                                 <a class="act-reply" ms-click="onReplay(el,$outer.el)">回复</a>
                                                                                 <a class="act-reply" ms-click="del(el,$outer.el)" ms-visible = "el.cuser_id == static_info.c_user_id || static_info.rec_user_id==static_info.c_user_id">| 删除</a>
                                                                            </span>
                                                                                <div class="clear"></div>

                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>

                        </table>

                        <input type="hidden" name="coverurl" id="coverurl">


                    </form>
                </div>

            </h1>
        </div>
    </div>
    <div id="popup" class="ol-popup-nowrap">
      <a href="#" id="popup-closer" class="ol-popup-closer"></a>
      <div id="popup-content"></div>
    </div>
<script type="text/javascript" src="${ctx}/assets/libs/openlayers/ol.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/map/map_record.js"></script>
</body>
</html>