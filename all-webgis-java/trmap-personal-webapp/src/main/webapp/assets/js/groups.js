/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2016/2/25
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {
    var co = require("__/js/comm");
    var u = require("_/tr_util");
    var ht = $("html");

    var grou;
    var fans;
    var text_right =$(".text_right")
    $(function(){

        var reg=/^\S.{0,5}$/;
        //左侧列表
        grou = avalon.define({
            $id:"grou",
            cureent_gp_index:0,
            group_list:[],
            member_click:function(el,index){
                grou.cureent_gp_index = index;
                if(index===0){
                    text_right.hide();
                }else if(index!=0){
                    text_right.show();
                }
                fans.view_mode= 0;
                nidexNum = index;
                fans.gp = el;
                fans.pagename = el.name;
                fans.pageid = el.gid;
                if(pa) {
                    pa.type = "groups";
                }
                fans_sr.doo();

            },

            $computed:{
                current_gp:{
                    get:function(){
                        return this.group_list[this.cureent_gp_index];
                    }
                }
            }
        });

        //右侧粉丝
        var groupgidval= $(".text_left span");
        fans = avalon.define({
            $id:'fans',
            list:[],
            total:'',
            gp:{},
            pagename:"",
            pageid:"",
            group_mumbers:[],
            //选中的粉丝的id
            userId:'',
            //选中的粉丝对象
            fansel:{},
            //选中更改组的id
            groupgid:"",
            //图片文件路径前缀
            ibpath:co.media_image_path,

            reqPara:{
              type:"group"
            },

            $computed:{
                current_gp:{
                    get:function(){
                        return grou.current_gp
                    }
                }
            },



            /**
             * fensi两种视图模式的管理
             */
            view_mode:0,
            /*头像默认图片*/
            default_headimg:co.theme_thumb_headimg,
            theumimg:co.theme_thumb_default,
            asdfasdfasdfasdf:{},
            del:function(el){
                u.confirm("确定删除组？",function(cancel){
                    if(!cancel){
                        u.ajax(
                            u.pagevar("delgroup","../data/success.json"),
                            {buid:fans.pageid},
                            function(t){
                                if(t.vl()){
                                	location.href=ctx+"/attention/toaddens";
//                                    fans_sr.doo();
                                }
                            }
                        )
                    }
                },"删除组")
            },
            edit:function(el){
                u.confirm("<input type='text' class='editname' value="+fans.pagename+">",function(cancel){
                    var editname =$("input.editname");
                    if(!cancel){
                        var new_gp_name = $.trim(editname.val());

                        if(!reg.test(new_gp_name)){
                            u.tips($("input.editname"),"组名不能超过6个字符",2000);
                            return false;
                        }
                        u.ajax(
                            u.pagevar("editgroup","../data/success.json"),
                            {buid:fans.pageid,editname: new_gp_name },
                            function(t){
                                if(t.vl()){
                                	fans.pagename   =   new_gp_name;
                                    grou.current_gp.name = new_gp_name;
                                }
                            }
                        )
                    }
                },"修改组名称")
            },
            change_group:function(el){
                if(grou.group_list.length <= 1){
                    //u.msg("只有一个分组，无法移动");
                    u.tips(this,"现在无用户分组，请在左边进行添加");
                    return;
                }

                fans.asdfasdfasdfasdf = el;
                fans.groupgid=fans.pageid;
                fans.group_mumbers=grou.group_list;
                $(".change_group input:checked").parent("label").hide();
            },
            determine:function(el){
                if(fans.groupgid == grou.current_gp.gid){
                    u.msg("未更改组");
                    fans.asdfasdfasdfasdf = {};
                    return;
                }
                u.confirm("确定更改组？",function(cancel){
                    if(!cancel){
                        u.ajax(
                            u.pagevar("change_groups","../data/success.json"),
                            {userId:el.userId,gid:$("input.groupgid").val()},
                            function(t){
                                if(t.vl()){
                                    fans.asdfasdfasdfasdf = {};
                                    fans.list.remove(el),
                                    fans.total--;
                                }
                            }
                        )
                    }
                })
            },
            cal:function(el){
                fans.asdfasdfasdfasdf = {};
            },
            cancel_attention:function(el){
                fans.userId=el.userId;
                qxguanzhu();
            },
            attention:function(el){
                fans.userId=el.userId;
                fans.fansel=el
                guanzhu();
            }
        })

        /*取消关注*/
        function  qxguanzhu(){
            u.confirm("确定取消关注？",function(cancel){
                if(!cancel){
                    u.ajax(
                        u.pagevar("cancelattent","../data/success.json"),
                        {buid:fans.userId},
                        function(t){
                            if(t.vl()){
                                fans_search.doo();
                                fans_sr.doo();
                            }
                        }
                    )
                }
            })
        }



        /*关注*/
        function guanzhu(){
            u.confirm("确定关注？",function(cancel){
                if(!cancel){
                    u.ajax(
                        u.pagevar("attention","../data/success.json"),
                        {buid:fans.userId},
                        function(t){
                            if(t.vl()){
                                fans.list.remove(fans.fansel)
                                fans_search.doo();
                                fans_sr.doo();
                            }
                        }
                    )
                }
            },"关注粉丝")
        }

        //新建组
        $("span.addgroups").click(function(){
            var cindex = u.confirm("新组名 <input type='text' class='addname'>",function(cancel){
                if(!cancel){
                    var gname = $.trim($("input.addname").val());

                    if(!gname){
                        u.tips($("input.addname"),"请输入组名称",2000);
                        return false;
                    }

                    if(!reg.test(gname)){
                        u.tips($("input.addname"),"组名不能超过6个字符",2000);
                        return false;
                    }

                    var repeat = 0;
                    $.each(grou.group_list.$model,function(k,el){
                        if(gname == el.name){
                            u.tips($("input.addname"),"组名重复",2000);
                            repeat = 1;
                            return false;
                        }
                    });

                    if(repeat) return false;

                    u.ajax(
                        u.pagevar("addname","../data/success.json"),
                        {
                            addname:gname
                        },
                        function(t,data){
                            if(t.vl()){
                                grou.group_list.push({
                                    name:gname,
                                    gid:data.data
                                })
                                //fans_sr.doo();

                                u.layer.close(cindex);
                            }
                        }
                    )

                    return false;
                }
            },"新建分组")
        })


        var pa;
        var fans_sr = new (require("$/splitReq"))({
            container:"#page_no",
            reqPath: u.pagevar("fans_list","../data/fans_list.json"),
            rows:12,
            continuous_page: 0,
            edge_page: 0,
            pagename:"",
            firstReqAuto:false,
            onReq:function(para){
                pa = para;
                para.gid = fans.gp.gid;
               /* ht.removeClass("no_cl_loading");*/

            },
            onRece:function(data){
//                var t = u.getTooken(u.cj.tojson(data));
//                if(!t.vl()) {
//                    //出错了
//                }
            },
            onData:function(data){
                $.each(data.data,function(k,e){
                    e.group_list_showing = false;
                })
                fans.list   = data.data;
                fans.total  = data.totalCount;
                ht.addClass("no_cl_loading");
            }
        })


        u.ajax(
            u.pagevar("group_list","../data/group_list.json"),{},function(t,data){
                if(t.vl()){
                    grou.group_list.pushArray(data.data);
                    fans.group_mumbers.pushArray(data.data);
                    grou.member_click(data.data[0],0);
                }
                ht.addClass("no_cl_loading")
            }
        )


        //单击所有粉丝时候

        $(".bg-all").click(function(){
            delete pa.gid;
            pa.type = "fan";
            fans.pagename = "所有粉丝";
            grou.cureent_gp_index = -1;
            fans_sr.doo();
            fans.view_mode = 1;

        });


        //添加关注
        var sear=$("input[name='searchfriend']");
        $(".addattention").click(function(){
            sear.val("");
            if($('.concern').data().layer_index === undefined){
                $('.concern').data().layer_index = layer.open({
                    type: 1,
                    title: false,
                    closeBtn: 1,
                    shadeClose: false,
                    skin: 'yourclass',
                    area: ['738px', '493px'], //宽高
                    content: $('.concern'),
                    end:function(){
                        return false;
                    },
                    cancel:function(){
                        $(".layui-layer-shade").hide();
                        $('.concern').parents(".layui-layer").hide();
                        return false;
                    }
                });
            }else{
                fans_search.doo();
                $('.concern').parents(".layui-layer").show();
                $(".layui-layer-shade").show();
            }
        })

        var search_fans_list;
        search_fans_list = avalon.define({
            $id:"search_fans_list",
            list:[],
            /*头像默认图片*/
            default_headimg:co.theme_thumb_headimg,
            theumimg:co.theme_thumb_default,
            //图片文件路径前缀
            ibpath:co.media_image_path,
            attention:function(el){
                fans.userId=el.userId;
                fans.fansel=el
                guanzhu();
            }

        })

        var fans_search = new (require("$/splitReq"))({
            reqPath: u.pagevar("fans_search_list","../data/fans_list.json"),
            reqType:"post",
            rows:9,
            container:"#search_fans_page",
            total_page: 0,
            continuous_page: 0,
            current_page: 0,
            edge_page: 0,
            //firstReqAuto:false,
            onReq:function(para){
                para.key = $(".searchfriend>input").val();
            },
            onRec:function(data){
                var t = u.getTooken(u.cj.tojson(data));
                if(!t.vl()) {
                    //出错了
                }
            },
            onData:function(data){
                search_fans_list.list   = data.data;
                ht.addClass("no_cl_loading");
            }
        })


        $(".searchfriend>span").click(function(){
            fans_search.doo();
        });
        sear.keydown(function(e){
            if(e.keyCode==13){
                fans_search.doo();
            }
        });

        avalon.scan();

    })

    return {};
});

