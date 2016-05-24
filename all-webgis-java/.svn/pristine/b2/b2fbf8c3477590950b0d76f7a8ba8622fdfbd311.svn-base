/*
*已审批
* */
define(function(require,exports,module){
    var cl = require("ctool");
    var cj = require("ctooj");
    var parseTpl = require("parseTpl");
    var comm = require("__/js/comm");
    var _ = require.resolve("../");

    //搜索
    var search=$(".service_app .fw_search")
    search.click(function(){
        lister.doo();
    })

    //加载未审批内容
    var lister;
    var approval =$(".service_app .servicetab");
    require.async(["$/splitReq","parseTpl"],function(SR,parseTpl){
        var sr = lister =new SR({
            container:$(".page"),
            reqType:"POST",
            rows:12,
            reqPath:window.service_approval_sp_ajax || (_+"data/service_approval_sp.json"),
            cssStyle:"compact-theme",
            onReq:function(para){
                $.extend(
                    para,
                    {   search:$(".fw_value").val(),
                        sqtime:$("[name=sqtime]").val(),
                    }
                )
            },
            onData: function(data){
                data = cj.tojson(data);
                var db = approval.find("tbody");
                if(!db.length){
                    approval.append("<tbody></tbody>");
                    db = approval.find("tbody")
                }
                var null_tip = "<li class='result_null_tip'>查询结果空</li>";

                if(comm.valid(data)){
                    null_tip = "";
                }else{
                    data.rows = [];
                }

                if(data.rows.length > 0){
                    null_tip = "";
                }
                db.html(
                    null_tip || parseTpl(
                        data.rows,
                        '<tr sid="{id}">'+
                        '<td><img src="{isapproved}"></td>'+
                        '<td>{number}</td>'+
                        '<td>{gettercomp}</td>'+
                        '<td>{relUserMap.mapname}</td>'+
                        '<td>{applytime}</td>'+
                        '<td>{applymonth}</td>'+
                        '<td><span class="des">订单详情</span><span class="{ispause}"></span></td>'+
                        '</tr>',
                        function(val,name){
                            if(name=="isapproved"){
                                //已过期
                                if(this.isapproved==3){
                                    return ctx+"/assets/images/h_y.png"
                                }else if(this.isapproved==1){
                                    //未过期
                                    return ctx+"/assets/images/h_n.png";
                                }else{
                                    return ctx+"/assets/images/p_fail.png";
                                }
                            }else if(name=="ispause"){
                                if(this.ispause==2){
                                    return "stop ed"//恢复
                                }else if(this.ispause!=2&&this.isapproved==1){
                                    return "stop";//暂停
                                }else{
                                    return "non";
                                }
                            }
                        }
                        
                    )
                ).prepend('<tr class="first"><th class="zt"></th><th>申请编号</th><th>申请企业</th><th>申请服务</th><th class="max">申请时间</th><th>申请时长</th><th class="max">操作</th></tr>')
                $(".servicetab span.stop").text("暂停");
                $(".servicetab span.stop.ed").text("恢复");
            }
        })
    })
    
    //订单详情加载
    var vm;
    require.async("_/avalon/avalon.min",function(){
        vm = avalon.define({
            $id:"yddxq",
            dic_order_status:comm.dic_order_status,
            data:{}
        })
        avalon.scan();
    });
    


	var xq = $(".yddxq").show().appendTo(cj.getHideBox());

    //点击订单详情
    approval.delegate(".des","click",function(){
        var id=$(this).parents("tr").attr("sid")
        comm.ajax(window.service_approval_sp_from_ajax || (_+"data/service_approval_from.json"),{id:id},function(tk,data){
        	if(tk.vl()){
        		vm.data = data;
        		cl.layer(function(layer){
                    layer.open({
                        type: 1,
                        title: false,
                        closeBtn: 1,
                        shadeClose: true,
                        area: ['auto','700px'],
                        maxWidth: ['auto','1000px'],
                        move: 'true',
                        skin: 'yourclass',
                        content: xq
                    });
                })
        	}else{
        		comm.msg("订单详情加载失败");
        	}
    })
 })     
        

       

    //关闭打印
    $(".ddtop>.close").click(function(){
        $(".layui-layer,.layui-layer-shade").hide();
    })
    $(".ddtop>.close").click(function(){
        $(".layui-layer,.layui-layer-shade").hide();
    })


    //点击暂停
    approval.delegate(".stop","click",function(){
        var ee=$(this)
        var mm =ee.parents("tr").attr("sid");
        comm.confirm("<textarea name='stop' id='stop' type='text' cols='30' rows='5' class='stop'></textarea>",function(cancel){
            if(cancel){
                return;
            }
            if(!cancel){
                var stopp=$("textarea.stop");
                var s=$.trim(stopp.val())
                if(!s){
                    comm.layer.tips("请输入暂停原因",stopp,{
                        time: 1000
                    })
                    return false;
                }else{
                    var para={
                        "id":mm,
                        "reason":stopp.val()
                    }
                    $.post(window.release_stop_ajax || (_+"data/sava_profile.json"),para)
                        .fail(function(){
                            throw "操作有误"
                        })
                        .done(function(data){
                            data=cj.tojson(data);
                            if(data.result == "success"){
                                comm.msg("操作成功");
                                lister.doo();
                                ee.addClass("ed");
                                ee.text("恢复")
                            }else{
                                comm.msg("操作失败");
                            }

                        });
                }
            }

        })
    })

    //点击
    approval.delegate(".stop.ed","click",function(){
        var mm =$(this).parents("tr").attr("sid")
        comm.confirm("确定恢复吗？",function(cancel){
            if(cancel){
                return;
            }
            $.post(release_hf_ajax || (_+"data/sava_profile.json"),{id:mm})
                .fail(function(){
                    throw "操作有误"
                })
                .done(function(data){
                    data=cj.tojson(data);
                    if(data.result == "success"){
                        comm.msg("操作成功");
                        lister.doo();
                    }else{
                        comm.msg("操作失败");
                    }

                });

        })
    })




    return{};
})