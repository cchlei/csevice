/*
*服务查询
* */
define(function(require,exports,module){
    var cl = require("ctool");
    var cj = require("ctooj");
    var parseTpl = require("parseTpl");
    var comm = require("__/js/comm");
    var _ = require.resolve("../");

    parseTpl.addVars({
        suggestion:"",
        animate:"ed"
    });



    //servicemap宽度
    var sermap=$(".servicemap");
    cj.winResize(300,function(sw,sh,wi){
        var ac = arguments.callee;
        sw = sw;
        sermap.css({
            overflow: "hidden",
            width:sw-56
        })

        var fff = true;
        !function(){
            if(fff){
                fff = false;
                wi.resize();
            }
        }.delayCall(300);

    })

    //加载服务获取内容
    var lister;
    var obtain =$(".service_obtain .servicemap .grid");


    require.async("$/selectBox",function(){

       /* $(".sqtime,.jztime").selectBox({
            //mobile: true,
            menuSpeed: 'fast'
        })*/
    });


    var cache_obtain_data = {};

    var obtain_tpl =
        '<li sid="{id}" class="status_{isapproved} {animate}">'+
        '   <img src="{relUserMap.umicon}">'+
        '   <div class="describe">'+
        '       <p class="full"><span>订单编号</span><em>{number}</em></p>'+
        '       <p class="full" tiptx="{suggestion}"><span>订单状态</span><em>{isapproved_tx}</em></p>'+
        '       <p class="s0 s1 s2 s3 s4"><span>服务名称</span><em>{relUserMap.mapname}</em></p>'+
        '       <p class="full"><span>发布单位</span><em>{settercomp}</em></p>'+
        '       <p class="full"><span>申请字段</span><em>{attributesName}</em></p>'+
        '       <p class="full"><span>申请日期</span><em>{applytime}</em></p>'+
        '       <p class="s0 s2 s3"><span>申请时长</span><em>{applymonth}个月</em></p>'+
        '       <p class="s1 s3 s5"><span>截止日期</span><em>{utilDate}</em></p>'+
        '       <p class="operation">' +
        '           操作:' +
        '           <a class="btn_preview"  target="_blank" href="'+comm.ctx()+'/entrelmap/tolimitview/{relUserMap.id}">预览</a>' +
        '           <a class="btn_detail">订单详情</a>' +
        '           <a class="btn_revert">撤销</a>' +
        '           <a class="btn_edit" target="_blank" href="'+comm.ctx()+'/entrelmap/toApply/{relUserMap.id}/{id}">编辑</a>' +
        '           <a class="btn_goon">续约</a>' +
        '           <a class="btn_dele">删除</a>' +
        '       </p>'+
        '   </div>'+
        '   <em></em>'+
        '</li>'
    ;




    var obtain_list_map_func = function(v,name){

        if(name == "isapproved_tx"){
            return comm.dic_order_status[this.isapproved];
        }else  if(name=="relUserMap.umicon"){
            return comm._def_map_thumb(this.relUserMap.umicon,"http://oss.trmap.cn/rs/download/" +this.relUserMap.umicon);
        }else if(comm.isnull(v) && v!==0){
            return "";
        }
    }
    require.async(["$/splitReq"],function(SR){

        //服务获取点击
        var approval =$(".approval>span")
        approval.click(function(){
            var me = $(this);
            var no = me.attr("no");
            me.toggleClass("ed");
            if(me.is(".ed")){
                me.siblings().removeClass("ed");
            }
            sr.doo();

            //全选
            if(no == -1){

            }

        })


        var tip_index
        obtain.delegate("[tiptx]","mouseenter mouseleave",function(e){
            var me = $(this);
            var tiptx = me.attr("tiptx");
            if(comm.isnull(tiptx))   return;

            if(e.type == "mouseenter"){
                tip_index = layer.tips(tiptx, me, {
                    tips: [1, '#3595CC'],
                    offset: ['0', '-50px']
                });
            }else{
                layer.close(tip_index);
            }
        })


        var sr = lister = new SR({
            container:$(".page"),
            reqPath:window.ajax_service_query,
            rows:4,
            reqType:"POST",
            cssStyle:"compact-theme",
            onReq:function(para){
                $.extend(
                    para,
                    {
                        sqtime:$("[name=sqtime]").val(),
                        jztime:$("[name=jztime]").val(),
                        approval:approval.filter(".ed").map(function(k,v){
                            return this.getAttribute("no");
                        }).toArray().join(""),
                        search:$(".fw_value").val()
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

                $.each(data.rows,function(key,ele){
                    cache_obtain_data[ele.id] = ele;
                });;

                //搜索内容加载
                obtain.html(
                    null_list||parseTpl(data.rows,obtain_tpl,obtain_list_map_func)
                )

            }


        })

    })


    //撤销操作
    obtain.delegate(".btn_revert","click",function(e){
        var li = $(this).parents("li:first");
        var sid = li.attr("sid");
        var data = cache_obtain_data[sid];
        comm.confirm("确定撤销服务？",function(cancel){
            if(cancel){
                return;
            }
            comm.ajax(ajax_revoke,{id:sid},function(t){
                if(t.vl() && data) {
                    data.isapproved = 4;
                    li.replaceWith(parseTpl(data, obtain_tpl,obtain_list_map_func));
                }
            })
        })

    });

    //续约
    obtain.delegate(".btn_goon","click",function(e){
        var li = $(this).parents("li:first");
        var sid = li.attr("sid");
        var data = cache_obtain_data[sid];
        comm.ajax(ctx+"/entapp/couldApply",{id:sid},function(t){
            if(t.vl() && data) {
            	comm.confirm(
                    "<div class='lite_input_row'>" +
                    "   <em class='label'>服务时长：</em>" +
                    "   <input id='gon_on_time' style='width: 8em;' placeholder='单位：月'/>" +
                    "</div>",
                    function(cancel){
                        if(!cancel){
                            var tx = $("#gon_on_time");
                            var v = $.trim(tx.val());
                            if(!v){
                                comm.tips(tx,"请输入一个时长");
                                return false;
                            }

                            if(/[^\d\.]/.test(v)){
                                comm.tips(tx,"时长必须为数字");
                                return false;
                            }
                            if(!/^\d{1,2}$/.test(v)){
                                comm.tips(tx,"时长不超过99");
                                return false;
                            }
                            comm.ajax(ajax_goon,{id:sid,date:v},function(t){
                                if(t.vl() && data) {
                                	lister.doo();
                                	//以下做什么操作了？？？
                                    //data.isapproved =   1;
                                    //data.sqsc   =   v;
                                    t.data.ed = "";
                                    var item = $(parseTpl(t.data, obtain_tpl,obtain_list_map_func));
                                    obtain.prepend(item);
                                    item.addClass("ed");

                                    //li.replaceWith(parseTpl(data, obtain_tpl,obtain_list_map_func));
                                }
                            })
                        }
                    },
                    "时长设置"
                );
            }
        });
    });



    //删除
    obtain.delegate(".btn_dele","click",function(e){
        var li = $(this).parents("li:first");
        var sid = li.attr("sid");
        comm.confirm("确认要删除该订单吗?",function(cancel){
            if(!cancel){
                comm.ajax(ajax_dele,{id:sid},function(t){
                    if(t.vl()) {
                        li.remove();
                    }
                })
            }
        });
    });


    //搜索
    $(".service_obtain .fw_search").click(function(){
        lister.doo();
    })

    var vm;
    require.async("_/avalon/avalon.min",function(){
        vm = avalon.define({
            $id:"obxq",
            dic_order_status:comm.dic_order_status,
            //data:data:data.rows[0]
            data:default_vm_data
        })
        avalon.scan();
    });


    var default_vm_data = {
        setter:{},
        getter:{},
        relUserMap:{},
    }


    /**
     * 订单详情容器
     * @type {*|jQuery|HTMLElement}
     */
    var detail_layer = $(".obxq").show();
    detail_layer.wrap("<div class='obxqCont'></div>");
    detail_layer = detail_layer.parent().appendTo(cj.getHideBox());


    //点击订单详情
    obtain.delegate(".btn_detail","click",function(){
    	var id =$(this).parents("li:first").attr("sid");

        /*订单详情加载*/
        var index = comm.layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });

        comm.ajax(window.service_apply_from_ajax || (_+"data/service_obtain_from.json"),{id:id},function(tk,data){
            layer.close(index)


            if(tk.vl()){
                vm.data = tk.data|| default_vm_data;
                detail_layer.ly_index = comm.layer.open({
                    type: 1,
                    title: false,
                    closeBtn: 1,
                    shadeClose: true,

                    area: ['auto','660px'],
                    maxWidth: ['auto','1000px'],
                    move: 'true',
                    skin: 'yourclass',
                    content: detail_layer
                });
            }else{
                comm.msg("订单详情加载失败");
            }
        })

    })


    //关闭订单详情
    detail_layer.delegate(".ddtop>.close","click",function(){
        cj.getHideBox().append(detail_layer);
        comm.layer.close(detail_layer.ly_index);
    })




    //点击删除
    obtain.delegate("span.del","click",function(){
        var del = $(this);
        comm.confirm("是否要删除",function(cancel){
            if(cancel){
                return;
            }
            $.post(_+("sava_profile.json"),{sid:del.parents("li").attr("sid")})
                .fail(function () {
                    throw "请求出错"
                })
                .done(function(data){
                    data=cj.tojson(data);
                    if(data.result==success){
                        comm.msg("删除成功");
                        lister.doo();
                    }else{
                        comm.msg("删除失败");
                    }

                })

        })
    })



    return{};
})