<!DOCTYPE html> <html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>专题-天润云·个人</title>
    <%@ include file="/assets/global.jsp"%>
    <link rel="stylesheet" href="${ctx}/assets/css/comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/mythemes.css"/>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode"></script>
    <script> var href = '${ctx}/topic/tolist'; </script>

    <c:if test="${open==null}">
        <script> href = '${ctx}/topic/tolist'; </script>
    </c:if>
    <c:if test="${open==1}">
        <script> href = '${ctx}/topic/tolist?type=1'; </script>
    </c:if>
    <c:if test="${open==0}">
         <script> href = '${ctx}/topic/tolist?type=0'; </script>
    </c:if>

    <script>
        window.ctx = window.ctx || "";
        var fresh_topic;
        use(["_/avalon.min", "_/tr_util", "__/js/comm"], function (a, u) {
            fresh_topic = function () {
                var ac = arguments.callee;

                u.ajax("___/topic/countTopic".p(), function (t,data) {
                    if (!t.vl()) return;

                    if (!ac.vm) {
                        ac.vm = avalon.define({
                            $id: "subnav",
                            val: t.data
                        })

                        avalon.scan();
                        $("html").addClass("no_cl_loading");
                    }
                    ac.vm.val = data;
                })
            }
            fresh_topic();

            var ha = top.hash_parse;
            if(ha){
                $(document).delegate(".sub_nav>li>a","click",function(e){
                    e.preventDefault();
                    var aa = $(this);
                    var href = aa.attr("href");
                    ha.hash(
                            "#|"+ctx+"/topic/totopic||" +  href
                    );
                    $("#topic").attr("src",href);
                });

                //目标地址没有被，hash指定
                if(ha.hash().indexOf("||")==-1){
                    $("#topic").attr("src",href);
                    //$(".sub_nav>li>a:first").click();
                }
            }else{
                $("#topic").attr("src",href);
            }
        })

    </script>


</head>
<body>

<i class="cl_loading page"></i>
<div class="root frame_deep2">
    <div class="col2">
        <ul class="sub_nav" ms-controller="subnav">
            <li>
                <h3><i class="triangle"></i>我的专题 </h3>
                <a href="${ctx}/topic/tolist" class="bg-color-1" target=topic>所有专题 <em>({{val.alltheme}})</em></a>
                <a href="${ctx}/topic/tolist?type=1" class="bg-color-2" target=topic>公开专题 <em>({{val.opentheme}})</em></a>
                <a href="${ctx}/topic/tolist?type=0" class="bg-color-3" target=topic>私密专题 <em>({{val.privatetheme}})</em></a>
            </li>
            <li>
                <h3><i class="triangle"></i>发现 <i class="add"></i></h3>
                <a href="${ctx}/topic/shareListView" class="bg-color-4"  target=topic>所有分享 <em></em></a>
                <a href="${ctx}/topic/attenview" class="bg-color-5" target=topic>我的关注 <em></em></a>
                <a href="${ctx}/collect/collectview" class="bg-color-6" target=topic>我的收藏 <em></em></a>
                <a href="${ctx}/topic/toshare" class="bg-color-7" target=topic>分享给我 <em></em></a>
            </li>
        </ul>
    </div> 
    <div class="mainCont col3">
        <iframe src="about:blank" frameborder="0" name=topic id=topic></iframe>
    </div>
</div>
</body>
</html>