/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2016/2/25
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {
    require("_/avalon.min");
    var co = require("__/js/comm");
    var u = require("_/tr_util");
    var ht = $("html");
    require("_/validform");
    var userId=$("#userid").val();
    var webup = require("_/webuploader/webuploader.min");

    /*日历*/
    require("$/laydate");

    var ex={};

    var para = u.cl.urlParas();
    var isopen;


    $(function(){
        //创建的专题是否公开
        isopen = avalon.define({
            $id:"isopen",
            isopen:para.open * 1,
            toggle: function () {
                if(isopen.isopen ==1){
                    isopen.isopen =0
                }else{
                	isopen.isopen = 1
                }
            }
        })


        //添加专题表单验证
        $("tr.bth .sub_save").click(function(){
            vlform.ajaxPost(false,false);
        });

        var vlform = $(".record_add").Validform({
            dragonfly:true,
            callback:function(data){
                //响应
            	if(data.status==0) {
            		u.msg(data.msg);
            		//u.msg("记录添加成功！");
                    !function(){
                        history.back()
                    }.delayCall(1000);
                }else{
                	u.msg("记录添加失败！");
               }
            },
            tiptype:1,
            tiptype:function(msg,o,cssctl){
                var m =o.obj;
                //寻找或创建错误提示元素
                var p = m.parents("td:first");
                var el = p.find(".errtip");
                if(!el.length){
                    p.append("<div class='errtip'></div>")
                    el = p.find(".errtip")
                }
                el.html("");

                if(o.type == 3){
                    fail_action();
                }
                function fail_action(_msg) {
                    el.html(_msg || msg).addClass("err");
                    m.parent().addClass("error");
                }
            }
        });


        vlform.addRule([
            {
                ele:"input[name='title']",
                datatype:"*1-10",
                nullmsg:"请输入标题！",
                errormsg:"标题至少1个字符,最多10个字符！"
            },
            {
                ele:"textarea[name='description']",
                datatype:"*1-5000",
                nullmsg:"请输入描述！",
                errormsg:"描述至少1个字符,最多5000个字符！"
            },
            {
                ele:"#addrname",
                datatype:"*0-64",
                //nullmsg:"请标示地点！",
                errormsg:"最多64个字符！"
            }
        ]);


        /**
         * 附件管理
         * @type {{init: Function}}
         */
        var filemgr = {
            init:function(){
                var m = this;
                m.el = $(".uploadthing");
                
                var attachstr="";

                var up = webup({
                    server: u.pagevar("coverurl","../data/upload_success.json"),
                    auto:true,
                    //duplicate:true,
                    pick:{
                        "id":".uploadclick"
                    },
                    /*compress:{
                        width: 800,
                        height: 650,

                        // 图片质量，只有type为`image/jpeg`的时候才有效。
                        quality: 90,

                        // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
                        allowMagnify: false,

                        // 是否允许裁剪。
                        crop: true,

                        // 是否保留头部meta信息。
                        preserveHeaders: true,

                        // 如果发现压缩后文件大小比原来还大，则使用原来图片
                        // 此属性可能会影响图片自动纠正功能
                        noCompressIfLarger: true,

                        // 单位字节，如果图片大小小于此值，不会采用压缩。
                        compressSize: 1024*1024
                    },*/
                    threads:1
                });

                var hd_cont=$(".uploadthing");
                var layerjs_loading_ingdex;
                up.on("uploadStart",function(file){
                    layerjs_loading_ingdex =  u.layer.load(1, { shade: [0.36, '#fff']});
                });
                
                up.on("beforeFileQueued",function(file,data){
                	if(file.size <= 0){
                		u.msg("空文件不能上传！");
                	}
                });
                up.on("filesQueued",onQueued);
                up.on("fileQueued",onQueued);
                
                var str = "";
                function onQueued(f){
                	if(!f.push){
                		f = [f]
                	}
                	str = "";
                	$.each(f,function(k,file){
                		if(file.size>=1024 * 1024){
                			str += file.name+ ":文件过大，单个文件最大1M，小于1M的文件将被上传"
                			up.removeFile(file);
                		}
                	})
                }
                
                up.on("uploadSuccess",function(file,data){
                    data = u.cj.tojson(data);
                    u.msg(file.name+"上传成功");
                    file.name = data.data.name;
                    file.ext = data.data.ext;
                    file.id = data.data.id || data.coveurl;
                    //拼接附件ossid
                    attachstr += file.id+","
                    $("#coverurl").val(attachstr);
                    file.thumb = "";
                    //如果是图片使用生成的缩略图
                    up.makeThumb( file, function( error, ret ) {
                        if ( !error ) {
                            file.thumb = ret;
                            m.appendFile(file);
                        }else{
                            file.thumb = "../images/iconfont-noknow.png";
                            m.appendFile(file);
                            file.thumb =  co.filetype_thumb[file.type]  || co.filetype_thumb.default;
                        }
                    },256,256);

                })

                up.on("uploadFinished",function(err){
                	if(layerjs_loading_ingdex){
                		u.layer.close(layerjs_loading_ingdex);
                	}
                    if(str)
                		u.msg(str);
                });
                
                up.on("uploadError",function(err){
                    u.msg("上传失败");
                    u.layer.close(layerjs_loading_ingdex);
                });

                m.up = up;


                m.vm = avalon.define({
                    $id:"file_man",
                    list:[],
                    ibpath:co.media_image_path,
                    del:function(el){
                        u.ajax(
                            u.pagevar("filedelete","__/data/success.json".p()),
                            {
                                fileid:el.id
                            },
                            function(t){
                                if(t.vl()){
                                    m.vm.list.remove(el);

                                    if(el._events){
                                        up.removeFile(el,true);
                                    }
                                }
                            }
                        )
                    }
                });


            },
            appendFile:function(file){
                var m = this;
                m.vm.list.push(file);
            }
        };

        filemgr.init();

        avalon.scan();
        ht.addClass("no_cl_loading");
    })


    var rec_common = require("./record_comm");

    ex.mapel = rec_common.mapel;

    return ex;
});
