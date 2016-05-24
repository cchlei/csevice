<!doctype html><html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/global.jsp"%>
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>对不起，您的账号未激活！</title>
    <link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/common.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/prompt.css"/>
    <link rel="stylesheet" href="http://www.trmap.cn/css/notcie_page_comm.css" />
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode" ></script>
    <script type="text/javascript">
        $(function(){
            var h = $(window).height();
            $(".qyzh_activation").height(h);
        })
    </script>

</head>
<body>
  <div class="top">
	    <div class="mc">
	        <a href="http://www.trmap.cn" class="logo"></a>
	    </div>
    </div>
    <div class="qyzh_activation">
  
    <div class="zccenter">
        <div class="cloud">
        <i class="cloud1"></i>
        <i class="cloud2"></i>
    </div>
        <div class="tishi">
            <p class="ts">您的账号：<em>${param.g}</em>尚未激活，请先登录邮箱进行激活操作。若未收到激活邮件，您可以尝试重新发送邮件或返回首页。</p>
            <p class="ts"><span>邮箱地址：</span><em>${param.a}</em><span style="cursor: pointer;" class="fasong" id="resend_link" onclick="onFaSong()">发送</span><a class="re" href="${ctx}/logout">返回首页</a></p>
        </div>
        <div class="clear"></div>
    </div>
    <img src="${ctx}/assets/images/p_bgw.png" alt="">
</div>
<div class="foot">
    <p class="right">陕ICP备12000810-2号　版权所有：陕西天润科技股份有限公司</p>
</div>
</body>
</html>
<script type="text/javascript">

    //设置超时时间为60秒钟
    var timeout = 60;
    var fasong = $(".fasong");
    var resend;//重发邮件方法
	var resend_link = $("#resend_link");
    function resend(){
    	seajs.usep("__/js/comm,ctooj,cookie",function(co,cj,ck){
	    		$.post('${ctx}/resendmail/${param.code}',null)
	    	    .fail(function () {
	    	    	co.msg("邮件发送失败，请稍后重试！");
	               })
	    		.done(function (data) {
                     data = cj.tojson(data);
	                if (data.result == "success") {
	                 co.msg("邮件发送成功，请登录邮箱完成激活！！");

	    		    }else{
	    		     co.msg("邮件发送失败，请稍后重试！");
	    		    }
	    	   });

    	})

    }
    var timeRunning = false;
    function jishiqi(){
    	timeRunning = true;
    	fasong.html("发送" + timeout--);
        fasong.css("background","#333");
        if(timeout > 0){
        	setTimeout(jishiqi, 1000);
        } else {
        	timeRunning = false;
        	$(".fasong").click(onFaSong);
        	timeout = 60;
        	fasong.html("发送");
        	fasong.css("background","#6487d2");
        }
    }
    function onFaSong(){
    	//发送邮件
    	 resend();
    	//开始倒计时
         $(".fasong").removeAttr("onclick");
         $(".fasong").unbind("click",onFaSong);
         if(timeRunning) return;
         jishiqi();
    }
</script>

