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

    var cl = require("ctool");
    cl.boostIESelector();

    $(function(){

        var mmqd = $("li.qiangdu").hide();
        mmqd.b = mmqd.find("b");
        mmqd.string = ["","弱","中","强"];

        var demo = $("form").Validform({
            tiptype:function(msg,o,cssctl){
                var m = o.obj;
                var el = m.parent().next("i");

                if(!el.length){
                    el = $("<i class='inputtip'><i/>");
                    m.parent().after(el);
                }

                el.removeClass("suc err");

                var is_pwd = m.is("[name=password]");

                //验证失败
                if(o.type == 3){
                    fail_action();
                //通过验证
                }else if(o.type==2){

                    //重复性检测
                    if(o.obj.is("[ajax_valid]")){
                        demo.waiting ++ ;
                        $.ajax({
                            url:"http://user.trmap.cn/account/validation",
                            data:{
                                type: m.attr("name"),
                                value: m.val()
                            },
                            dataType:"jsonp"
                        })
                            .fail(function(){
                                demo.waiting -- ;
                                if(!demo.waiting) demo.ajax_vl_end();

                                fail_action("重复性检测失败");
                            })
                            .done(function(data){

                                if($.trim(data) == "true"){
                                    success_action();
                                }else{
                                    fail_action(m.attr("ajax_valid_errmsg"));
                                }
                                demo.waiting -- ;
                                if(!demo.waiting) demo.ajax_vl_end();
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


        //通过$.Tipmsg扩展默认提示信息;
        //$.Tipmsg.w["zh1-6"]="请输入1到6个中文字符！";
        demo.tipmsg.w["zh1-6"]="请输入1到6个中文字符！";

        demo.addRule([
            {
                ele:"input:eq(0)",
              //datatype:"/^[\\dA-z]{6,20}$/",
                datatype:"/^[\\w]{6,20}$/",
                nullmsg:"请输入用户名",
                errormsg:"请输入6到20的数字或字母"
            },

            {
                ele:"input:eq(1)",
                datatype:"e",
                nullmsg:"请输入邮箱",
                errormsg:"请输入合法的邮箱"
            },
            {
                ele:"input:eq(2)",
                datatype:"*6-20",
                nullmsg:"请输入密码",
                errormsg:"密码长度需要在6-20之间"
            },
            {
                ele:"input:eq(3)",
                datatype:"*",
                nullmsg:"请再次输入密码",
                errormsg:"密码不一致"
            }
        ]);

        demo.config({
            ajaxurl:{
                success:function(data,obj){
                    debugger;
                    //data是返回的json数据;
                    //obj是当前正做实时验证表单元素的jquery对象;
                    //注意：5.3版中，实时验证的返回数据须是含有status值的json数据！
                    //跟callback里的ajax返回数据格式统一，建议不再返回字符串"y"或"n"。目前这两种格式的数据都兼容。
                },
                error:function(data,obj){
                    debugger;
                }
            }
        })



        var fm = $("form");
        fm.find(".btn").click(function(){

            //验证错误存在
            if(fm.find(".input+.err").length)  return;

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

        return {};

    });











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