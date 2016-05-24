/**
 *
 * 地图发布--元数据编辑
 *
 */
define(function (require, exports, module) {
    var cl = require("ctool");
    var cj = require("ctooj");
    var _=require.resolve("../");
    var parseTpl = require("parseTpl");
    var comm = require("__/js/comm");

    //地区distpicker
    require("_/area/distpicker");


    parseTpl.addVars({
        default_img:comm._def_user_icon
    });

    
    cj.hrefPlus();
    
    $(function() {
        $("#distpicker").distpicker(
            window.default_area || {
                province: "陕西省",
                city: "西安市",
                placeholder:false
            }
        );

    });

   /* 服务大厅首页开始*/

    //banner宽度
    var banner =$(".banner");
    cj.winResize(300,function(sw,sh,wi,be_delay){

        if(be_delay)    return;
        var ac = arguments.callee;
        banner.css({
            width:sw-380-56
        })

        setTimeout(function(){
            ac(wi.width(),wi.height(),wi,true)
        },500);
    })

    //天地云地图快报以及入驻企业
    var hallnews = $(".newsflash>ul");
    var business =$(".business>.it");
    $.get(window.dall_index_news_ajax || (_ + "data/service_hall_index_new.json"))
        .fail(function(){
            throw "获取快报失败"
        })
        .done(function(data){
            require.async("parseTpl",function(PT) {
                data = cj.tojson(data);
                //快报
                hallnews.html(
                    PT(
                        data.rows,
                        '<li><em>●</em>{title}<span>{type}</span></li>',
                        function (val, name) {
                            if (name == "type") {
                                if (this.type == "hot") {
                                    return "HOT";
                                } else if (this.type == "news") {
                                    return "NEWS"
                                } else {
                                    return "";
                                }
                            }
                        }
                    )
                )
            })
        })

    $.get(window.hall_index_qy_ajax || (_ + "data/service_hall_index_qy.json"))
        .fail(function(){
            throw "获取快报失败"
        })
        .done(function(data){
            require.async(["_/marquIttop","parseTpl"],function(mq,PT) {
                data = cj.tojson(data);
                //入驻企业
                business.find("ul").html(
                    PT(
                        data.rows,
                        '<li class="item"><img src="http://oss.trmap.cn/rs/download/{img}" onerror="this.src=comm._def_user_icon"><span>{qyname}</span></li>',
                        function(v,n) {
                            if(n == "img"){
                                return v||comm._def_user_icon;
                            }
                        }
                    )
                )
                //加上向上滚动的代码
                business.find("ul").marquIt({
                    stepVal:2,
                    dura:45

                })
            })
        })
    /*服务大厅首页结束*/


    //search类型加载
    var sht = $(".service_hall_type");
    var page =$(".page");
    $.get(service_hall_type)
        .fail(function(){
            throw "调用数据失败"
        })
        .done(function(data){
            data = cj.tojson(data);
            var tpl = '<em>{type}</em>';
            var tpl1 ='<span>{type}</span>';


            var hall_type = $(".hall_type");
            var hall_branch = $(".hall_branch");

            $.each(data.menu,function(key1,el1){
                hall_type.append(parseTpl(el1, tpl));
                var p = $("<p></p>").appendTo(hall_branch);
                $.each(el1.branch,function(key2,el2){
                    p.append(parseTpl(el2, tpl1));
                })
            })

            //切换
            var em =$(".hall_type>em");
            var pp =$(".hall_branch>p");
            em.click(function(){
                var m = $(this);
                m.addClass("ed").siblings().removeClass("ed");
                var index = m.index();
                pp.hide().eq(index).show();
            })
            em.first().click();

            var hall_branch_span =$(".hall_branch>p>span");
            hall_branch_span.click(function(){
                var mm =$(this);
                /*mm.addClass("ed").siblings().removeClass("ed"); //单选 */
                mm.toggleClass("ed");
            })

        })

    //servicemap高度
    var servicemap =$(".servicemap");
    cj.winResize(300,function(sw,sh){

        servicemap.css({
            width:sw-56
        })
    })

    //加载地图搜索内容
    var lister;
    var service =$(".servicemap>ul");
    require.async(["$/splitReq","parseTpl"],function(SR,PT){
        var sr = lister =new SR({
            container:$(".page"),
            reqPath:window.service_hall_ajax || (_ + "data/service_hall.json"),
            rows:9,
            reqType:"POST",
            cssStyle:"compact-theme",
            onReq:function(para){
                $.extend(
                    para,
                    {
                        area:$("[name=area]").val(),
                        search:$(".fw_value").val(),
                        hall_type:$(".hall_type>em.ed").text(),
                        fwtype:$(".hall_branch>p:visible>span.ed").map(function(k,v){
                            return $(v).text()
                        }).toArray().join("%%")
                    }
                )
            },
            onData:function(data){
                data=cj.tojson(data);
                var null_list="<li>查询为空</li>"
                if(data.result=="error"){
                    alert(data.msg);
                    data.rows=[];
                }else{
                    null_list="";
                }

                if(data.rows.length==0){
                    var null_list="<li style='background: none'>查询为空</li>"
                }
                $.each(data.rows,function(k,e){
                    $.each(e,function(kk,ee){
                        if(ee && typeof ee == "object"){
                            $.each(ee,function(kkk,eee){
                                e[kk + "." + kkk] = eee;
                            })
                        }
                    })

                });
                
                //搜索内容加载
                service.html(
                    null_list || PT(
                        data.rows,
                        '<li sid="{id}">'+
                            '<a target=_blank href="'+ctx+'/entrelmap/tolimitview/{id}"><img  src="http://oss.trmap.cn/rs/download/{umicon}"/></a>'+
                            '<div class="describe">'+
                                '<p><span>服务名称</span><em>{mapname}</em></p>'+
                                '<p><span>更新日期</span><em>{updateretime}</em></p>'+
                                '<p><span>有效日期</span><em>{validitytime}</em></p>'+
                                '<p><span>发布单位</span><em>{user.name}</em></p>'+
                                '<p><span>描述范围</span><em title="{scope}" class="severfw">{scope}</em></p>'+
                                '<p class="sq_hall">申请</p>'+
                            '</div>'+
                            '<em></em>'+
                        '</li>'

                    )
                )
            
            }
            

        })
    })

    //搜索
    var hallcenter =$(".hallcenter");
    $(".service_hall .fw_search").click(function(){
        hallcenter.hide();
        servicemap.show();
        sht.show();
        page.show();
        lister.doo();
    })





    
    comm.getUser(function(u){
        var fu = $(".fwtop .user").show();
        fu.find("img").attr("src", u.headimg);
        fu.find(".name").text(u.name);
    });

    //点击提交
    service.delegate("p.sq_hall","click",function(){
        var sq = $(this);
        var mapid = sq.parents("li").attr("sid");
        comm.ajax(ctx+"/entrelmap/isApply/"+mapid,{},function(t){
            if(t.vl()){
                //成功
            	window.location=ctx+'/entrelmap/toApply/'+mapid+'/null';
            }
        });
    })
    return {};
})
;