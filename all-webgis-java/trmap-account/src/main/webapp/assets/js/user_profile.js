/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2016/2/22
 * Time: 9:15
 * To change this template use File | Settings | File Templates.
 */

define(function (require, exports, module) {
    var cl = require("ctool");
    var u = require("_/tr_util");
    var co = require("__/js/comm");

    require("_/avalon/avalon.min");
    var userId=$("#userid").val();
    var webup = require("_/webuploader/webuploader.min");
   require("_/messenger.js")
    var messenger = new Messenger('user_profile', 'trmap');
    messenger.addTarget(top, 'main');




    var mail = ({
        init:function(){
            var m = this;
            var lindex;
            m.el = $(".ep_email");
            m.vm = avalon.define({
                "$id":"ep_email",
                v:"",
                v_edit:"",
                pop:function(e){
                	m.vm.v_edit="";
                    lindex = u.layer.open({
                        title:"修改email",
                        content:$(".ep_email .pop-el"),
                        area:["300px"],
                        type:1
                    });
                },
                pop_close:function(e){
                    //
                    if(!m.vm.v_edit){
                        u.tips(m.el.find(".pop-el .form-control"),"请输入新邮件地址");
                        return;
                    }

                    if(!/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/.test(m.vm.v_edit)){
                        u.tips(m.el.find(".pop-el .form-control"),"邮件地址非法");
                        return;
                    }
                    
                    u.ajax(
                        u.pagevar("yz_email", "__/data/success.json#".p()),
                        {
                            email: m.vm.v_edit
                        },
                        function (t, data) {
                            if (data.status == 1) {//0是假的成功
                                u.tips(m.el.find(".pop-el .form-control"), "该邮箱已存在！");
                                return;
                            } else {
                                u.ajax(
                                    u.pagevar("edit_email", "__/data/success.json#".p()),
                                    {
                                        id: userId,
                                        email: m.vm.v_edit
                                    },
                                    function (t) {
                                        if (t.vl()) {
                                            m.vm.v = m.vm.v_edit;
                                            m.vm.v_edit = "";
                                            u.layer.close(lindex);
                                            u.msg("邮件发送成功，请立刻到邮箱完成激活！")
                                        }
                                    }
                                );
                            }
                        }
                    );
                 
                   
                },
                anjian:function(e){ if (e.keyCode == 13) { m.vm.pop_close();  } }
            });
            return m;
        }
    }).init();


    var phone = ({
        init:function(){
            var m = this;
            m.el = $(".ep_phone");
            var lindex;
            m.vm = avalon.define({
                "$id":"ep_phone",
                title:"修改",
                v:"",
                v_edit:"",
                anjian:function(e){ if (e.keyCode == 13) { m.vm.pop_close() } },
                pop:function(e){
                    lindex = u.layer.open({
                        title:"修改电话号码",
                        content:m.el.find(".pop-el"),
                        area:["300px"],
                        type:1
                    });
                },
                pop_close:function(){
                    if(!m.vm.v_edit){
                        u.tips(m.el.find(".pop-el .form-control"),"请输入新的电话号");
                        return;
                    }

                    if(!(/^1[3|4|5|7|8]\d{9}$/.test(m.vm.v_edit))){
                        u.tips(m.el.find(".pop-el .form-control"),"请输入合法的号码");
                        return;
                    }


                    u.ajax(
                        u.pagevar("edit_phone","__/data/success.json#".p()),
                        {
                            id : userId,
                            phone: m.vm.v_edit
                        },
                        function(t){
                            if(t.vl()) {
                                m.vm.v = m.vm.v_edit;
                                m.vm.v_edit = "";
                                u.layer.close(lindex);
                                u.msg("电话号码修改成功！")
                            }
                        }
                    );

                }
            });
        }
    }).init();


    var pwd = ({
        init:function(){
            var m = this;
            m.el = $(".ep_pwd");

            m.pp = m.el.find(".pp");
            m.p1 = m.el.find(".p1");
            m.p2 = m.el.find(".p2");


            var lindex;

            m.vm = avalon.define({
                "$id":"ep_pwd",
                title:"修改",
                p1:"",
                p2:"",
                pp:"",
                pop:function(e){
                    lindex = u.layer.open({
                        type:1,
                        title:"修改密码",
                        content: m.el.find(".pop-el"),
                        area:["360px"]
                    });
                },
                anjian:function(e){ if (e.keyCode == 13) { m.vm.pop_close() } },
                pop_close:function(){
                    if(!m.vm.pp){
                        u.tips(m.pp,"请输入原密码");
                        return;
                    }

                    if(!m.vm.p1){
                        u.tips(m.p1,"请输入新密码");
                        return;
                    }

                    if(!/^.{6,20}$/.test(m.vm.p1)){
                        u.tips(m.p1,"密码长度要求为6-20位字符");
                        return;
                    }

                    if(!m.vm.p2){
                        u.tips(m.p2,"请再次输入新密码");
                        return;
                    }

                    if(m.vm.p1 != m.vm.p2){
                        u.tips(m.p2,"两次输入的密码不一致");
                        return;
                    }





                    u.ajax(
                        u.pagevar("edit_pwd","__/data/success.json#".p()),
                        {
                            id : userId,
                        	oldpassword:   m.vm.pp,
                        	newpassword :  m.vm.p1
                        },
                        function(t,data){
                            if(t.vl()) {
                                m.vm.pp = "";
                                m.vm.p1 = "";
                                m.vm.p2 = "";
                                u.layer.close(lindex);
                                u.msg("密码修改成功!");

                            }else{
                            	m.vm.pp = "";
                                m.vm.p1 = "";
                                m.vm.p2 = "";
                                u.layer.close(lindex);
                            	u.msg(data.msg);
                            }
                        }
                    );

                }
            });



            var other_form = ({
                init:function(){
                    var m = this;
                    m.el =  $(".other_form");
                    m.vm = avalon.define({
                        $id:"other_form",
                        d:{
                            id : userId,
                            ep_name    :   u.v4n("ep_name",  m.el),
                            user_sex   :   m.el.name("user_sex").attr("valu")
                        },
                        submit:function(e){
                            e.preventDefault();

                           /* if(!m.vm.d.user_name) {
                                u.tips(m.el.name("user_name"),"用户名称不能为空");
                                return;
                            }

                            if(!/^.{6,20}$/.test(m.vm.d.ep_name)){
                                u.tips(m.el.name("user_name"),"用户名称长度要求位6-20字符");
                                return;
                            }*/

                            if(!m.vm.d.ep_name) {
                                u.tips(m.el.name("ep_name"),"名称不能为空");
                                return;
                            }

                            if(!/^.{6,20}$/.test(m.vm.d.ep_name)){
                                u.tips(m.el.name("ep_name"),"昵称长度要求为6-20位字符");
                                return;
                            }

                            u.ajax(
                                u.pagevar("edit_other_prop","__/data/success.json#".p()),
                                m.vm.d.$model,
                                function(t){
                                    if(t.vl()) {
                                        u.msg("修改成功");
                                    }
                                }
                            );
                        },
                        reset:function(e){
                            e.preventDefault();
                            m.vm.d = u.clone(def);
                        }
                    });

                    var def = u.clone(m.vm.d.$model);

                    return m;
                }
            }).init();

            return m;
        }
    }).init();


    $(function(){

        var filemgr ={
            init:function(){

                var up = webup({
                    server: u.pagevar("edit_thumb","__/data/success.json#".p()),
                    auto:true,
                    pick:{
                        "id":".thumbnail"
                    },
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
                    },
                    duplicate:true
                });

                up.addButton({
                    "id":".changeimg"
                })



                var picker = $(".header-img>.webuploader-pick");
                up.fresh_button_size = function(){

                    var upload_div = picker.next();

                    if(upload_div.length){
                        doset();
                    }else{
                        cl.run_until(500,function(){
                            upload_div = picker.next()
                            if(upload_div.length){
                                doset();
                                return true;
                            }
                        })
                    }

                    function doset(){
                        upload_div.css({
                            bottom: 4,right:4,
                            width:"auto",
                            height:"auto"
                        });
                    }

                }

                up.fresh_button_size();



                var hd_cont = $(".header-img");
                hd_cont.img = hd_cont.find("img");
                hd_cont.img.src = hd_cont.img.attr("src");

                var lb = $(".header_img_label>.lb");
                lb.tx = lb.text();



                up.on("uploadStart",function(file){
                    lb.text("上传中...");
                    up.makeThumb( file, function( error, ret ) {
                        if ( !error ) {
                            //hd_cont.img.attr("src",ret);
                        }
                    });
                });

                up.on("uploadSuccess",function(file,data){
                    lb.text(lb.tx);
                    var url = data._raw;
                    //将上传图片返回的编号保存到用户对应的 头像里面

                    hd_cont.img.attr("src",co.media_url + url);

                    u.ajax(
                        u.pagevar("edit_url","__/data/success.json#".p()),
                        {
                            id : userId,
                            url: url
                        },
                        function(t){
                            if(t.vl()) {
                            	hd_cont.img.src=headimgvm.img;
                                u.msg("修改成功");
                                messenger.targets['main'].send("user_profile_change");
                            }else{
                                u.msg("上传头像失败！");
                            }
                        }
                    );



                })

                up.on("uploadError",function(type){
                    lb.text(lb.tx);
                    u.msg("上传失败");
                    hd_cont.img.attr("src",hd_cont.img.src);
                })

                var headimgvm;
                headimgvm = avalon.define({
                    $id:"headimg",
                    list:[],
                    img:headimg?(co.media_url + headimg):(co.media_url + "hd.png"),
                    ipth:co.media_url,
                    clickimg:function(el){
                        u.ajax(
                            u.pagevar("edit_url","__/data/success.json#".p()),
                            {
                                id:userId,
                                url: el
                            },
                            function(t){
                                if(t.vl()){
                                    headimgvm.img = headimgvm.ipth+el;
                                    hd_cont.img.src=headimgvm.img;
                                    messenger.targets['main'].send("user_profile_change");
                                    u.msg("修改成功");
                                }
                        }
                        )
                    }
                })
                $.ajax({
            		url:u.pagevar("imglist","../data/imglist.json"),
                    jsonp:"callback",
                    dataType:'jsonp',
                    success:function(t,data){
                    	headimgvm.list= t.data;
                    }
                })

                avalon.scan();
            }
        }
        filemgr.init();



        !function(){
        	$("html").addClass("no_cl_loading");
        }.delayCall(120)
    });



    return {};
});