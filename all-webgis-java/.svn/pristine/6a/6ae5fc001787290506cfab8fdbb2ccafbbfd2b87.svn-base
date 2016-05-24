<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>分组</title>
    <%@ include file="/assets/global.jsp"%>
    <link rel="stylesheet" href="${ctx}/assets/css/comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/groups.css"/>
    <script src="${ctx}/assets/libs/avalon.min.js"></script>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode" main="__/js/groups"></script>
    <script>
        var pageCfg = {
            /*左侧分组*/
            group_list: "${ctx}/group/groups",

            /*右侧粉丝*/
            fans_list: "${ctx}/attention/attens",

            /*改变组*/
            change_groups:"${ctx}/attention/edit",

            /*取消关注*/
            cancelattent:"${ctx}/attention/cancle",

            /*添加分组*/
            addname:"${ctx}/group/add",

            /*添加关注*/
            attention:"${ctx}/attention/add",

            /*删除组*/
            delgroup:"${ctx}/group/delete",

            /*搜索好友列表*/
            fans_search_list:"${ctx}/attention/query",

            /*修改组名称*/
            editgroup:"${ctx}/group/edit"
        };
    </script>
</head>
<body>
<i class="cl_loading page"></i>

<div class="root no_col_1 no_top">
    
    <div class="col2" ms-controller="grou">
        <ul class="fans_sub">
            <li>
                <h2 class="focus_name">
                    我的关注
                </h2>

                <div ms-each="group_list" style="max-height: 324px; overflow: auto">
                    <span href="#" class="bg-focus" ms-click="member_click(el,$index)" ms-class-1="hove:cureent_gp_index == $index"><em>{{el.name}}</em>
                    </span>
                </div>
                <span class="addgroups"><em>+</em> 添加分组</span>
            </li>
            <li>
                <div class="fans_name">
                    粉丝
                </div>
                <span class="bg-all">所有</span>
            </li>
            <li>
                <div class="fans_guanzhu">
                    关注
                </div>
                <span class="addattention"><em>+</em> 添加关注</span>
            </li>
        </ul>


    </div>
    <div class="mainCont col3" ms-controller="fans">
        <div class="inner">
            <h1 class="fans_all page_name">
                <div class="text_left"><span>{{pagename}}</span> ({{total}}个人)</div>

                <div ms-visible="view_mode==0" class="text_right">
                    <span ms-click="edit">修 改</span>
                    <span ms-click="del">删 除</span>
                </div>
                <div class="clear"></div>
            </h1>

            <div class="main">
                <div class="main_fans">
                    <ul class="fans_framework" ms-each="list" ms-if="list.length!=0">

                        <li>
                            <div class="fans_img">
                                <p><img ms-attr-src="co.getThumb(el.headimg,72,72,default_headimg)"></p>
                            </div>
                            <div ms-visible="view_mode === 0" class="branchgroup">
                                <span class="rightname">
                                    <em>{{el.userName}}</em>
                                    <div class="guanzhu" ms-if="el.atteflag"><i></i>互相关注</div>
                                </span>
                                <div class="clear"></div>
                                <span class="grouping" ms-click="change_group(el)"><em>{{gp.name}}</em><i></i></span>
                                <span class="cancel_attention" ms-click="cancel_attention(el)">取消关注</span>
                                <div class="change_group" ms-visible="asdfasdfasdfasdf == el">
                                    <div ms-each="group_mumbers"><label ms-if="current_gp.gid!=el.gid"><input type="radio" ms-duplex-string="groupgid" ms-attr-value="el.gid">{{el.name}} </label></div>
                                    <input type="hidden" ms-attr-value="groupgid" class="groupgid">
                                    <div class="bth"><span class="determine" ms-click="determine(el)">确定</span><span class="cal" ms-click="cal(el)">取消</span></div>
                                </div>
                            </div>

                            <div ms-visible="view_mode === 1" class="fanslist">
                                <span class="rightname">
                                    <p>{{el.userName}}</p>
                                    <p class="attention" ms-click="attention(el)" ms-if="!el.atteflag">关注</p>
                                    <p class="guanzhu" ms-if="el.atteflag"><i></i>互相关注</p>
                                </span>
                            </div>
                        </li>

                    </ul>
                    <div class="clear"></div>
                    <div class="fans_framework noxinxi" ms-if="list.length==0">
                        <div>该分组还没有关注的好友，要多去结识好友哦</div>
                    </div>
                    <ul id="page_no" ms-if="list.length!=0"></ul>
                </div>
            </div>


        </div>

    </div>
    <!--添加关注-->
    <div class="concern" ms-controller="search_fans_list">
        <div class="searchfriend">
            <input type="text" name="searchfriend"><span>搜索</span>
        </div>
        <ul ms-each="list"  ms-if="list.length!=0">
            <li>
                <div class="fans_img">
                    <p><img ms-attr-src="co.getThumb(el.headimg,72,72,default_headimg)"></p>
                </div>

                <div class="fanslist">
                    <span class="rightname">
                        <p>{{el.userName}}</p>
                        <p class="attention" ms-click="attention(el)" ms-if="el.atteflag==-1">关注</p>
                        <p class="qxattention" ms-if="el.atteflag==0">已关注</p>
                        <p class="guanzhu" ms-if="el.atteflag==1"><i></i>互相关注</p>
                    </span>
                </div>
                <div class="clear"></div>
            </li>
        </ul>
        <div class="clear"></div>
        <div id="search_fans_page"  ms-if="list.length!=0"></div>
        <div ms-if="list.length==0">
            <div class="noname">未找到相关用户!</div>
        </div>

    </div>
</div>
</body>
</html>