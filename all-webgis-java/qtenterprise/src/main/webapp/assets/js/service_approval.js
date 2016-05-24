/*
*审批
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

    //加载服务审批内容
    var lister;
    var approval =$(".service_app .servicetable");
    require.async(["$/splitReq","parseTpl"], function (SR, parseTpl) {
        var sr = lister = new SR({
            container: $(".page"),
            reqType: "POST",
            rows: 12,
            reqPath: window.service_approval_ajax || (_ + "data/service_approval.json"),
            cssStyle: "compact-theme",
            onReq: function (para) {
                $.extend(
                    para,
                    {
                        sqtime: $("[name=sqtime]").val(),
                        search: $(".fw_value").val()
                    }
                )
            },
            onData: function (data) {
                data = cj.tojson(data);
                var db = approval.find("tbody");
                if (!db.length) {
                    approval.append("<tbody></tbody>");
                    db = approval.find("tbody")
                }
                var null_tip = "<li class='result_null_tip'>查询结果空</li>";

                if (comm.valid(data)) {
                    null_tip = "";
                } else {
                    data.rows = [];
                }

                if (data.rows.length > 0) {
                    null_tip = "";
                }
                db.html(
                    null_tip || parseTpl(
                        data.rows,
                        '<tr sid="{id}">' +
                        '<td>{number}</td>' +
                        '<td>{gettercomp}</td>' +
                        '<td>{relUserMap.mapname} </td>' +
                        '<td>{applytime}</td>' +
                        '<td>{applymonth}</td>' +
                        '<td><span class="des">订单详情</span><span class="pz">批准</span><span class="npz">不批准</span></td>' +
                        '</tr>'
                    )
                ).prepend('<tr class="first"><th>申请编号</th><th>申请企业</th><th>申请服务</th><th>申请时间</th><th>申请时长</th><th class="max">操作</th></tr>')
            }
        })
    })



    //订单详情加载
    var vm;
    require.async("_/avalon/avalon.min",function(){
        vm = avalon.define({
            $id:"ddxq",
            dic_order_status:comm.dic_order_status,
            data:{}
        })
        avalon.scan();
    });



    var xq = $(".ddxq").show().appendTo(cj.getHideBox());



    //点击订单详情
    approval.delegate(".des","click",function(){
        var id=$(this).parents("tr").attr("sid")
        comm.ajax(window.service_approval_from_ajax || (_+"data/service_approval_from.json"),{id:id},function(tk,data){
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
        xq.appendTo(cj.getHideBox())
        $(".layui-layer,.layui-layer-shade").hide();
    })

    //点击批准
    approval.delegate(".pz","click",function(){
        var mm =$(this).parents("tr").attr("sid")
        comm.confirm("确定批准吗？",function(cancel){
            if(cancel){
                return;
            }
            $.post(window.release_sp_ajax || (_+"data/sava_profile.json"),{ispass:"yes",id:mm})
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

    //点击不批准
    approval.delegate(".npz","click",function(){
        var mm =$(this).parents("tr").attr("sid");
        comm.confirm("<textarea name='nopizhun' id='nopizhun' type='text' cols='30' rows='5' class='nopizhun'></textarea>",function(cancel){
            if(cancel){
                return;
            }

            if(!cancel){
                var txx=$("textarea.nopizhun")
                var res=$.trim(txx.val())
                if(!res){
                    comm.layer.tips("请输入不批准原因",txx,{
                        time: 1000
                    });
                    return false;
                }else{
                    var para ={
                		"ispass":"no",
                        "id":mm,
                        "reason":txx.val()
                    }
                    $.post(window.release_sp_ajax || (_+"data/sava_profile.json"),para)
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
                }

            }


        })
    })


    return{};
})