/*
*服务查询
* */
define(function(require,exports,module){
    var cl = require("ctool");
    var cj = require("ctooj");
    var _ = require.resolve("../");
    var comm = require("__/js/comm");

    window.ctx = window.ctx || proot;
    comm.ctx();
    //服务类型选中(多选)
    $(".fwtype>span>i").click(function(){
        var type = $(this);
        type.toggleClass("ed");
    })
    //高级选择接口选中（单选）
    $(".senior>span.jiekou>span>i").click(function(){
        var type = $(this);
        type.parent().siblings().find("i").removeClass("ed");
        type.parent().find("i").addClass("ed")
    })

    //点击搜索
    $(".service_search_top>span.fw_search").click(function(){
        lister.doo();
    })

    //servicemap高度
    var sermap=$(".servicemap");
    cj.winResize(300,function(sw,sh){
        sw = sw;
        sermap.css({
            height: sh-360,
            overflow: "hidden",
            width:sw-56
        })
    })

    //加载服务查询——地图列表
    var page=$(".service_query .page");
    var lister;
    var servicemap = $(".servicemap>ul");
    var search = $(".service_search_top>input.fw_value");
    var fwtype =function(){
    	return $(".fwtype>span>i.ed");
    }
    var senior =function(){
        return $(".jiekou>span>i.ed");
    }
    require.async(["$/splitReq","parseTpl"],function(SR,parseTpl){
        var sr = lister =new SR({
            container:$(".page"),
            /*reqPath:_ + "data/service_query.json",*/
            reqPath:window.service_query_ajax || (_ + "data/service_query.json"),
            rows:8,
            reqType:"POST",
            cssStyle:"compact-theme",
            onReq:function(para){
                $.extend(
                    para,
                    {
                        search:search.val(),
                        fwtype:fwtype().map(function(i,el){
                            return $(el).parent().text()
                        }).toArray().join("%%"),
                        jiekou:senior().parent().text(),
                        fbtime:$("[name=fbtime]").val(),
                        gxtime:$("[name=gxtime]").val(),
                        yxqk:$("[name=yxqk]").val()
                    }
                )
            },
            onData: function(data){
                data = cj.tojson(data);
                var null_tip = "<li>查询结果空</li>";
                
                if(data.result=="error"){
                    alert(data.msg);
                    data.rows = [];
                    page.hide();
                }else{
                    null_tip = "";
                    page.show();
                }

                if(data.rows.length==0){
                    var null_tip = "<li>查询结果空</li>";
                    page.hide();
                }


                servicemap.html(
                    null_tip || parseTpl(
                        data.rows,
                        '<li sid={id}><img src="{thumb}" alt="">'+
                            '<div class="map_describes">'+
                               // '<p>服务编码：{id}</p>'+
                                '<p>服务名称：{mapname}</p>'+
                                '<p>有效期：{validitytime}</p>'+
                                '<em>{isonline}</em>'+
                            '</div>'+
                            '<div class="handlemap">'+
                                '<span class="yulan"><a href='+comm.ctx()+'/u2{shareurl} target=_blank>预览</a></span>'+
                                '<span class="online">{revert_online}</span>'+
                                '<span class="updown">下线</span>'+
                            '</div>'+
                        '</li>',
                        function(val,name){
                        	//XX(0, "下线"),SX(1, "在线"),GQ(2, "挂起");
                            if(name=="revert_online"){//操作
                                if(this.isonline == 1){
                                    return "挂起";
                                } else if (this.isonline == 2) {
                                    return "上线";
                                }else{
                                    return "未知";
                                }
                            }else if(name=="isonline"){//状态
                                if(this.isonline == 1){
                                    return "在线";
                                } else if (this.isonline == 2) {
                                    return "挂起";
                                }else{
                                    return "未知";
                                }
                            }else if(name=="validitytime"){
                            	if(this.validitytime == null){
                                    return "";
                                }
                            }else if(name=="mapdesc"){
                            	if(this.mapdesc == null){
                                    return "";
                                }
                            }else if(name=="thumb"){
                                return comm._def_map_thumb(this.umicon,"http://oss.trmap.cn/rs/download/" + this.umicon);
                            }
                        }

                    )
                )
            }
        })
    })

    //点击下线变上线
    /*servicemap.delegate(".online","click",function(){
        var online = $(this);
        var li = online.parents("li:first");
        var online_em = online.parent().siblings("div").find("em");
        var para ={
            "mapid":li.attr("sid"),
            "statue":online.text()
        }*/
        //点击上线挂起操作
       /* online_operate(para,function(){
        	comm.ajax(ctx+"/entrelmap/online",para,function(tk,data){
        		if(tk.vl(true)){
        			if(online.text()=="在线"){
                        online.text("挂起");
                        online_em.text("在线");
                    }else if(online.text()=="挂起"){
                        online.text("在线");
                        online_em.text("挂起");
                    }
        			lister.doo();
        			comm.msg("操作成功");
        		}else{
        			comm.msg("操作失败");
        		}
        	})
        })

    })

    function online_operate(para,callback){
        $.post(window.online_ajax || (_+"data/sava_profile.json"),para)
            .fail(function(){
                throw "操作失败"
            })
            .done(function(data){
                data=cj.tojson(data);
                if(data.result == "success"){
                    (callback || $.noop)();
                }else{
                    alert("操作出错")
                }
            })
    }*/

    //点挂起、上线
    servicemap.delegate(".online","click",function(){
        var online = $(this);
        var li = online.parents("li:first");
        var online_em = online.parent().siblings("div").find("em");
        if(online.text()=="上线"){
            comm.confirm("确定更改服务在线状态？",function(cancel){
                if(cancel){
                    return;
                }
                var para ={
                    "mapid":li.attr("sid"),
                    "statue":online.text()
                }
                comm.ajax(ctx+"/entrelmap/online",para,function(tk,data){
                    if(tk.vl(true)){
                        online.text("挂起");
                        online_em.text("在线");
                        lister.doo();
                        comm.msg("操作成功");
                    }else{
                        comm.msg("操作失败");
                    }
                })
            })
        }else if(online.text()=="挂起"){
            comm.confirm("<textarea name='guaqi' id='tx_guaqi' type='text' cols='30' rows='5' class='dell'></textarea>",function(cancel){
                if(cancel){
                    return;
                }
                var guaqi = $("#tx_guaqi");
                var guaqi_tx = $.trim(guaqi.val());

                if(!guaqi_tx){
                    comm.tips(guaqi,"请输入挂起原因")
                    return false;
                }
                var para ={
                    "mapid":li.attr("sid"),
                    "statue":online.text(),
                    "reason":$("[name=guaqi]").val()
                }
                comm.ajax(ctx+"/entrelmap/online",para,function(tk,data){
                    if(tk.vl(true)){
                        online.text("在线");
                        online_em.text("挂起");
                        lister.doo();
                        comm.msg("操作成功");
                    }else{
                        comm.msg("操作失败");
                    }
                })
            },"请输入挂起原因")
        }else{
            comm.msg("操作失败");
        }
    })
    //点击下线删除
    servicemap.delegate(".updown","click",function(){
        var updown =$(this);
        var sid = updown.parents("li:first");

        comm.confirm("<textarea name='dell' id='tx_xiaxian' type='text' cols='30' rows='5' class='dell'></textarea>",function(cancel){
            if(cancel){
                return;
            }
            var xiaxian = $("#tx_xiaxian");
            var xiaxian_tx = $.trim(xiaxian.val());

            if(!xiaxian_tx){
                comm.tips(xiaxian,"请输入下线原因")
                return false;
            }
            var para ={
                "mapid":sid.attr("sid"),
                "statue":updown.text(),
                "reason":$("[name=dell]").val()
            }
            comm.ajax(ctx+"/entrelmap/online",para,function(tk,data){
                if(tk.vl(true)){
                    lister.doo();
                    comm.msg("操作成功");
                }else{
                    comm.msg("操作失败");
                }
            })
        },"下线会删除服务，是否要删除?")
    })


    //鼠标放上去“下线”“预览”显示
    $(".servicemap>ul").delegate("li","hover",function(){
        var mm =$(this);
        mm.toggleClass("ed");
    })

    return{};
})