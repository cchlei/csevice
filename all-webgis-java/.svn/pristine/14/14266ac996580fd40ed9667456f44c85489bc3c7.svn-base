/**
 *
 * 地图发布--元数据编辑
 *
 */
define(function (require, exports, module) {
    var cl = require("ctool");
    var cj = require("ctooj");
    require("_/webuploader/webuploader.min");
    require("_/webuploader/webuploader.css");
    require("_/validform");
    //表单美化
    require("_/jqtransform/jquery.jqtransform");
    require("_/jqtransform/jqtransform.css");

    //地区distpicker
    require("_/area/distpicker");
    
    var comm = require("__/js/comm");


    $(function() {


        $("#distpicker").distpicker(
            window.default_area || {
                province: "陕西省",
                city: "西安市",
                placeholder:false
            }
        );
    });

    var layer = require("$/layer");

    
    var is_image_upading = false;
    

    $(function () {
        /*var uploader_conf = [
         //图层图片
         {server:"http://2betop.net/fileupload.php"}
         ];*/

        $.each(uploader_conf, function (key, ele) {

            var btn = $(".uploader:eq(" + key + ")");
            var imgcont = $("<p><span class='preview'><span class='tip'>上传中...</span></span></div></p>");
            imgcont.preview = imgcont.find(".preview");

            //图层图片
            var uploader = new WebUploader.Uploader({
                swf: require.resolve("_/webuploader/Uploader.swf#"),
                auto: true,
                pick: {html: "上传"},
                duplicate: true,
                server: ele.server,
                pick: {
                    "id": btn,
                    multiple:false
                }
            });

            uploader.on("fileQueued", function (file) {
            	is_image_upading = true;
                $(".tctp").val(file.name);

                var next = btn.parents("p:first").next();
                if(next.has(".preview")){
                	imgcont = next;
                    imgcont.preview = imgcont.find(".preview");

                }
                
                //上传中
                if (!imgcont.parent().length) {
                    btn.parents("p:first").after(imgcont);
                }


                uploader.makeThumb(file, function (err, src) {
                    if (err) {
                    	comm.msg("生成缩略图出错");
                        return;
                    }
                    imgcont.find("img").remove();
                    imgcont.preview.append("<img id=imgumicon src=" + src + ">");
                    imgcont.preview.find(".tip").show();
//                    $("#imgumicon").attr("src",src);
                }, 600, 600);
            });
            uploader.on("uploadSuccess",function(file, response){
                //上传成功
                imgcont.preview.find(".tip").hide();
                file.ossid = response._raw;
                $('#imgumicon').attr('src','http://oss.trmap.cn/rs/download/'+response._raw);
            });
            // 完成上传完了，成功或者失败，先删除进度条。
            uploader.on( 'uploadComplete', function( file ) {
                $( '#'+file.id ).find('.progress').remove();
                //提交后台插入记录
            	var files = uploader.getFiles("complete");
            	ids = files[0].ossid;
            	$("#umicon").val(ids);
                is_image_upading = false;
            });
        });

        var agreement_layer_index = -1;
        
        //点击发布弹出同意框
        $(".maprelease").click(function () {

        	if(is_image_upading){
        		comm.msg("图片正在上传中，请等待");
        		return;
        	}
        	
            //判断上传图片是否存在
            if (!$("#imgumicon").attr("src")) {
            	comm.msg("请先上传图片");
                return;
            }
            if (vlform.check())
                agreement_layer_index = layer.open({
                    type: 1,
                    area: 'auto',
                    maxWidth: 855,
                    title: false,
                    shadeClose: true,
                    skin: 'relasebg',
                    closeBtn: 2,
                    content: $('.releaselayer')
                });
        })

        //点击同意发布协议
        $(".agreement").click(function () {
            layer.close(agreement_layer_index);
            save_mode = "publish";
            vlform.ajaxPost(false,false,vlform.forms.attr("action_release"));
        })
        
        $(".calcenagree").click(function(){
        	layer.close(agreement_layer_index)
        })


        var save_mode = "";

        //点击保存
        $(".mapsave").click(function () {
        	
        	if(is_image_upading){
        		comm.msg("图片正在上传中，请等待");
        		return;
        	}
            save_mode = "save";
            $("#savemode").val("save");
            if (!$("#imgumicon").attr("src")) {
            	comm.msg("请先上传图片");
                return;
            }
            //todo:如果传入参数true，表示绕过验证，直接提交
            vlform.ajaxPost(false,false,vlform.forms.attr("action_save"));
        });


        //点击更新弹出
        $(".mapup").click(function () {
        	if(is_image_upading){
        		comm.msg("图片正在上传中，请等待");
        		return;
        	}
            save_mode = "update";
            //判断上传图片是否存在
            //if(!$(".mapupdata .preview.suc").length){
            if (!$("#imgumicon").attr("src")) {
            	comm.msg("请先上传图片");
                return;
            }
            //更新成功弹出框
            if (vlform.check()) {
                $("#savemode").val("update");
                //vlform.ajaxPost();
                vlform.ajaxPost(false,false,vlform.forms.attr("action_release"));
            }
        })
        
       
        //表单验证
        var vlform = $(".mapupdata").Validform({
            ajaxPost: true,
            callback: function (data) {
                data = cj.tojson(data);




                //发布与更新成功失败共四中情况
                if (save_mode == "save") {
                    if (data.result == "success") {
                    	comm.msg("保存成功");
                    } else {
                    	comm.msg("保存失败"+data.msg);
                    }
                } else if (save_mode == "publish") {
                	if (data.result == "success") {
                        comm.msg("发布成功");
                		location.reload();
                    } else {
                        comm.msg("发布失败"+data.msg);
                    }
                } else if (save_mode == "update") {
                    if (data.result == "success") {
                        var index = layer.open({
                            type: 1,
                            area: 'auto',
                            maxWidth: 480,
                            title: false,
                            closeBtn: 1,
                            shadeClose: true,
                            skin: 'updatebg',
                            content: $('.layersuccess')
                        });




                        var count = 5;
                        $(".minclose").html(count);
                        cl.run_until(1000,function(a){
                            $(".minclose").html(count);
                            if(!count--){
                                location.href = ctx+"/entmap/tomaplist";
                                return true;
                            }
                        });


                    } else {
                        layer.open({
                            type: 1,
                            area: 'auto',
                            maxWidth: 480,
                            title: false,
                            closeBtn: 1,
                            shadeClose: true,
                            skin: 'updatebg',
                            content: $('.layerfail')
                        });
                    }
                }

            },

            usePlugin: {
                jqtransform: {
                    selector: ":checkbox"
                }
            }
            ,
            tiptype: function (msg, o, cssctl) {
                var m = o.obj;
                var p = m.parents("p:first");

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

                    //进行ajax验证
                    if (o.obj.is("[ajax_valid_errmsg]")) {
                        $.ajax({
                            url: "http://user.trmap.cn/account/validation",
                            data: {
                                type: m.attr("name"),
                                value: m.val()
                            },
                            type: "post",
                            dataType: "jsonp"
                        })
                            .fail(function () {
                                fail_action("重复性检测失败");
                            })
                            .done(function (data) {
                                if ($.trim(data) == "true") {
                                    success_action();
                                } else {
                                    fail_action(m.attr("ajax_valid_errmsg"));
                                }
                            })
                        ;
                    } else {
                        success_action();
                    }
                }


                function fail_action(_msg) {
                    el.html(_msg || msg).addClass("err");
                    m.parent().addClass("error");
                }

                function success_action() {
                    // el.html("&#xe601;").addClass("suc");
                    // m.parent().removeClass("error");
                }

            }
            ,
            showAllError: true,
            datatype: {
                "zh1-6": /^[\u4E00-\u9FA5\uf900-\ufa2d]{1,6}$/
            }
            ,
            ajaxPost: false
        });


        vlform.addRule([
            {
                ele: "[name=mapname]",
                datatype: "s2-11",
                nullmsg: "请填写图层名称",
                errormsg: "图层名字过长"
            },
            {
                //ele:"input:eq(1)",
                datatype: "<img[^>]*>",
                nullmsg: "请上传图层图片",
                errormsg: "请输入有效的图层图片"
            },

            {
                ele: "textarea:eq(0)",
                datatype: "*1-200",
                nullmsg: "请输入服务描述",
                errormsg: "描述不超过200个字符"
            },

            {
                ele: ":radio:first",
                datatype: "*",
                nullmsg: "请至少选择一个接口类型"
            },


            {
                ele: "[name=tags]:first",
                datatype: "*",
                nullmsg: "请选择标签类型",
                errormsg: "请选择标签类型"
            },

            /*{
                ele: "[name=area]",
                datatype: "*",
                nullmsg: "请选择行政区划级别",
                errormsg: "请正确选择"
            },*/
            {
                ele:"[name=validityregion]",
                datatype:"*",
                nullmsg: "请选择有效期",
                errormsg: "请正确选择"
            }
        ]);

    })

//倒计时
    var timeout = 5;

    function show() {
        var minclose = $(".minclose");
        minclose.html(timeout);
        timeout--;
        if (timeout == 0) {
            window.opener = null;
            window.location.href = "index.html";
        }
        else {
            setTimeout("show()", 5000);
        }
    }


    return {};
})
;