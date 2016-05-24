<!doctype html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ include file="/assets/global.jsp"%>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <title>重置密码</title>
    <link rel="stylesheet" href="${ctx}/assets/css/tr_comm.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/prompt.css"/>
    <script src="${ctx}/assets/libs/cseajs/csea$.js" id="seajsnode" ></script>
</head> 
<body>  
<div class="resetpassword">
    <div class="topp"></div>
    <form class="zccenter" action="${ctx}/entaccount/resetpass" method="post">
        <ul>
            <li><span>旧的登录密码:</span><input id="oldpassword" ajax_valid_errmsg="旧密码错误" name="oldpassword" class="ipt" placeholder="请输入密码" type="password" value="" size="20" placeholder="长度6-20个字符" autocomplete="off"></li>
            <li><span>新的登录密码:</span><input id="newpassword" name="newpassword" class="ipt" placeholder="请输入密码" type="password" value="" size="20" autocomplete="off"></li>
            <li><span>确认新的登录密码:</span><input id="passwordagain" name="renewpassword" recheck="newpassword" class="ipt" placeholder="请输入密码" type="password" value="" size="20" placeholder="请再一次输入密码" autocomplete="off"></li>
            <li class="ascertain">确定</li>
            <input type="hidden" name="userId" value="${userId}"/>
            
        </ul> 
    </form>
</div>
<script>
    seajs.usep("_/validform,ctooj",function(e,cj){
        var vlform = $(".zccenter").Validform({
            tiptype:function(msg,o,cssctl){
                var m = o.obj;
                var p = m.parents("li:first");

                //寻找或者创建错误提示元素
                var el = p.find(".errtip");
                if(!el.length){
                    p.append("<span class='errtip'></span>");
                    el = p.find(".errtip");
                }
                el.html("");



                //先清理掉成功和失败的状态
                el.removeClass("suc err");

                //验证失败
                if(o.type == 3){
                    fail_action();

                    //通过验证
                }else if(o.type==2){
                	
                    //进行ajax验证
                    if(o.obj.is("[ajax_valid_errmsg]")){
                        $.ajax({
                            url:ctx+"/entaccount/validation",
                            data:{
                                type: m.attr("name"),
                                value: m.val(),
                                userId:${userId}
                            },
                            type:"post",
                            dataType:"jsonp"
                            })
                            .fail(function(){
                                fail_action("密码错误");
                            })
                            .done(function(data){
                                if($.trim(data) == "true"){
                                    success_action();
                                }else{
                                    fail_action(m.attr("ajax_valid_errmsg"));
                                }
                            });
                    }else{
                        success_action();
                    }
                }


                function fail_action(_msg){
                    el.html(_msg||msg).addClass("err");
                    m.parent().addClass("error");
                }

                function success_action(){
                    // el.html("&#xe601;").addClass("suc");
                    // m.parent().removeClass("error");
                }

            },
            showAllError:true,
            datatype:{
                "zh1-6":/^[\u4E00-\u9FA5\uf900-\ufa2d]{1,6}$/
            },
            ajaxPost:false
        });

        vlform.addRule([
             {
                 ele:"input:eq(0)",
                 datatype:"*6-20",
                 nullmsg:"请填写旧密码",
                 errormsg:"密码6-20个字符"
             },
            {
                ele:"input:eq(1)",
                datatype:"*6-20",
                nullmsg:"请填写新密码",
                errormsg:"密码6-20个字符"
            },
            {
                ele:"input:eq(2)",
                datatype:"*6-20",
                nullmsg:"请填写密码",
                errormsg:"两次输入的内容不一致！"
            }

        ]);
       
        var fm = $("form");
       
        fm.find(".ascertain").click(function(){
        	if(!vlform.check(false)) {
                return;
            }
            if(fm.find("input+.err").length) return false;
            fm[0].submit();
         })

    });
   </script>
</body>
</html>
