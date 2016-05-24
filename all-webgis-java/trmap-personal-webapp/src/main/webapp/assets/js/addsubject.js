/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2016/2/25
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {
    require("_/avalon");
    var co = require("__/js/comm");
    var u = require("_/tr_util");
    var ht = $("html");
    require("_/validform");
    var userId=$("#userid").val();
    var webup = require("_/webuploader/webuploader.min");

    require.async("__/js/theme_list_left");
    var para = u.cl.urlParas();


    ht.addClass("no_cl_loading");

    var vm;
    var vm1;
    var vm2;
    $(function(){
        //专题名称
        vm2 = avalon.define({
            $id:"subname",
            themename:[],
            valuename:"",
            onname:function(el){
                vm2.valuename = el;
                $("#subjectname").focus();
            },
            nameinput:function(el){
                $("td .prompt").show();
            }
        })


        $("#subjectname").prev().blur(function(){

            !function(){
                $("td .prompt").hide();
            }.delayCall(500);

            !function(){
                $("#subjectname").blur();
            }.delayCall(100);



        });


        u.ajax(u.pagevar("name_subject",'__/data/name_subject.json'.p()),function(t,data){
            if(t.vl()){
                vm2.themename = data.themename;
            }
        })

        //创建的专题是否公开
        vm1 = avalon.define({
            $id:"isopen",
            isopen:para.open * 1,
            toggle: function () {
                if(vm1.isopen != 1){
                    vm1.isopen = 1;
                }else{
                    vm1.isopen = 0;
                }
            },
            /*好友人数*/
            total:"",
            /*指定好友人数*/
            zdtotal:"",
            jurisdict:-1,
            list:[],
            jurisdict_id_str:"",
            /*头像默认图片*/
            default_headimg:co.theme_thumb_headimg,
            //图片文件路径前缀
            ibpath:co.media_image_path,
            zdfriend:function(){
                var capture = $.extend(true,[],vm1.list.$model);
                layer.open({
                    type: 1,
                    area: ['496px', '467px'], //宽高
                    title:"指定好友",
                    content:$(".zdfriend"),
                    move: false,
                    btn: ['确定', '取消'],
                    yes: function(index, layero){
                        var str = "";
                        var len = 0;
                        $.each(vm1.list.$model, function () {
                            $.each(this.user,function(k,el){
                                if(el.checked){
                                    str += "|"+el.userId;
                                    len = len + 1;
                                }
                            });
                        });
                        vm1.zdtotal =len;
                        u.layer.close(index);

                        if(str){
                            str = str.substr(1);
                        }

                        vm1.jurisdict_id_str = str;

                    },
                    cancel:function(){
                        vm1.list = capture;
                    }
                });
            },
            checked:true,
            /*点击好友分组*/
            hove:function($na,e){
                if(e.target.tagName == "I"){
                    return;
                }

                $na.shrink = !$na.shrink;

            },
            toggle_user_status:function($user){
                $user.checked = !$user.checked;
            },
            /*添加整个组成员*/
            addgroup:function(el){
                $.each(el.user,function(k,el){
                    el.checked = true;
                })
            }
        })


        u.ajax(
            u.pagevar("friendlist","../data/group_list1.json"),
            function(t,data){
                if(t.vl()){
                    vm1.list = data.data;
                    vm1.total = data.totalCount;
                    var len = 0;
                    $.each(vm1.list.$model, function () {
                        $.each(this.user,function(k,el){
                            if(el.checked){
                                len = len + 1;
                            }
                        });
                    });
                    vm1.zdtotal =len;

                }
            }
        )


        //选择专题标签颜色
        vm = avalon.define({
            $id:"s_color",
            color_list:[],
            current_color_index:0,
            $computed:{
                topiccolor:{
                    get:function(){
                        var cl = this.color_list[this.current_color_index];
                        return cl?cl.color:"";
                    }
                },
            },

            click_co:function(el){
                vm.current_color_index = el.index;
            }
        });

        u.ajax(
            u.pagevar("theme_color","../data/co_subject.json"),{},function(t,data){
                if(t.vl()){
                    $.each(data.rows,function(k,el){
                        el.index = k;
                    })
                    vm.color_list = data.rows;
                }
            }
        )
        avalon.scan();

        var imgright =$(".imgupload")
        //图片上传
        var filemgr = {
            init:function(){
                var m = this;
                var up = webup({
                    server: u.pagevar("coverurl","../data/success.json"),
                    auto:true,
                    pick:{
                        "id":".imgupload>div>h3",
                        "multiple":false
                    },
                    accept:{
                        extensions: 'jpg,jpeg,bmp,png'
                    },
                    duplicate:true,
                    compress:{
                        width: 450,
                        height: 450,

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
                        compressSize: 0
                    }
                });
                var hd_cont = $(".header-img");
                hd_cont.img = hd_cont.find("img");
                hd_cont.img.src = hd_cont.img.attr("src");

                var layerjs_loading_ingdex;
                up.on("uploadStart",function(file){
                    layerjs_loading_ingdex =  u.layer.load(1, { shade: [0.36, '#fff'] /*0.1透明度的白色背景*/});
                });



                up.on("uploadSuccess",function(file,data){
                    u.msg("上传成功");
                    imgright.find("img").removeClass("on");

                    $("#coverurl").val(data.data);
                    u.layer.close(layerjs_loading_ingdex);

                    //m.vm.img = co.getThumb(data.id,256);
                    m.vm.topicimg = data.id;

                    up.makeThumb( file, function( error, ret ) {
                        if ( !error ) {
                            hd_cont.img.attr("src",ret);
                        }
                    },256,256);

                })
                up.on("uploadError",function(type){
                    u.msg("上传失败");
                    hd_cont.img.attr("src",hd_cont.img.src);
                    u.layer.close(layerjs_loading_ingdex);

                })

                m.vm=avalon.define({
                    $id:"imgupload",
                    imglist:[],
                    topicimg:"",
                    img:ctx+"/assets/images/uploader.png",
                    thump:co.media_image_path,
                    onimg:function(el){
                        var n =$(this);
                        n.parent("li").siblings().find("img").removeClass("on");
                        n.addClass("on");
                        hd_cont.img.src = m.vm.thump+el;
                        m.vm.img = hd_cont.img.src;
                        m.vm.topicimg =el;
                        $("#coverurl").val(el);
                    }
                })
                $.ajax({
            		url:u.pagevar("imglist","../data/imglist.json"),
                    jsonp:"callback",
                    dataType:'jsonp',
                    success:function(t,data){
						m.vm.imglist= t.data;
                    }
                })

            }
        };

        filemgr.init();


        function goback(){
            var para = u.cl.urlParas();
            if(para.from){
                location.href = decodeURIComponent(para.from);
                //location.reload();
            }else{
                parent.location.href=ctx+"/topic/totopic";
            }
        }

        window.goback = goback;



        $("a:has(.sub_cancel)").attr("href","javascript:goback();");

        //添加专题表单验证
        $("tr.bth .sub_save").click(function(){
            vlform.ajaxPost(false,false);
        })

        var vlform = $(".addsubform").Validform({
            dragonfly:false,
            callback:function(data){
                //响应
            	if(data.status==0) {
            		u.msg("专题添加成功！");
            		  !function(){
                          goback();
                      }.delayCall(100);
                }else{
                	u.msg("专题添加失败！");
               }
            },
            //tipSweep:true,
            tiptype:1,
            beforeCheck:function(){
                /*console.log(88)*/
            },
            tiptype:function(msg,o,cssctl){
                var m =o.obj;
                //寻找或创建错误提示元素
                var p = m.parents("td:first");
                var el = p.find(".errtip");
                if(!el.length){
                    p.append("<span class='errtip'></span>")
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
                function success_action(data){
                	if(data.status==0) {
                		u.msg("专题添加成功！");
                    }else{
                    	u.msg("专题添加失败！");
                   }
              }
            }
        });
        vlform.addRule([
            {
                ele:"#subjectname",
                datatype:"*1-10",
                nullmsg:"请输入专题名称！",
                errormsg:"名称至少1个字符,最多10个字符！"
            },
            {
                ele:"#describe",
                datatype:"*1-45",
                nullmsg:"请输入专题描述！",
                errormsg:"专题描述至少1个字符,最多45个字符！"
            }
        ]);


        avalon.scan();
    })

    return {};
});