/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2015/11/27
 * Time: 9:31
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {
    var cl = require("ctool");
    var parseTpl = require("parseTpl");
    var cj = require("ctooj");
    var ck = require("cookie");
    var _ = require.resolve("./");
    var __ = require.resolve("../");
    var co = require("__/js/comm");
    var comm = co;

 
    parseTpl.addVars({
        root: "loasdfasfkaksfd/err.jpg"
    });

    $(function () {
        setTimeout(function () {
            $(".conter .service ul li p").show();
        }, 1000);
    })
    var vm;
    comm.ajax(window.service_information_ajax || (__+"data/shuju.json"),function(tk,data){
        if(tk.vl()){
            require.async("_/avalon/avalon.min",function(){
                vm = avalon.define({
                    $id:"wrap",
                    data:data
                })
                avalon.scan();
            });
        }else{
            comm.msg("服务信息加载失败！");
        }
    })



    /************logoupload start********************************/
    var logo_file_picker = $("#filePicker");
    if(window.WebUploader){
        if ( !WebUploader.Uploader.support() ) {
        	co.msg('Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
            throw new Error( 'WebUploader does not support the browser you are using.' );
        }
        var logouploader = WebUploader.create({

            // swf文件路径
            //swf: BASE_URL + '/js/Uploader.swf',
            // 选完文件后，是否自动上传。
            auto: true,
            // 文件接收服务端。--测试
            server: ctx+'/entfile/upload',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
//            pick: logo_file_picker,
            pick: {
            	"id":logo_file_picker,
                multiple:false
            },
            // 只允许选择文件，可选。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            },
            // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            resize: false



        });

        logouploader.on("fileQueued",function(file,data){

            logouploader.makeThumb(file,function(err,src){
                logo_file_picker.prev(".images").find("img").attr("src",src);
            })

        })
        // 文件上传成功，显示上传成功，并将返回的字段提交到后台保存。
        logouploader.on("uploadSuccess",function(file,response){
            var id = response._raw;
            //将上传图片返回的编号保存到用户对应的 头像里面
            $.get(ctx+"/entaccount/update",{"url":id})
                .fail(function(){
                	co.msg("上传头像失败！");
                })
                .done(function (data) {
                    data = cj.tojson(data);

                    if (data.result == "success") {
                    	co.msg(data.msg);
                        (callback || $.noop)();
                    } else {
                    	co.msg(data.msg);
                    }
                });
        })
        // 文件上传失败，显示上传出错。
        logouploader.on( 'uploadError',function(file) {
        	co.msg("上传头像失败！");
        });
    }
    /************logoupload end********************************/
    
    
    //左侧高度
    var h = $(document).height();
    var nlh = $(".navleft").height(h);

    //右侧宽度
    var w = $(window).width();
    var navright = $(".navright").width(w);
    //右侧basicright宽度
    var basicright = $(".basicright");
    $(".basicright").width(w - 221);

    var nr = $(".navright");
    var nl = $(".navleft");


    !function(){
        var img = nr.find(".images>img");
        img.blankImg();
    }();




//左侧列表获取
    $.get(nav_data_url)
        .fail(function () {
            co.msg("获取导航列表失败");
        })
        .done(function (data) {
            // 2015-12-2 16:03:59
            // 张谦.$.parseJSON后 data对象为null
//            data = $.parseJSON(data);
            data = cj.tojson(data);

            var tpl = '<dl {typename}><dt href={href}><i {typeicon}{root}></i>{name}</dt></dl>';
            var tpl2 = '<dd {typename} href="{href}"><i {typeicon}></i>{name}</dd>';
            var tpl3 = '<li {typename} href="{href}"><i {typeicon}></i>{name}</li>';

            var tpl_onreplace = function (valeu, name) {
                if (name == "typename") {
                    if (rg.test(this.icon)) {
                        return "";
                    } else {
                        return "typename=" + this.icon;
                    }
                } else if (name == "typeicon") {
                    if (!rg.test(this.icon)) {
                        return "";
                    } else {
                        return "style=background-image:url(" + this.icon + ")";
                    }
                }
            }

            var rg = /\./;

            $.each(data.menus, function (key1, el1) {
                var menu = parseTpl(
                    el1,
                    tpl,
                    tpl_onreplace
                );
                menu = $(menu).appendTo(nl);
                if (el1.subs) {
                    var subs = "";
                    $.each(el1.subs, function (k2, el2) {
                        subs = parseTpl(
                            el2,
                            tpl2,
                            tpl_onreplace
                        );
                        menu.append(subs);

                        if (el2.three) {
                            var three = $("<ul ></ul>")
                            $.each(el2.three, function (k3, el3) {
                                three.append(parseTpl(
                                    el3,
                                    tpl3,
                                    tpl_onreplace
                                ));
                            })
                            menu.append(three);
                        }

                    });
                }


            })




            var ifr = $("#main").hide();

            var all_href = nl.find("[href]");
            var this_index = 0;

            nl.delegate("[href]", "click", function () {
                var m = $(this);
                var href = m.attr("href");
                if (href == undefined || href == "undefined" || href === null || href == "null" || href=="#") {
                    href = "";
                }


                if (href) {
                    this_index = all_href.index(m);
                    ifr.show();
                    ifr.prop("src", href);
                    nr.find(".top,.conter").hide();
                    location.hash = "#" + this_index;
                }

            });


            //上下收缩
            $(".navleft>dl>dt").click(function (e) {
                var mm = $(this);
                var em = mm.find("em");
                if ($(e.target).is(em)) {
                    return;
                }
                mm.toggleClass("ed").siblings().slideToggle();
                if (mm.hasClass("ed")) {
                    mm.attr("style", "border-left:2px #5a7bc0 solid;")
                } else {
                    mm.attr("style", "border-left:0px;")
                }
            })
            $(".navleft>dl>dd").click(function () {
                var mmm = $(this);
                mmm.next("ul").slideToggle();
            })


            //默认展开第二个菜单
            $("dl[typename=fwgx]>dt:first").click();

            //左右侧收缩
            $("em.switch").click(function () {
                var m = $(this);
                m.toggleClass("active");
                var logo = $(".navleft>.top>i");//logo变换
                logo.toggleClass("active");//logo变换
                if (m.hasClass("active")) {
                    $(".navleft").animate({width: '+100px'}, "100").addClass("expand");
                    m.animate({left: '+85px'},"100");
                    $(".navright").animate({width: sw - 100},"100");
                } else {
                    $(".navleft").animate({width: '+300px'}, "100").removeClass("expand");
                    m.animate({left: '+285px'},"100");
                    $(".navright").animate({width: sw - 300},"100");
                }
            })


            nl.find("[href]:eq("+ (location.hash.substr(1)||0) +")").click();


        });
    
    //驳回弹出信息
    $(".reject").hover(function () {
        cl.layer(function (layer) {
            layer.tips('原因：'+$("#remarks").val(), '.reject', {
                tips: [1, '#eaedf2'],
                skin: 'reasonlay'
            });
        })

    })


    var resetpass_dura = 60 * 1000;

/************重置登录密码 start********************************/
    var resetpass = $(".resetpass").click(function () {
    	var useremail = $("[name=email]").text();
    	window.location.href = ctx + "/entaccount/toreset?email="+useremail;
//	        if (resetpass.is(".gray"))   return;
//	        var useremail=$("[name=email]").text();
//	        //调用发送邮件接口
//	        $.post(ctx+"/entaccount/resendPassEmail",{email:useremail})   
//            .fail(function () {
//                co.msg("原因：您的企业邮箱发送邮件失败，请重新点击发送邮件！");
//            })
//            .done(function (data) {
//                data = cj.tojson(data);
//                if (data.result == "success") {
//                	co.msg(data.msg);
//                    resetpass.addClass("gray");
//                    //成功后倒计时
//                    setTimeout(function () {
//                        resetpass.removeClass("gray");
//                    }, resetpass_dura + 10);
//                    //设置时间点
//                    var now = new Date();
//                    now.setTime(
//                        now.getTime() + resetpass_dura
//                    );
//
//                    //设置一个尺寸存在到某个事件点的cookie
//                    ck.set("resetpass_allready", 1, {
//                        expires: now
//                    });
//
//                } else {
//                	co.msg(data.msg);
//                     cl.layer(function (layer) {
//                        layer.tips('原因：您的企业邮箱发送邮件失败，请重新点击发送邮件！', resetpass, {
//                            tips: [2, '#eaedf2'],
//                            skin: 'reasonlay',
//                            time: 60
//                        });
//                    })
//                }
//            });
    })
    
 /************重置登录密码 end********************************/   
    
    //检查cookie，以确定按钮是否可以点击
    if (ck.get("resetpass_allready") == 1) {
        resetpass.addClass("gray");
    }
    var sw;
    cj.winResize(300, function (_sw, sh,wi) {
        var ac = arguments.callee;
        sw = _sw;

        nr.css({
            "min-height": sh,
            overflow: "hidden",
            width: sw - nl.width()
        });

        nl.css({
            height: sh,
        });

        basicright.css({
            width: sw - nl.width() - 221
        })
        $("#index").css({
            height: sh,
            width: sw - nl.width()
        })


        var fff = true;
        !function(){
            if(fff){
                fff = false;
                wi.resize();
            }
        }.delayCall(300);
    });

/************弹出企业认证表单 start********************************/
    $(".putin").click(function () {
        var href = this.getAttribute("_href");
        cl.layer(function (layer) {
            layer.open({
                type: 2,
                area: ['960px', '80%'],
                fix: false, //不固定
                maxmin: true,
                title: "企业用户信息认证",
                content: href
            });
        });
    });
  //弹出企业认证修改表单
    $(".againputin").click(function(){
        var href = this.getAttribute("_href");
        cl.layer(function(layer){
            layer.open({
                type: 2,
                area: ['90%', '90%'],
                fix: false, //不固定
                maxmin: true,
                title:"企业用户认证信息修改",
                content: href
            });
        });
    });
    //撤销
    $(".cancel").click(function(e) {
    	do_cancel(function() {
    		
    	});
    })
    function do_cancel(callback) {//cancel_url
    	$.get(window.cancel_url)
	        .fail(function () {
	            throw "撤销操作出错"
	        })
	        .done(function (data) {
	            data = cj.tojson(data);
	
	            if (data.result == "success") {
	            	location.reload();
	            	co.msg(data.msg);
                  (callback || $.noop)();
	            } else if(data.result == "error") {
	            	co.msg(data.msg);
	            }else if(data.result == "fail"){
	            	co.msg(data.msg);
	            }
	        })
	    ;
    }
/*********** 弹出企业认证表单  end********************************/
/***********企业邮箱与企业电话修改保存  start********************************/
    
    $(".revisemail,.revisephone").click(function (e) {
        var sava_btn = $(this);
        //修改
        var cont = sava_btn.parent("li");
        //cont.toggleClass("editing")
        //sava_btn.toggleClass("ed");
        if(!cont.is(".editing")) {
            cont.find("input").val(function () {
               return $(this).parents("li").find(".tx").text();
            })
            sava_btn.text("保存");
            cont.addClass("editing");
            sava_btn.addClass("ed");
        } else {
            //做保存
            var para = {};
            if(sava_btn.is(".revisemail")){
                para.mail = cont.find("input").val();
            }else if(sava_btn.is(".revisephone")){
            	// para.phone=$("[name=mobile]");
            	para.phone = $.trim(cont.find("input").val());
            	
            	if(!para.phone){
            		co.tips(cont.find("input"),'请输入电话号码',{dire:3});
            	    return  false;
            	}
            	
            	if(!/(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\(\d{3}\))|(\d{3}\-))?(1[358]\d{9})$)/.test(para.phone)){
            		co.tips(cont.find("input"),'电话号码格式不正确',{dire:3});
            		return  false;
            	}
            }

            
            comm.ajax(profile_modify_ajax || (ctx+"/entaccount/update") || (__ + "data/sava_profile.json"),para,function(tk){
            	if(tk.vl()){
            		cont.find(".tx").text(function () {
                        return cont.find("input").val();
                    })
                    sava_btn.text("修改");
            		cont.removeClass("editing");
                    sava_btn.removeClass("ed");
                    co.msg("电话号码修改成功")
            	}
            })
            
            
            
        }
    })



    return {};

});

/***********企业邮箱与企业电话修改保存  end********************************/