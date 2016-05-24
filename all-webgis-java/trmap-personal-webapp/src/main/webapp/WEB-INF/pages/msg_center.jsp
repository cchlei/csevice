<!DOCTYPE html> <html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
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
            /*消息数量*/
            message_nubmer:"${ctx}/message/count",

            /*消息数据*/
            message_list:"${ctx}/message/messageList"

            /*删除返回*/
       //     message_del:"${ctx}/" 

        };
    </script>
</head>
<body>
    <i class="cl_loading page"></i>
    <div class="root no_col_1 no_top">

            <div class="col2" ms-controller="number">
                <h1 class="page_name">消息中心</h1>
                <ul>
                    <li><a href="javascript:showmessages(-1,1,10);" class="orange">所有消息({{number.all}})</a></li>
                    <li><a href="javascript:showmessages(1,1,10);" class="blue">已读消息({{number.yyd}})</a></li>
                    <li><a href="javascript:showmessages(0,1,10);" class="red">未读消息({{number.wyd}})</a></li>
                </ul>
            </div>
            <div class="mainCont col3">
                <div class="inner">
                    <h1 class="page_name">所有消息</h1>

                    <div class="main">
                        <ul id="messagesList">
                            <!-- <li class="odd">
                                <span>2016-2-16</span>
                                <strong>
                                    <i class="state-expand"></i>
                                    欢迎进入天润云</strong>
                            </li>
                            <li class="even">
                                <span>2016-2-16</span>
                                <strong>
                                    <i class="state-expand_mail"></i>
                                    神马不是马添加您为好友。 </strong>

                            </li> -->
                        </ul>

                        <div class="clear"></div>
                        <div id="resultbottom" style="height:20px; text-align:center; width:960px; float:left;">
					   <!--  首页 -->
					    <a  href="javascript:void(0)" onclick="displayres(1,4)">
					    <img src="${ctx}/assets/images/scroll_first.jpg" style="position:relative;top:5px;border:none;cursor:pointer">
					    </a>&nbsp;
					   <!--  上页 -->
					      <a  href="javascript:displayres('prev',4)" >
					    <img src="${ctx}/assets/images/scroll_prev.jpg" style="position:relative;top:5px;border:none;cursor:pointer"></a>&nbsp;
					    
					    <font size="3" weight="bold" family="微软雅黑" id="pageNum">第pageNum页/共rows页</font>&nbsp;
					   
					   <!-- 下页 -->
					     <a  href="javascript:displayres('next',4)" >
					    <img src="${ctx}/assets/images/scroll_next.jpg" style="position:relative;top:5px;border:none;cursor:pointer">
					    </a>&nbsp;
					    <!-- 尾页 -->
					    <a  href="javascript:void(0)" onclick="displayres('rows',4)">
					    <img src="${ctx}/assets/images/scroll_end.jpg" style="position:relative;top:5px;border:none;cursor:pointer"></a>&nbsp;
                      </div> 

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
            <p><em>记录标题：</em> {{list.title}}</p>
            <p><em>事件描述：</em> {{list.description}}</p>
            <p>评论</p>
            <div class="people">
                <img ms-attr-src="list.cuser_headimg || default_img">
                <ul>
                    <li><span>{{list.cusername}}：</span>{{list.comcontent}}</li>
                    <li>{{list.comtime}}</li>
                </ul>
            </div>
        </div>
    </div>

</body>
</html>