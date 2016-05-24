/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2015/11/2
 * Time: 17:03
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {
    require("__/libs/validform");

    var co = require("_/comm");
    var __ = require.resolve("../");
    var cl = require("ctool");
    cl.boostIESelector();

    $(function(){
        var mmqd = $("li.qiangdu").hide();
        mmqd.b = mmqd.find("b");
        mmqd.string = ["","弱","中","强"];


        //图标相关
        $(".item").each(function(i){
            var me=$(this);
            var input = me.find("input");
            input.prev("i").addClass("i_" + input.attr("name"))
        });;




        var demo = $("form").Validform({
            tiptype:function(msg,o,cssctl){
                var m = o.obj;
                var el = m.parent().next("i");

                if(!el.length){
                    el = $("<i class='inputtip'><i/>");
                    m.parent().after(el);
                }

                //先清理掉成功和失败的状态
                el.removeClass("suc err");

                //是不是密码
                var is_pwd = m.is("[name=password]");

                //验证失败
                if(o.type == 3){
                    fail_action();
                //通过验证
                }else if(o.type==2){
                    if(o.obj.is("[ajax_valid]")){
                        demo.waiting ++ ;
                        $.ajax({
                            url:qtenterprise+"/entaccount/validationjson",//jsonp跨域访问要设置header
                            data:{
                                type: m.attr("name"),
                                value: m.val()
                            },
                            type:"get",
                            dataType:"jsonp"
                        })
                            .fail(function(){
                                demo.waiting -- ;
                                if(!demo.waiting) demo.ajax_vl_end();
                                fail_action("重复性检测失败");
                            })
                            .done(function(data){
                                demo.waiting -- ;
                                if(!demo.waiting) demo.ajax_vl_end();
                                if(!data.status){
                                    success_action();
                                }else{
                                    fail_action(m.attr("ajax_valid"));
                                }
                            })
                        ;
                    }else{
                        success_action();
                    }
                }



                function fail_action(_msg){
                    el.html(_msg||msg).addClass("err");
                    m.parent().addClass("error");
                    if(is_pwd){
                        mmqd.hide();
                    }
                }


                function success_action(){
                    el.html("&#xe601;").addClass("suc");
                    m.parent().removeClass("error");
                    if(is_pwd){
                        mmqd.show();
                        var g = AnalyzePasswordSecurityLevel(m.val());
                        mmqd.removeClass("g1 g2 g3 g4").addClass("g" + g);
                        mmqd.b.html(mmqd.string[g]);
                    }
                }

            },
            label:".label",
            showAllError:true,
            datatype:{
                "zh1-6":/^[\u4E00-\u9FA5\uf900-\ufa2d]{1,6}$/
            },
            ajaxPost:false
        });

        demo.waiting = 0;
        demo.ajax_vl_end = function(){ }

        demo.addRule([
            {
                ele:"input:eq(0)",
                datatype:"/^[\\w]{6,20}$/",
                nullmsg:"请输入用户名",
                errormsg:"请输入长度在6到20的数字或者字母"
            },

            {
                ele:"input:eq(1)",
                datatype:"s6-20",
                nullmsg:"请输入企业名称",
                errormsg:"企业名称长度需要在6-20之间"
            },

            {
                ele:"input:eq(2)",
                datatype:"e",
                nullmsg:"请输入邮箱",
                errormsg:"请输入合法的邮箱"
            },

            {
                ele:"input:eq(3)",
                datatype:"m|/(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)/|/(^((\(\d{3}\))|(\d{3}\-))?(1[358]\d{9})$)/",
                nullmsg:"请输入电话号码",
                errormsg:"请输入合法的手机或者座机号"
            },

            {
                ele:"input:eq(4)",
                datatype:"*4-4",
                nullmsg:"请输入验证码",
                errormsg:"验证码不正确"
            },
            {
                ele:"input:eq(5)",
                datatype:"*6-20",
                nullmsg:"请输入密码",
                errormsg:"密码6-20个字符"
            },
            {
                ele:"input:eq(6)",
                datatype:"*",
                nullmsg:"请再次输入密码",
                errormsg:"2次输入密码不一致"
            }
        ]);

        demo.config({
            ajaxurl:{
                success:function(data,obj){
                  //  debugger;
                    //data是返回的json数据;
                    //obj是当前正做实时验证表单元素的jquery对象;
                    //注意：5.3版中，实时验证的返回数据须是含有status值的json数据！
                    //跟callback里的ajax返回数据格式统一，建议不再返回字符串"y"或"n"。目前这两种格式的数据都兼容。
                },
                error:function(data,obj){
                   // debugger;
                }
            }
        })



        var fm = $("form");
        fm.find(".btn").click(function(){
            if(fm.find(".input+.err").length)  return false;

            if(!$("#argument_confirm").is(":checked")){
                cl.layer(function (layer) {layer.msg('请勾选协议！');})
                return false;
            }

            if(demo.check(false)) {
                if(demo.waiting){
                    demo.ajax_vl_end = function(){
                        fm.find(".btn").click();
                    }
                }else{
                    fm.submit();
                }
            }
        })


        //验证码单独处理，补丁
        ({
            init:function(){
                var m = this;
                m.el = $(".safecode input");
                m.a = $(".safecode>a").click(function(){
                    m.el.val("");
                    !function(){
                        demo.check(false,m.el);
                    }.delayCall(450);
                });
                return m;
            }
        }).init();
    });

    return {};




    //密码等级函数
    function AnalyzePasswordSecurityLevel(password) {
        var securityLevelFlag = 0;
        if (password.length < 6) {
            return 0;
        }
        else {
            if (/[a-z]/.test(password)){
                securityLevelFlag++;    //lowercase
            }
            if (/[A-Z]/.test(password)){
                securityLevelFlag++;    //uppercase
            }
            if(/[0-9]/.test(password)){
                securityLevelFlag++;    //digital
            }
            if(containSpecialChar(password)){
                securityLevelFlag++;    //specialcase
            }
            return securityLevelFlag;
        }
    }

    function containSpecialChar(str)
    {
        var containSpecial = RegExp(/[(\ )(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\-)(\_)(\+)(\=)(\[)(\])(\{)(\})(\|)(\\)(\;)(\:)(\')(\")(\,)(\.)(\/)(\<)(\>)(\?)(\)]+/);
        return (containSpecial.test(str));
    }
});

function reRand(){
	$("#randImg").attr("src", qtenterprise+"/entaccount/random?t=" + new Date());
}