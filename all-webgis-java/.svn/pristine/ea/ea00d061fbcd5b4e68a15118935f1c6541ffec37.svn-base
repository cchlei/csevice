<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/global.jsp"%>
<!doctype html><html>
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <link rel="stylesheet" href="${ctx}/css/common.css"/>
    <link rel="stylesheet" href="${ctx}/css/qtmap.css"/>
    <link rel="stylesheet" href="${ctx}/css/notcie_page_comm.css"/>
    <script src="${ctx}/js/cseajs/csea$.js" id="seajsnode" main="_/notice_page_comm"></script>
    <title></title>
</head>
<body>
    <div class="navbar">
        <div class="mc">
            <a href="#" class="logo"></a>
            <!--<span class="webname">天润云地图</span>-->
                <span class="nav">
                    <a href="#">个人</a>
                    <a href="#">企业</a>
                    <a href="#">行业</a>
                    <a href="#">云服务</a>
                    <a href="#">开发者</a>
                    <a href="#">联系我们</a>
                </span>
                <span class="btns"> <a href="http://www.trmap.cn">登录</a> <a href="#">注册</a> </span>
        </div>
    </div>


    <div id="page_cont">
        <div class="midcont mc">
            <table>
                <tr>
                    <td style="padding-right: 8em; width: 30%;"><img src="${ctx}/images/mail_.png" alt=""></td>
                    <td style="line-height: 2.5em;" class="lay_a">
                        <h3>恭喜您，注册成功！</h3>

                        <p class="gap"></p> <p class="gap"></p> <p class="gap"></p>

                        我们已经向您的注册邮箱发送了一封激活邮件，请点击邮件中的激活链接完成帐号激活！

                        <br>
                        <a style="color: #FF8F5B; margin: 0.5em 0;">您的注册邮箱是：${user.email}</a>

                        <p class="gap"></p> <p class="gap"></p> <p class="gap"></p>

                        <a href="http://www.hao123.com/mail" class="btn">立即前往邮箱</a> &nbsp;&nbsp;&nbsp;

                        <p class="gap"></p> <p class="gap"></p> <p class="gap"></p>

                        我没有收到邮件? <br>
                        请确认邮件是否被拦截 <br>
                        尝试<a href="#" style="color: #ff8f5b;" class="fasong" id="resend_link" onclick="resend()" >重新发送</a>邮件 <br>
                        <a href="#">换一个邮箱试试</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>

    <div class="bott">
        <p class="mc botmenu">
            <a href="#">关于天润云</a> <i></i>
            <a href="#">常见问题</a> <i></i>
            <a href="#">服务保障</a> <i></i>
            <a href="#">加入我们</a>
        </p>

        <p class="copyright">
            陕ICP备12000810-2号 版权所有：
            <a href="//trgis.com" target="_blank">陕西天润科技股份有限公司</a> &nbsp;&nbsp;
            地址:西安市雁塔北路8号 &nbsp;&nbsp;
            邮箱<a href="mailto:contact@trgis.com">contact@trgis.com</a>
        </p>

    </div>

</body>
</html>

<script type="text/javascript">
   function  resend(){
	    var id="${user.id}";
      	seajs.usep("ctooj,cookie,$/layer",function(cj,ck,layer){
	    	
	    		$.post('${ctx}/account/resendmail',{
	    			'userId':id
	    		})
	    	    .fail(function () {
	    	    	layer.msg("邮件发送失败，请稍后重试！");
	               })
	    		.done(function (data) {
                      data = cj.tojson(data);
	                if (data.result == "success") {
	                	layer.msg("邮件发送成功，请登录邮箱完成激活！！");
	                	
	    		    }else{
	    		    	layer.msg("邮件发送失败，请稍后重试！");
	    		    }
	    	   });
	    
   	})
   	
   }
</script>

