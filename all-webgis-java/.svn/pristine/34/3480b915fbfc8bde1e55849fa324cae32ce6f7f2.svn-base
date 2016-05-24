/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2015/12/4
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {

    var cl = require("ctool");
    var cj = require("ctooj");
    require("./tgrid.css");
    require("_/avalon/avalon.min");



    var def = {
        rowClick:function(index,el){},
        orderChange:function(by,way){ }
    };



    var tpl = "" +
        "<div ms-controller={tgrid_id} class='tgrid'>" +
            "<i class='loadingLayer'></i>" +
            "<table>" +
            "   <thead>" +
            "       <tr>" +
            "           <th ms-attr-class='$index==order_status.by_index?(\"way_\"+order_status.way):\"\"' ms-repeat=column ms-click='th_click($index,el)'>{{el.title || el}} <i></i></th>" +
            "           <th class='option'>操作</th>" +
            "       </tr>" +
            "   </thead>" +
            "   <tbody>" +
            "       <tr ms-repeat='data' ms-class-1='active:$index==row_sel_index||light_fid==el.fid' ms-click='row_click($index,el,$event)' ms-attr-fid='el.fid'>" +
            "           <td ms-repeat='el.attrs'>{{(el || '-') | html }}</td>" +
            "           <td class='option'><em class=loc ms-click='option(\"loc\",el)'>定位</em><em class='del' ms-click='option(\"loc\",el)'>删除</em></td>" +
            "       </tr>" +
            "   </tbody>" +
            "</table>" +
        "</div>"
    ;


    var tgrid = function(el,config){
        var m = this;
        m.init(el,config);
    }


    $.extend(tgrid.prototype,{
        init:function(el,config){
            var m = this;
            m.sett = $.extend({},def,config);

            m.el = $(el);

            m.controllerid = "tgrid" + new Date().getTime();

            m.el.append(
                tpl.replace(/\{tgrid_id\}/g,m.controllerid)
            );


            var vm = m.vm = avalon.define({
                $id:m.controllerid,
                column:[],
                data:[],
                row_sel_index:-1,


                /**
                 * 高亮的fid
                 */
                light_fid:-1,

                //单击某一行
                row_click:function(index,el,ev){

                    if($(ev.target).is(".loc,.del")){
                        return;
                    }

                    m.vm.row_sel_index = index;
                    m.sett.rowClick.call(m,index,el.$model);

                    m.vm.light_fid = -1;

                    m.cur_row = el.$model;
                },

                //单击表头
                th_click:function(index,el){

                    // 不支持排序的字段
                    if(el.sortable === false){
                        return;
                    }

                    var os = vm.order_status.$model;



                    if(os.by_index == index){
                        var newway = os.way + 1;
                        if(newway>1){
                            newway = -1;
                        }

                        vm.order_status.way = newway;

                        vm.order_status.by_index = -1;
                        vm.order_status.by_index = index;
                    }else{
                        vm.order_status.way = 1;
                        vm.order_status.by_index = index;
                    }

                    vm.order_status.by = el["id"];
                },

                /**
                 * 记录排序状态
                 */
                order_status:{

                    /**
                     * 排序的字段
                     */
                    by_index:undefined,

                    /**
                     * 排序的方式
                     * 0表示不排序
                     * 1表示增序
                     * 2表示降序
                     */
                    way:0,

                    /**
                     * 用来记录排序的字段
                     */
                    by:""
                },


                /**
                 * 操作
                 */
                option:function(flag,row_el){
                    if(flag == "loc"){
                        //定位
                    }

                    if(flag == "del"){
                        //删除
                    }
                }


            });


            avalon.scan();


            //排序状态改变
            //vm.$watch("order_status.$all",function(k,v){
            vm.order_status.$watch("$all",function(k,v){
                var ac = arguments.callee;
                var os = m.vm.order_status;

                if(!ac.t){
                    ac.t = cl.throttle(100,function(){
                        m.sett.orderChange.call(m,os.by_index,os.way,os.by);
                    })
                }

                ac.t();
            });
        },

        /**
         * 设置列
         * @param list
         */
        column:function(list){
            var m = this;

            if(list === undefined){
                return m.vm.column.$model;
            }

            m.vm.column = list;

            $.each(m.vm.data,function(key,ele){
                ele.attrs.splice(0,m.vm.column.length)
            });

            m.vm.order_status.by_index = -1;
            m.vm.order_status.way = 0;
        },

        /**
         * 直接设置数据
         * @param list
         */
        data:function(list){
            var m = this;

            if(list === undefined){
                return m.vm.data.$model;
            }

            $.each(list,function(key,ele){
                ele.attrs.length = m.vm.column.length;
            });

            m.vm.row_sel_index = -1;
            m.vm.data = list;
        },





        /**
         * 刷新数据
         */
        freshData:function(){
            var m = this;

        },

        /**
         * 刷新
         */
        draw:function(){

        },


        /**
         * 根据传入的fid来高亮某一行
         * @param fid
         */
        lightByFid:function(fid){
            var m = this;
            m.vm.light_fid = fid;
            m.vm.row_sel_index = -1;
        },


        /**
         * 删除行，通过fid
         * @param fid
         */
        delRowByFid:function(fid){
            var m = this;
            var index ;
            $.each(m.vm.data.$model,function(key,ele){
                if(ele.fid == fid){
                    index = key * 1;
                    return false;
                }
            });
            m.vm.data.removeAt(index);
        },


        /**
         * 控制loading的显示和关闭
         */
        loadingLayer:function(disable){
            var m = this;
            if(disable === true){
                m.el.removeClass("loading");
            }else{
                m.el.addClass("loading");
            }
        }

    });




    return tgrid;
});