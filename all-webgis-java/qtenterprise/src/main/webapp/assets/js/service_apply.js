/**
 *
 * 服务申请
 *
 */
define(function (require, exports, module) {
    var cl = require("ctool");
    var cj = require("ctooj");
    var _=require.resolve("../");
    var comm = require("__/js/comm");
    var parseTpl = require("parseTpl");
    var jqueryui = require("_/jqueryui/jquery-ui");

    //申请试用时间框
    $( "#spinner" ).spinner({
        min: 0
    });


    //计算服务申请页面top进度图片
    var jdimg=$(".jdimg");
    var form =$(".order>.form");
    var applyusetime =$(".applyusetime");
    var sqsucess= $(".sqsucess");


    var vm;
    //加载服务申请接口类型、图层字段
    comm.ajax(window.service_apply_type_ajax || (_+"data/service_apply_type.json"),function(tk,data){
        if(tk.vl()) {

            data.rows.jktype = $.map(data.rows.jktype,function(v,k){
                return {
                    name:v,
                    checked:true
                }
            })




            require.async("_/avalon/avalon.min",function(){
                vm = avalon.define({
                    $id:"order",
                    data:data.rows,
                    tbdata:{},
                    oncheck:function(el){
                        el.checked = !el.checked;
                    }
                })
                avalon.scan();
                $(".order").show()
            });
        }else{
            comm.msg("失败");
        }
    })


    //同意协议
    var xieyi =$(".xieyi>i")
    xieyi.click(function(){
        var xieyi =$(this);
        xieyi.toggleClass("ed");
    })

    //获取申请时间
    var times = $("[name=usetime]");
    var shuzi=/^[1-9]\d{0,2}$/

    var jktype;
    $(".applyusetime p.next").click(function(){

        if(!jktype){
            jktype =$(".jktype>span>i");
        }
        if(!$.trim(times.val())){
            comm.msg("请正确填写申请时间");
        }else if(!shuzi.test(times.val())){
            comm.msg("请正确填写申请时间且不可超过三位数");
        }else if(!jktype.hasClass("ed")){
            comm.msg("请选择接口类型");
        }else if(!xieyi.hasClass("ed")){
            comm.msg("未选中同意协议选框");
        }else{
            form.show();
            applyusetime.hide();
            sqsucess.hide();
            jdimg.attr("src",comm.ctx()+"/assets/images/qpply2.jpg");

            /*计算服务申请信息生成*/
            $(".sysj .stime").text(times.val()+"个月");
            var dwxx =$("table.dwxx");
            var fwxx =$("table.fwxx");
            comm.ajax(window.service_apply_from_ajax || (_+"data/service_apply.json"),function(tk,data){
                if(tk.vl()){
                    require.async("parseTpl",function(PT){
                        vm.tbdata = data.rows;
                    })
                }else{
                    comm.msg("失败");
                }
            });

        }


    })

    //上一步
    $(".back").click(function(){
        form.hide();
        applyusetime.show();
        jdimg.attr("src",ctx+"/assets/images/qpply1.jpg");
    })

    //点击确定
    var laye;
    $(".form .sure").click(function(){
//        show();
        if(!laye){
            laye =$(".layer>span");
        }
        //提交表单内容
        var para={
            "sqtime":times.val(),
            "serviceType":$(".jktype>span>i.ed").map(function(k,v){
                return $(v).parent().text()
            }).toArray().join(","),
//            "mrlayer":$(".mrtype>[code]").map(function(k,v){
//                return $(v).attr("code")
//            }).toArray().join(","),
            "attributes":$(".layer>span>i.ed").map(function(k,v){
                return $(v).parent().attr("code")
            }).toArray().join(",")
        }
        comm.ajax(window.apply_time_ajax,para,function(tk){
            if(tk.vl(true)){
                form.hide();
                applyusetime.hide();
                sqsucess.show();
                jdimg.attr("src",ctx+"/assets/images/qpply3.jpg");
            }else{
                form.hide();
                jdimg.hide();
                applyusetime.hide();
                sqsucess.hide();
                $(".sqfail").show().find(".reason").text(tk.msg);

            }
        });
    })
    return {};
})
