<!DOCTYPE html> <html class="_main">
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>天润云·个人</title>
    <%@ include file="/assets/global.jsp"%>
    <link rel="stylesheet" href="${ctx}/assets/css/comm.css"/>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode" main="__/js/main"></script>
    <script>
        var pageCfg = {
            /*左侧分组*/
            msgcenter: "${ctx}/message/count"
        };
    </script>
</head>
<body>
    <i class="cl_loading page"></i>
    <div class="root no_col2">
        <div class="topbar">
            <a class="logo" href="#"> <i></i> <b></b> </a>
            <a href="#" class="user_header"><img src="http://oss.trmap.cn/rs/download/${headimg}"></a>
            <script>
                var hd_img = $("img:last").error(function(){
                    use("__/js/comm",function(comm){
                        hd_img.attr("src","http://a.trmap.cn/assets/images/hd.png");
                        hd_img.error(function(){
                            hd_img.attr("src",comm.theme_thumb_default);
                        });
                    })
                })
            </script>

                <span class="user_name">
                    欢迎 <a href="#">${username}</a> <b>|</b> <a href="${ctx}/logout">退出</a>
                </span>
        </div>
        <div class="nav col1">
            <a target="main" href="${ctx}/topic/totopic">
                <i><img src="${ctx}/assets/images/icon_zhuanti.png" alt=""></i>
                <b>专题</b>
            </a>

            <a target="main" href="${ctx}/topic/tocalendar">
                <i><img src="${ctx}/assets/images/icon_rili.png" alt=""></i>
                <b>日历</b>
            </a>


            <a target="main"  href="${ctx}/attention/toaddens">
                <i><img src="${ctx}/assets/images/icon_haoyou.png" alt=""></i>
                <b>好友</b>
            </a>

            <a target="main"  href="${ctx}/message/tocenter">
                <i><img src="${ctx}/assets/images/icon_xiaoxi.png" alt=""></i>
                <b>消息</b>
            </a>


            <a target="main" href="${ctx}/account/page">
                <i><img src="${ctx}/assets/images/icon_zhanghu.png" alt=""></i>
                <b>账户</b>
            </a>
        </div>

        <div class="col3">
            <iframe src="about:blank" frameborder="0" id="main" name="main"></iframe>
        </div>
    </div>
</body>
</html>