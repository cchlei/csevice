<!DOCTYPE html> <html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>消息中心</title>
    <%@ include file="/assets/global.jsp"%>
    <link rel="stylesheet" href="${ctx}/assets/css/comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/msg_center.css"/>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode" main="__/js/msg_center"></script>
    <script>
        var pageCfg = {
            /*左侧分组*/
            msgcenter: "${ctx}/message/count",

            /*右侧消息*/
            msg_list: "${ctx}/message/messageList",

            /*右侧消息*/
            clickmsg: "${ctx}/message/update",
            /*回复*/
            huifu_comment:"${ctx}/comment/save",
            del_comment:"${ctx}/comment/del"
        };
        var current_user_id = "${current_user_id}";
        var current_user_name = "${current_user_name}";
    </script>
</head>
<body>
    <i class="cl_loading page"></i>
    <div class="root no_col_1 no_top">
            <div class="col2" ms-controller="msgcenter">
                <h1 class="page_name">消息中心</h1>
                <ul>
                    <li><a class="orange" ms-click="togg(-1)">所有消息 ({{list.all}})</a></li>
                    <li><a class="blue" ms-click="togg(1)">已读消息 ({{list.yyd}})</a></li>
                    <li><a class="red" ms-click="togg(0)">未读消息 ({{list.wyd}})</a></li>
                </ul>
            </div>
        <div class="mainCont col3" ms-controller="msg_list">
            <div class="inner">
                <h1 class="page_name">{{msgname}}</h1>
                <div class="main">
                    <ul ms-each="msglist" class="mainmsg" ms-if="msglist.length!=0">
                        <li ms-click="msgmsg(el)">
                            <!--欢迎信-->
                            <div ms-if="el.msgType==0">
                                <span>{{el.occurtime}}</span>
                                <strong>
                                    <i class="state-expand" ms-class-1="yd:el.readflag==1"></i>
                                    <em ms-class-1="weidu:el.readflag==0">{{el.messageTitle}}</em>
                                </strong>
                            </div>
                            <!--新增粉丝-->
                            <div ms-if="el.msgType==1">
                                <span>{{el.occurtime}}</span>
                                <strong>
                                    <i class="state-expand" ms-class-1="yd:el.readflag==1"></i>
                                    <em ms-class-1="weidu:el.readflag==0">{{el.messageTitle}}</em>
                                </strong>
                            </div>
                            <!--新增评论-->
                            <div ms-if="el.msgType==2">
                                <span>{{el.occurtime}}</span>
                                <strong>
                                    <i class="state-expand" ms-class-1="yd:el.readflag==1"></i>
                                    <em ms-class-1="weidu:el.readflag==0">{{el.messageTitle}}</em>
                                </strong>
                            </div>
                              <!--系统消息-->
                            <div ms-if="el.msgType==3">
                                <span>{{el.occurtime}}</span>
                                <strong>
                                    <i class="state-expand" ms-class-1="yd:el.readflag==1"></i>
                                    <em ms-class-1="weidu:el.readflag==0">{{el.messageTitle}}</em>
                                </strong>
                            </div>
                        </li>
                    </ul>
                    <div class="mainmsg  noxinxi" ms-if="msglist.length==0">
						暂时没有可读消息
                	</div>
                    <div class="clear"></div>
                    <div class="pageno" ms-visible="totalCount!=0"></div>

                </div>
            </div>

        </div>
    </div>

    <!--信息弹出框-->
    <div class="msgpop_up" ms-controller="msgpop_up">
        <!--欢迎页-->
        <div class="welcome" ms-if="list.msgType==0">
            <h3>亲爱的{{list.username}}：</h3>
            <p>您好！</p>
            <p>欢迎加入天润云地图，我们将时刻注意您的动态确保为您及时提供所需服务</p>
            <p class="tright">天润云地图</p>
        </div>
        <!--关注-->
        <div class="follow" ms-if="list.msgType==1">
            <h3>亲爱的{{list.username}}：</h3>
            <p>您好！</p>
            <p>{{list.messageContent}}</p>
            <p class="tright">天润云地图</p>
        </div>
        <!--评论-->
        <div class="review" ms-if="list.msgType==2">
        <p><em>记录标题：</em><a ms-attr-href="${ctx }/record/toRecord/{{list.rid}}"
					class="green_account" target="_self">{{list.title}}</a></p>
        <p><em>事件描述：</em> {{list.description}}</p>
        <p>评论</p>
        <div class="people" ms-if="!list.hasDeleted">
            <img ms-attr-src="list.cuser_headimg?(ibpath + list.cuser_headimg):default_img">
            <ul>
                <li><span>{{list.cusername}}：</span>{{list.comcontent}}</li>
                <li>{{list.comtime}}</li>
            </ul>
            <div class="clear"></div>
            <div ms-each="list.child">
                <ul>
                    <li><span>{{el.cusername}}</span>回复<span>{{el.busername}}：</span><em>{{el.comcontent}}</em></li>
                    <li>{{el.comtime|date("yyyy MM dd")}} <span ms-click="onReplay(el)" class="del">回复</span><span ms-click="del(el)" class="del" ms-visible = "el.cuser_id == el.current_user_id || el.current_user_id==el.rec_user_id">删除</span></li>
                </ul>
                <div class="clear"></div>
            </div>

            <div class="textright">
                <textarea class="messages" placeholder=""></textarea><span ms-click="publish">回复</span>
            </div>
        </div>
        <div class="people" ms-if="list.hasDeleted">
            留言已删除
        </div>
        </div>
        <!-- 系统消息 -->
        <div class="follow" ms-if="list.msgType==3">
            <h3>亲爱的{{list.username}}：</h3>
            <p>您好！</p>
            <p>{{list.messageContent}}</p>
            <p class="tright">天润云地图</p>
        </div>
    </div>
</body>
</html>