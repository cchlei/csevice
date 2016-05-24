/*
 *服务查询
 * */
define(function(require,exports,module){
    var cl = require("ctool");
    var cj = require("ctooj");
    var comm = require("__/js/comm");
    var _ = require.resolve("../");

    window.ctx = window.ctx || proot;

    //点击搜索
    $(".map_search_top>span.map_search").click(function(){
        lister.doo();
    })

    //releasemap高度
    var resmap=$(".releasemap");
    cj.winResize(300,function(sw,sh){
        sw = sw;
        resmap.css({
            height: sh-299,
            overflow: "hidden",
            width:sw-56
        })
    })

    //加载地图制作与发布——地图列表
    var lister;
    var releasemap = $(".releasemap>ul");
    var search = $(".map_search_top>input.map_value");
    var fbtime =$(".map_search_top>.fbtime");
    require.async(["$/splitReq","parseTpl"],function(SR,parseTpl){
        var sr = lister =new SR({
            container:$(".page"),
            reqPath:window.map_make_ajax || (_ + "data/map_make.json"),
            reqType:"POST",
            rows:8,
            cssStyle:"compact-theme",
            onReq:function(para){
                $.extend(
                    para,
                    {
                        search:search.val(),
                        fbtime:$("[name=fbtime]").val()
                    }
                )
            },
            onData: function(data){
                data = cj.tojson(data);
                var null_tip = "<li class='result_null_tip'>查询结果空</li>";

                if(comm.valid(data)){
                    null_tip = "";
                }else{
                    data.rows = [];
                }

                if(data.rows.length > 0){
                    null_tip = "";
                }


                releasemap.html(
                    null_tip || parseTpl(
                        data.rows,
                        '<li sid={id}><img src="{thumb}" alt=""><span>{mapname}</span>'+
                            '<div class="map_bg"></div>'+
                            ' <div class="mapoperation">'+
                                '<p class="{bg}">{isshare}</p>'+
                                '<ul>'+
                                    '<li class="fb"><a>发布</a></li>'+
                                    '<li class="bj"><a href="'+ctx+'/entmap/toEditEntMap/{id}">编辑</a></li>'+
                                    '<li class="sc">删除</li>'+
                                '</ul>'+
                            '</div>'+
                         '</li>',
                        function(val,name){
                            if(name=="isshare"){
                                if(this.isshare == 0){
                                    return "未发布";
                                } else if (this.isshare ==1) {
                                    return "已发布";
                                }else{
                                    return "未知";
                                }
                            }
                            if(name=="bg"){
                                if(this.isshare == 0){
                                    return "blue";
                                } else if (this.isshare ==1) {
                                    return "orang";
                                }else{
                                    return "mr";
                                }
                            }
                            if(name=="mapdesc"){
                                if(this.mapdesc == null){
                                    return "";
                                } 
                            }if(name == "thumb"){
                                return comm._def_map_thumb(this.umicon,"http://oss.trmap.cn/rs/download/" + this.umicon);
                            }
                        }
                    )
                )
            }
        })
    })


    //点击发布，更改未发布状态
    $(".grid").delegate(".mapoperation>ul>li.fb","click",function(){
        var fb =$(this);
        var li = fb.parents("li:first");
        var p  =fb.parent().siblings();
        var para ={
            "id":li.attr("sid")
        }
        comm.ajax(window.release_ajax|| (_+"data/sava_profile.json"),para,function(t){
        	if(t.vl()){
        		location.href = window.release_url+"/"+li.attr("sid")
        	}
        });
    })
 

    //删除地图
    $(".grid").delegate(".mapoperation>ul>li.sc","click",function(){
        var sc =$(this);
        var sid = sc.parents("li:first");

        comm.confirm("是否要删除",function(cancel){
            if(cancel){
                return;
            }
            var mapid={
                "id":sid.attr("sid")
            }
            $.post(window.release_dele_ajax || (_+"data/sava_profile.json"),mapid)
                .fail(function(){
                    throw "删除操作失败"
                })
                .done(function(data){
                    data=cj.tojson(data);
                    if(data.result == "YFB"){
                        comm.msg("请先将图层下线才可以进行删除操作！");
                        lister.doo();
                    }else{
                    	if(data.result == "success"){
                            comm.msg("删除成功");
                            lister.doo();
                        }else{
                            comm.msg("删除失败");
                        }
                    }
                })
            ;


        })

    })
    //鼠标放上去“发布”“编辑” “删除”显示
    $(".grid").delegate(".mapoperation>ul>li","hover",function(){
        var mm =$(this);
        mm.toggleClass("ed");
    })


    /**
     * 创建地图动作
     */
    var createmap = ({
        init:function(){
            var m = this;
            m.btn = $(".makemap");

            var tpl =
                "<form class='map_creater_form'  method='POST' action='" + window.map_create + "'>" +
                "   <h2>创建新的地图</h2>" +
                "   <p><em>地图名称:</em>  <input name='mapname' placeholder='3-11个字'/></p>" +
                "   <p><em>地图描述:</em>  <textarea name='mapdesc'></textarea></p>" +
                "   <p><em>&nbsp;</em><a class='btn'>创 建</a></p>" +
                "</form>"
            ;

            m.el = $(tpl);

            m.form = m.el[0];

            m.el.mapname = m.el.find("[name=mapname]");
            m.el.mapdesc = m.el.find("[name=mapdesc]");

            m.el.appendTo(cj.getHideBox());

            m.btn.click(function(e){
                e.preventDefault();
                m.show();
            });

            m.el.find(".btn").click(function(){
                m.submit();
            });

            m.el.find("p").keydown(function(e){
                if(e.keyCode==13){
                    m.submit();
                    return false;
                }
            });

            return m;
        },

        show:function(){
            var m = this;
            comm.layer.open({
                type: 1,
                shade: 0.7,
                area:["auto","auto"],
                title: false, //不显示标题
                content: m.el, //捕获的元素
                cancel: function(index){
                    layer.close(index);
                    m.form.reset();
                }
            });

        },

        submit:function(){
            var m = this;

            var mn = $.trim(m.el.mapname.val());
            var desc = $.trim(m.el.mapdesc.val());

                if (!mn) {
                    comm.tips(m.el.mapname, "请输入地图名称")
                    return;
                }

                if (mn.length < 3 || mn.length > 11) {
                    comm.tips(m.el.mapname, "地图名称长度要求为3-11字符")
                    return;
                }
                if (!desc) {
                    comm.tips(m.el.mapdesc, "请输入地图描述")
                    return;
                }


                if (desc.length > 200) {
                    comm.tips(m.el.mapdesc, "描述长度不能大于200字符")
                    return;
                }

                m.el.submit();

                m.form.reset();
            }

    }).init();


    return{};
})