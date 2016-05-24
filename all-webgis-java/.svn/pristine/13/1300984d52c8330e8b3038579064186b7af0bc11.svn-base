/**
 *
 * 计算服务申请
 *
 */
define(function (require, exports, module) {
    var cl = require("ctool");
    var cj = require("ctooj");
    var _=require.resolve("../");
    var comm = require("__/js/comm");
    var parseTpl = require("parseTpl");


    //计算服务申请页面top进度图片
    var jdimg=$(".jdimg");

    /*申请使用时间*/
    var form =$(".order>.form");
    var applyusetime =$(".applyusetime");
    var yxq =$(".applyusetime>p>span.yxq");
    var sqsucess= $(".sqsucess");
    yxq.click(function(){
        var m =$(this);
        m.toggleClass("ed");
        if(m.hasClass("ed")){
            $("[name=usetime]").val(12)
        }
    })

    $("html").click(function(){
        var times = $("[name=usetime]").val();
        if(times !== 12){
            yxq.removeClass("ed");
        }
        if(times == 12){
            yxq.addClass("ed");
        }
    })




    $(".applyusetime>p.next").click(function(){
        var times = $("[name=usetime]").val();

        if(times == ""){
            comm.msg("申请时间不能为空");
        }else{
            form.show();
            applyusetime.hide();
            sqsucess.hide();
            jdimg.attr("src",ctx+"/assert/images/qpply2.jpg");

            /*计算服务申请信息生成*/
            var dwxx =$("table.dwxx");
            var fwxx =$("table.fwxx");
            comm.ajax(window.service_apply_from_ajax || (_+"data/js_service_apply.json"),function(tk,data){
                if(tk.vl()){
                    require.async("parseTpl",function(PT){
                        data = cj.tojson(data);
                        $(".sqsytime").text(times);
                        dwxx.html(
                            PT(
                                data.rows,
                                '<tr><td colspan="6" align="center" class="ordertitle" width="812">单位信息</td></tr>'+
                                '<tr><td rowspan="4" height="146" width="69" class="ordercenter"><p>服务申请方</p></td>'+
                                '<td width="100" class="tit1">单位名称</td>'+
                                '<td width="233">{sqcompany}{sqauthentication}</td>'+
                                '<td rowspan="4" width="69" class="ordercenter"><p>服务发布方</p></td>'+
                                '<td width="100" class="tit1">单位名称</td>'+
                                '<td width="234">{fbcompany}{fbauthentication}</td></tr>'+
                                '<tr><td class="tit1">联系人</td><td>{sqcontacts}</td>'+
                                '<td class="tit1">联系人</td><td>{fbcontacts}</td></tr>'+
                                '<tr><td class="tit1">邮箱</td><td>{sqemail}</td>'+
                                '<td class="tit1">邮箱</td><td>{fbemail}</td></tr>'+
                                '<tr><td class="tit1">电话</td><td>{sqphone}</td>'+
                                '<td class="tit1">电话</td><td>{fbphone}</td></tr>'
                            )
                        )

                        //服务信息
                        fwxx.html(
                            PT(
                                data.rows,
                                '<tr><td colspan="4" align="center" class="ordertitle" width="811">服务信息</td></tr>'+
                                '<tr>'+
                                '<td class="tit1 bg">服务名称</td>'+
                                '<td>{servicename}</td>'+
                                '<td class="tit1 bg">发布时间</td>'+
                                '<td>{releasetime}</td>'+
                                '</tr>'+
                                '<tr>'+
                                '<td class="tit1 bg">更新时间</td>'+
                                '<td>{refreshtime}</td>'+
                                '<td class="tit1 bg">截止时间</td>'+
                                '<td>{enddate}</td>'+
                                '</tr>'+
                                '<tr>'+
                                '<td class="tit1 bg">范围</td>'+
                                '<td colspan="3">{range}</td>'+
                                '</tr>'+
                                '<tr>'+
                                '<td class="tit1 bg">输入</td>'+
                                '<td>{entry}</td>'+
                                '<td class="tit1 bg">输出</td>'+
                                '<td></td>'+
                                '</tr>'+
                                '<tr>'+
                                '<td class="tit1 bg">服务描述</td>'+
                                '<td colspan="3">{description}</td>'+
                                '</tr>'
                            )
                        )

                    })
                }else{
                    comm.msg("失败");
                }
            });

        }


        /*$.get(window.service_apply_from_ajax || (_+"data/service_apply.json"))
            .fail(function(){
                throw "获取信息失败";
            })
            .done(function(data){
                require.async("parseTpl",function(PT){
                    data = cj.tojson(data);
                    dwxx.html(
                        PT(
                            data.rows,
                            '<tr><td colspan="6" align="center" class="ordertitle" width="812">单位信息</td></tr>'+
                            '<tr><td rowspan="4" height="146" width="69" class="ordercenter"><p>服务申请方</p></td>'+
                            '<td width="100" class="tit1">单位名称</td>'+
                            '<td width="233">{sqcompany}{sqauthentication}</td>'+
                            '<td rowspan="4" width="69" class="ordercenter"><p>服务发布方</p></td>'+
                            '<td width="100" class="tit1">单位名称</td>'+
                            '<td width="234">{fbcompany}{fbauthentication}</td></tr>'+
                            '<tr><td class="tit1">联系人</td><td>{sqcontacts}</td>'+
                            '<td class="tit1">联系人</td><td>{fbcontacts}</td></tr>'+
                            '<tr><td class="tit1">邮箱</td><td>{sqemail}</td>'+
                            '<td class="tit1">邮箱</td><td>{fbemail}</td></tr>'+
                            '<tr><td class="tit1">电话</td><td>{sqphone}</td>'+
                            '<td class="tit1">电话</td><td>{fbphone}</td></tr>'
                        )
                    )

                    //服务信息
                    fwxx.html(
                        PT(
                            data.rows,
                            '<tr><td colspan="4" align="center" class="ordertitle" width="811">服务信息</td></tr>'+
                            '<tr>'+
                            '<td class="tit1 bg">服务名称</td>'+
                            '<td>{servicename}</td>'+
                            '<td class="tit1 bg">发布时间</td>'+
                            '<td>{releasetime}</td>'+
                            '</tr>'+
                            '<tr>'+
                            '<td class="tit1 bg">更新时间</td>'+
                            '<td>{refreshtime}</td>'+
                            '<td class="tit1 bg">截止时间</td>'+
                            '<td>{enddate}</td>'+
                            '</tr>'+
                            '<tr>'+
                            '<td class="tit1 bg">范围</td>'+
                            '<td colspan="3">{range}</td>'+
                            '</tr>'+
                            '<tr>'+
                            '<td class="tit1 bg">输入</td>'+
                            '<td>{entry}</td>'+
                            '<td class="tit1 bg">输出</td>'+
                            '<td></td>'+
                            '</tr>'+
                            '<tr>'+
                            '<td class="tit1 bg">服务描述</td>'+
                            '<td colspan="3">{description}</td>'+
                            '</tr>'
                        )
                    )

                    //申请使用时间
                    sysj.html(
                        PT(
                            data.rows,
                            '<tr><td colspan="4" align="center" class="ordertitle" width="811">申请信息</td></tr>'+
                            '<tr>'+
                            '<td class="tit1 bg">申请使用时间</td>'+
                            '<td colspan="3">{usetime}</td>'+
                            '</tr>'
                        )
                    )
                })
            })*/


    })

    //上一步
    $(".back").click(function(){
        form.hide();
        applyusetime.show();
        jdimg.attr("src",ctx+"/assert/images/qpply1.jpg");
    })

    //点击确定
    $(".form .sure").click(function(){


        show();

        //提交表单内容
        comm.ajax(window.apply_time_ajax,{"sqtime":$(".sqsytime").text()},function(tk){
            if(tk.vl(true)){
                form.hide();
                applyusetime.hide();
                sqsucess.show();
                jdimg.attr("src","../images/qpply3.jpg");
            }else{
                form.hide();
                jdimg.hide();
                applyusetime.hide();
                sqsucess.hide();
                $(".sqfail").show();

            }
        });


    })



    return {};
})


/*
comm.ajax(window.sqtime_ajax || (_+"data/sava_profile.json"),para,function(tk,data){
    if(tk.vl()){
        form.show();
        applyusetime.hide();
        sqsucess.hide();
        jdimg.attr("src","../images/qpply2.jpg");
    }else{
        comm.msg("失败");
    }
});*/
