/*
 *重置密码
 * */
define(function(require,exports,module){
    var cj = require("ctooj");
    require("_/validform");
    var comm = require("__/js/comm");
    $(function(){
        var zccenter = $(".zccenter1");
        var email =zccenter.find("#email")
        var validcode =zccenter.find("#validcode")
        zccenter.find(".next").click(function(){
            var pass = $("#email").val();
            if(!vlform1.check()) {
                return;
            }

            if(vlform1.forms.find(".errtip.err").length) return '';
            vlform1.ajaxPost();
//            vlform1.forms.submit();
        })

        var vlform1 = zccenter.Validform({
            ajaxPost:true,
            callback:function(data){
                data = cj.tojson(data);
                if(data.result == "success"){
                	//发送重置密码邮件
                	comm.msg("用户重置密码邮件发送成功，请到邮件完成密码重置！");
                }else{
                	comm.msg(data.msg);
                }
            },
            tiptype: function (msg, o, cssctl) {
                var m = o.obj;
                var p = m.parents("li:first");

                //寻找或者创建错误提示元素
                var el = p.find(".errtip");
                if (!el.length) {
                    p.append("<span class='errtip'></span>");
                    el = p.find(".errtip");
                }
                el.html("");


                //先清理掉成功和失败的状态
                el.removeClass("suc err");

                //验证失败
                if (o.type == 3) {
                    fail_action();

                    //通过验证
                } else if (o.type == 2) {
                    success_action();
                }


                function fail_action(_msg) {
                    el.html(_msg || msg).addClass("err");
                    m.parent().addClass("error");
                }

                function success_action() {
                    // el.html("&#xe601;").addClass("suc");
                    // m.parent().removeClass("error");
                }

            },
            //showAllError: true,
            datatype: {
                "zh1-6": /^[\u4E00-\u9FA5\uf900-\ufa2d]{1,6}$/
            },
        });


        //验证码单独处理，补丁
             ({
                 init:function(){
                     var m = this;
                     m.el = $(".safecode input");
                     m.a = $(".safecode>a").click(function(){
                         m.el.val("");
                         !function(){
                         	vlform1.check(false,m.el);
                         }.delayCall(450);
                     });
                     return m;
                 }
             }).init();
        
        
        vlform1.addRule([
            {
                ele: "#email",
                datatype: "/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,4}$/",
                nullmsg: "请填写邮箱",
                errormsg: "请输入正确邮箱地址"
            },
            {
                ele:"#validcode",
                datatype:"*",
                nullmsg:"请输入验证码",
                errormsg:"验证码不能为空"
            }

        ]);

        $(".ascertain").click(function(){
            var pass = $("#password").val();
            if(pass == "") {
            	comm.msg("密码不能为空！");
                return;
            }
            if(!vlform.check()) {
                return;
            }
            if(vlform.forms.find(".errtip.err").length) return '';
            comm.ajax(ctx+"/validateRequest",{"u":$('#u').val(),"k":$('#k').val()},function(tk){
                if(tk.vl(true)){
                	vlform.forms.submit();
                }else{
                	comm.msg(tk.msg);
                }
            });
        })
        var vlform = $(".zccenter").Validform({
            tiptype: function (msg, o, cssctl) {
                var m = o.obj;
                var p = m.parents("li:first");

                //寻找或者创建错误提示元素
                var el = p.find(".errtip");
                if (!el.length) {
                    p.append("<span class='errtip'></span>");
                    el = p.find(".errtip");
                }
                el.html("");


                //先清理掉成功和失败的状态
                el.removeClass("suc err");

                //验证失败
                if (o.type == 3) {
                    fail_action();

                    //通过验证
                } else if (o.type == 2) {
                    success_action();
                }


                function fail_action(_msg) {
                    el.html(_msg || msg).addClass("err");
                    m.parent().addClass("error");
                }

                function success_action() {
                    // el.html("&#xe601;").addClass("suc");
                    // m.parent().removeClass("error");
                }

            },
//            showAllError: true,
            datatype: {
                "zh1-6": /^[\u4E00-\u9FA5\uf900-\ufa2d]{1,6}$/
            },
        });
        vlform.addRule([
            {
                ele: "#password",
                datatype: "*6-20",
                nullmsg: "请填写新密码",
                errormsg: "长度6-20个字母或数字"
            },
            {
                ele: "#passwordagain",
                datatype: "*",
                nullmsg: "请再次输入密码",
                errormsg: "密码不一致！"
            }
        ]);
    })
    return{};
})


function reRand(){
	$("#randImg").attr("src", ctx+"/random?t=" + new Date());
}