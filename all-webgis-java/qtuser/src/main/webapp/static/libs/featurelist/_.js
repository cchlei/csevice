/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2015/11/12
 * Time: 16:01
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {

    var cl = require("ctool");

    require("_/datatables/Bootstrap-3.3.5/css/bootstrap.min.css");
    require("_/datatables/DataTables-1.10.10/css/dataTables.bootstrap.min.css");
    require("_/datatables/Select-1.1.0/css/select.bootstrap.min.css");


    require("./style.css");
    var cl = require("ctool");

    var def = {

        /**
         * 数据表格的公共配置
         */
        datatable_config_comm:{
            "pagingType":   "full_numbers",
            "sLoadingRecords": "正在加载数据...",
            "sZeroRecords": "暂无数据",
            "processing":true,
            "serverSide":true,
            "lengthChange":false,
            "stateSave": true,
            "searching": false,
            "language": {
                "processing": "玩命加载中...",
                "lengthMenu": "显示 _MENU_ 项结果",
                "zeroRecords": "没有匹配结果",
                "info": "共 _TOTAL_ 条记录",
                "infoEmpty": "共 0 条记录",
                "infoFiltered": "(由 _MAX_ 项结果过滤)",
                "infoPostFix": "",
                "url": "",
                "paginate": {
                    "first":    "首页",
                    "previous": "上一页",
                    "next":     "下一页",
                    "last":     "末页"
                }
            },
        },


        /**
         * 兴趣点列表配置
         */
        feature_config:{
            select: true
        },


        /**
         * ajax请求的地址
         */
        fileAjaxPath:"#",


        /**
         * 附件列表的配置
         */
        file_config:{},

        /**
         * 初始化完成
         */
        initCallback: $.noop,

        /**
         * row_data 选中行的数据，如果是取消选择 为null
         * @param row_data
         */
        on_feature_select:function(row_data,file_req_para_addtion){}
    };

    $.extend(def.feature_config,def.datatable_config_comm);
    $.extend(def.file_config,def.datatable_config_comm);


    var flist = function(el,config){
        var m = this;
        require.async("_/datatables/DataTables-1.10.10/js/jquery.dataTables.min",function(){
            require.async(
                [
                    "_/datatables/DataTables-1.10.10/js/dataTables.bootstrap.min",
                    "_/datatables/Select-1.1.0/js/dataTables.select.min"
                ],
                function(){
                    m.init(el,config);
                }
            );
        });
    }

    $.extend(flist.prototype,{
        init:function(el,config){
            var m = this;
            m.sett = $.extend(true, {}, def, config);
            m.el = $(el);

            m.file_req_para = {};

            var tb = m.el.find("table:first");
            tb.addClass('table table-striped table-bordered table-hover');
            //var a = tb.dataTable(m.sett.feature_config);
            var a = tb.dataTable(m.sett.feature_config);


            var feat = tb.api();

            window.feat = feat;

            feat.on("select",function(e,dt,type,index){
                m.sett.on_feature_select.call(m,dt.data(),m.file_req_para);
                file._t_ajax_reload();
            });

            feat.on("deselect",function(e,dt,type,index){
                m.sett.on_feature_select.call(tb,null);
            });

            var ftb = m.el.find("table:last");
            ftb.addClass('table table-striped table-bordered think-row');
            ftb.dataTable(m.sett.file_config);
            var file = ftb.api();


            //文件表格请求的时候触发
            file.on("preXhr",function(e, settings, data){
                $.extend(data, m.file_req_para);
            });

            file._t_ajax_reload = cl.throttle(500,function(){
                file.ajax.reload();
            });
        }
    });







    return flist;;
});