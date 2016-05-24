/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2015/11/27
 * Time: 9:31
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {
	var cl = require("ctool");
    var cj = require("ctooj");
    require("_/webuploader/webuploader.min");
    require("_/webuploader/webuploader.css");
    require("_/validform");
    var comm = require("__/js/comm");

    $(function(){

        var uploader_comm = {
            swf: require.resolve("_/webuploader/Uploader.swf#"),
            auto:true,
            pick:{ html: "上传"}
        };

        /*var uploader_conf = [
            //营业执照
            {server:"http://2betop.net/fileupload.php"},

            //组织机构
            {server:"http://2betop.net/fileupload.php"},

            //法人身份证
            {server:"http://2betop.net/fileupload.php"},

            //负责人身份证
            {server:"http://2betop.net/fileupload.php"}
        ];*/

        $.each(uploader_conf,function(key,ele){

            var btn = $(".uploader:eq("+key+")");
            var imgcont = $("<p><span class='preview'><span class='tip'>上传中...</span></span></div></p>");
            imgcont.preview = imgcont.find(".preview");

            //营业执照
            var uploader = new WebUploader.Uploader(
                $.extend(true,uploader_comm,{
                    server: ele.server,
                    pick:{
                        "id":btn
                    }
                })
            );

            uploader.on("fileQueued",function(file){
                //上传中
                if(!imgcont.parent().length){
                    btn.parents("p:first").after(imgcont);
                }


                uploader.makeThumb(file,function(err,src){
                    if(err){
                        alert("生成缩略图出错");
                        return;
                    }
                    imgcont.find("img").remove();
                    imgcont.preview.append("<img src="+ src +">");
                },600,600);
            });

            uploader.on("uploadSuccess",function(file,reponse){
                //上传成功
                imgcont.preview.addClass("suc");

                //response 就是上传完成后，服务器响应的信息。你把图片地址传回来
            });
        });

    	$(".submit").click(function(){
			
    		var u1 = $("#u1").val(); 
    		var u2 = $("#u2").val(); 
    		var u3 = $("#u3").val(); 
    		var u4 = $("#u4").val(); 
    		if(!vlform.check()) {
    			return;
    		} 
    		if(u1 == "") {
    			alert("营业执照附件不能为空！");
    			return;
    		}
    		if(u2 == "") {
    			alert("组织机构附件不能为空！");
    			return;
    		}
    		if(u3 == "") {
    			alert("法人身份证附件不能为空！");
    			return;
    		}
    		if(u4 == "") {
    			alert("管理员身份证附件不能为空！");
    			return;
    		}
    		if(!($("#accept").is(':checked'))) {
    			alert("您还未同意服务协议！");
    			return;
    		}
    		if(vlform.forms.find(".errtip.err").length) return'';
    		
    		
    		vlform.ajaxPost()
    	})

        ///表单验证
        var vlform = $(".auth_info_table").Validform({
        	
        	ajaxPost:true,
            callback:function(data){
	           	 data = cj.tojson(data);
	           	 if(data.result == "success"){
	           		 parent.location.reload();
	           		 alert(data.msg);
	           	 }else{
	           		 alert(data.msg);
	           	 }
            },
            
            tiptype:function(msg,o,cssctl){
                var m = o.obj;
                var p = m.parents("p:first");

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
                            url:ctx + "/entcainfo/validation",
                            data:{
                                type: m.attr("name"),
                                value: m.val()
                            },
                            type:"post",
                            dataType:"jsonp"
                        })
                            .fail(function(data){
                                fail_action("重复性检测失败");
                            })
                            .done(function(data){
                                if($.trim(data) == "true"){
                                    success_action();
                                }else{
                                    fail_action(m.attr("ajax_valid_errmsg"));
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
                }

                function success_action(){
                    //el.html("验证通过").addClass("suc");
                    m.parent().removeClass("error");
                }

            },
            showAllError:true,
            datatype:{
                "zh1-6":/^[\\u4E00-\\u9FA5\\uf900-\\ufa2d]{1,6}$/
            },
            ajaxPost:false
        });


        vlform.addRule([
            {
                ele:"input:eq(0)",
                datatype:"s2-25",
                nullmsg:"请填写企业名称",
                errormsg:"企业名称不可包含符号且长度应控制到2-25之间"
            },
            {
                ele:"input:eq(1)",
                datatype:"*1-255",
                nullmsg:"请填写企业地址",
                errormsg:"企业地址长度应控制到1-255之间"
            },
            {
                ele:"input:eq(2)",
                datatype:"*1-255",
                nullmsg:"请填写企业简介",
                errormsg:"企业简介长度应控制到1-255之间"
            },
            {
                ele:"input:eq(3)",
                datatype:"n15-15",
                nullmsg:"请输入营业执照注册号",
                errormsg:"请输入有效的营业执照注册号"
            },

//            {
//                ele:"input:eq(3)",
//                datatype:"n5-9",
//                nullmsg:"请输入组织机构代码",
//                errormsg:"请输入有效的组织机构代码"
//            },

            {
                ele:"input:eq(5)",
                datatype:"*2-10",
                nullmsg:"请输入法人姓名",
                errormsg:"姓名长度应控制在2-10之间"
            },

            {
                ele:"input:eq(6)",
                datatype:"/(^\\d{15}$)|(^\\d{17}([0-9]|X)$)/",
                nullmsg:"请输入身份证号",
                errormsg:"请输入有效身份证号"

            },

            {
                ele:"input:eq(8)",
                datatype:"/^(\\d{4})-(0\\d{1}|1[0-2])-(0\\d{1}|[1-2]\\d{1}|3[0-1])$/",
                nullmsg:"请输入身份有效期",
                errormsg:"请输入有效的日期格式"
            },


            {
                ele:"input:eq(9)",
                datatype:"*2-10",
                nullmsg:"请输入运维负责人姓名",
                errormsg:"姓名长度应控制在2-10之间"
            },

            {
                ele:"input:eq(10)",
                datatype:"/(^\\d{15}$)|(^\\d{17}([0-9]|X)$)/",
                nullmsg:"请输入身份证号",
                errormsg:"请输入有效身份证号"
            },

            {
                ele:"input:eq(12)",
                datatype:"/^(\\d{4})-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])$/",
                nullmsg:"请输入身份有效期",
                errormsg:"请输入有效的日期格式"
            },


            {
                ele:"input:eq(13)",
                datatype:"m|/^\\\\d{2,3}-?\\\\d{7,8}$/",
                nullmsg:"请输入电话号码",
                errormsg:"请输入合法的手机或者座机号"
            }

        ]);


    });


    return {};
});