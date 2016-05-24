<!DOCTYPE html> <html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>添加专题</title>
    <%@ include file="/assets/global.jsp"%>
    <link rel="stylesheet" href="${ctx}/assets/css/comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/addsubject.css"/>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode" main="__/js/addsubject"></script>
    <script>
        var pageCfg = {
            /*专题数量*/
            themes_nubmer:"${ctx}/topic/countTopic",

            /*专题名称命名*/
          //  name_subject:"${ctx}/assets/data/name_subject.json",
            name_subject:"${ctx}/topic/name",

            /*专题颜色*/
            theme_color:"${ctx}/assets/data/co_subject.json",

            /*图片请求地址*/
            coverurl:"${ctx}/attach/upload",
            
           	/*专题封面图集*/
  			imglist:"http://os.trmap.cn/imagestorage/imglist/1",

            /*指定好友列表*/
            friendlist:"${ctx}/attention/queryattens"

        };
    </script>
</head>
<body>
    <i class="cl_loading page"></i>
    <div class="root no_top no_col_1_2">
            <jsp:include page="/assets/top.jsp"></jsp:include>
        <div class="col2">
            <ul class="sub_nav" ms-controller="number">
                 <li>
                    <h3><i class="add"></i><i class="triangle"></i>我的专题 </h3>
                    <a href="${ctx}/login#"  	class="bg-color-1" >所有专题 <em>({{number.alltheme}})</em></a>
                    <a href="${ctx}/login#1"  	class="bg-color-2" >公开专题 <em>({{number.opentheme}})</em></a>
                    <a href="${ctx}/login#0" 	class="bg-color-3" >私密专题 <em>({{number.privatetheme}})</em></a>
                </li>

                <li>
                    <h3><i class="triangle"></i>发现 <i class="add"></i></h3>
                    <a href="${ctx}/topic/shareListView" class="bg-color-4">所有分享 <em></em></a>
                    <a href="#" class="bg-color-5">我的关注 <em></em></a>
                    <a href="${ctx}/collect/collectview" class="bg-color-6">我的收藏 <em></em></a>
                    <a href="#" class="bg-color-7">分享给我 <em></em></a>
                </li>
            </ul>
        </div>
        <div class="mainCont col3">
            <div class="inner">
                <h1 class="page_name">我的专题</h1>

                <form action="${ctx}/topic/add" class="addsubform">
                    <table>
                        <tr>
                            <td><em>专题名称</em></td>
                            <td ms-controller="subname">
                                <input
                                        name="name"
                                        placeholder="请输入专题名称"
                                        ms-duplex="valuename"
                                        ms-click="nameinput"
                                        >
                                <input id="subjectname" type="text" style="float:left;width: 0px; height:0px; border: 1px #e8eff9 solid; line-height: 0px; padding: 0;outline: none;" ms-duplex="valuename">
                                <div class="prompt"><span ms-repeat="themename" ms-click="onname(el)">{{el}}</span><i class="close">×</i></div>

                            </td>
                        </tr>
                        <tr ms-controller="isopen">
                            <td><em>是否公开</em></td>
                            <td>
                                <div ms-click='toggle' style="display: inline-block;">
                                    <div class="switch blue" ms-if="isopen">
                                        <span class="open"></span><span>公开</span>
                                    </div>
                                    <div class="switch" ms-if="!isopen">
                                        <span>私密</span><span class="close"></span>
                                    </div>
                                </div>
                                <input type="hidden" name="shareflag" ms-duplex-number="isopen" >
                                <div class="friend" ms-if="isopen==1">
                                    <label><input type="radio" name="jurisdict" value="-1" ms-duplex-number="jurisdict"/><span>所有人</span></label>
                                    <label><input type="radio" name="jurisdict" value="0" ms-duplex-number="jurisdict"/><span>我的好友</span></label>
                                    <label><input type="radio" name="jurisdict" value="1" ms-duplex-number="jurisdict" ms-click="zdfriend()"/><span>指定好友</span></label>
                                    <span ms-if="jurisdict == 0">人数：{{total}}</span>
                                    <span ms-if="jurisdict == 1">人数：{{zdtotal}}</span>
                                </div>
                            </td>
                            <td>
                                <input type="hidden" name="jurisdict_list" ms-duplex="jurisdict_id_str">
                                <div class="zdfriend">
                                    <div class="left">
                                        <div  ms-repeat="list">
                                            <h3 ms-click="hove(el,$event)" ms-class-1="cur:el.shrink">{{el.name}} <i ms-click="addgroup(el)"></i></h3>
                                            <ul ms-visible="el.shrink">
                                                <li ms-repeat="el.user" ms-click="toggle_user_status(el)" ms-class-1="cur:el.checked">
                                                    <img ms-attr-src="co.getThumb(el.headimg,26,26,default_headimg)">{{el.userName}}
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="right">
                                        <h3>已选好友</h3>
                                        <div ms-each="list">
                                            <p ms-repeat="el.user" ms-visible="el.checked" ms-attr-sid="el.userId" ms-class-1="cur:el.checked">
                                                <img  ms-attr-src="co.getThumb(el.headimg,26,26,default_headimg)">{{el.userName}} <i ms-click="toggle_user_status(el)"></i>
                                            </p>
                                        </div>
                                    </div>
                                </div>

                            </td>

                        </tr>
                        <tr>
                            <td><em>专题描述</em></td>
                            <td><textarea name="description" id="describe" placeholder="请输入专题描述"></textarea></td>
                        </tr>
                        <tr>
                            <td><em>标签颜色</em></td>
                            <td  ms-controller='s_color'>
                                <div  ms-each-el="color_list">
                                    <span class="test" ms-class-1="gray:el.index == current_color_index" ms-click="click_co(el)"><em ms-css-background-color="el.color"></em></span>
                                </div>
                                <input type="hidden" name="topiccolor" ms-duplex="topiccolor">
                            </td>
                        </tr>
                        <tr ms-controller="imgupload">
                            <td><em>专题封面</em></td>
                            <td class="header-img">
                                <img ms-attr-src="{{img}}" class="cover_img">
                            </td>
                            <td class="imgupload">
                                <div>
                                    <h3><i></i>上传图片</h3>
                                    <ul><li ms-repeat="imglist"><img ms-attr-src= "thump+el" ms-click="onimg(el)"></li></ul>
                                </div>
                            </td>
                        </tr>
                        <tr class="bth">
                            <td><a href="javascript:history.go(-1)"><span class="sub_cancel">取消</span></a><span class="sub_save">保存</span></td>
                        </tr>
                    </table>
                    <input type="hidden" name="coverurl" id="coverurl" ms-duplex="topicimg">

                </form>
            </div>
        </div>
    </div>
</body>
</html>