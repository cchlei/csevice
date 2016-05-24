/**
 * Created with PhpStorm.
 * User: Shinelon
 * Date: 2016/2/25
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
define(function (require, exports, module) {
    var u = require("_/tr_util");
    var co = require("./comm");

    require("__/css/animate.css");

    var mapel = ({
        init:function(){
            var m = this;


            m.ec = co.ec._class();


            m.el = $(".map_wrapper");
            m.btns = m.el.find(".btn_bar");
            m.btn_list = m.btns.find(">a");

            m.geom_input = $("#geom");

            m.ev_class_string = m.btn_list.map(function(){
                var b = $(this);
                return b.attr("event");
            }).toArray().join(" ");



            var no_deal_blur = false;
            m.input = $("#addrname");
            m.input





            function get_a_geom(data){
                var geom = data.geometry;
                if(geom){
                    m.setGeom(geom);
                    m.drawGeomToMap(data);
                }
            }

            function search_poi(key,callback){
                $.ajax({
                    url:co.poiAutoCompleteUrl.replace("%s",encodeURIComponent($.trim(key))),
                    jsonp:"callback",
                    dataType:'jsonp',
                    success:function(resp){
                        callback(resp);
                    }
                })
            }


            m.ec.on("search_poi",function(key){
                search_poi(key,function(data){
                    if(!data.results || !data.results.length) {
                        u.tips(m.ser_el.tx,"地址未找到！",{
                            dire:3
                        })
                        m.ser_vm.do_clear();
                        return;
                    }
                    m.ser_vm.list = data.results;
                    m.ec.emit("on_search_result",data.results);
                    var item = data.results[0];
                    m.ec.emit('locaion',item);
                })
            })


            m.btns.delegate(">a","click",function(){
                var b = $(this);

                //删除按钮不是弹性按钮
                if(b.is(".del")){
                    m.release_btns();
                    m.ec.emit("clear");
                    return;
                }

                var ev = b.attr("event");

                //如果是弹起状态，会派发事件，改变状态
                if(!b.is(".cur")){
                    b.addClass("cur");
                    m.btns.removeClass(m.ev_class_string);
                    //m.btns.addClass(ev);
                    m.ec.emit("startDraw",ev);
                }else{
                    m.release_btns();
                    m.ec.emit("endDraw");
                }
            });



            //默认屏蔽所有按钮
            //m.forbid_btns(true,true);

            m.ec.on("clear",function(){
                m.setGeom("");
            });


            m.append_search_input();



            return m;
        },

        /**
         * 松开所有按钮
         */
        release_btns:function(){
            var m = this;
            m.btns.removeClass(m.ev_class_string);
            m.btn_list.removeClass("cur");
        },

        /**
         * 屏蔽所有按钮
         * status false表示解除屏蔽
         */
        forbid_btns:function(status,withClear){
            var m = this;
            if(status === false) {
                m.btns.removeClass("forbid and_clear");
            }else{
                m.btns.addClass("forbid");
                if(withClear) {
                    m.btns.addClass("forbid and_clear");
                }
            }
        },

        /**
         * 绘制成功后设置geom
         * @param geom
         */
        setGeom:function(geom){
            var m = this;
            m.release_btns();
            m.geom_input.val(geom);
        },


        /**
         * 绘制geom到map上
         */
        drawGeomToMap:function(data){
            var m = this;
            m.ec.emit("draw_geom",data);
        },

        draw_default_geom:function(){
            var m = this;
            //编辑记录的情况
            if(m.geom_input.val()){
                m.drawGeomToMap({
                    geometry: m.geom_input.val(),
                    name:m.input.val()
                });
            }
        },


        /**
         * 添加剂地图搜索框
         */
        append_search_input:function(){
            var m = this;
            var tpl =
                "<div class='map_location' ms-controller='map_location'>" +
                "   <input type='text' ms-duplex='addr_search' ms-on-keydown ='do_search($event)' placeholder='搜索地址' autocomplete='off'/>" +
                "   <a ms-click='do_search({keyCode:13})'>搜索</a>" +
                "   <i class='clear' ms-click='do_clear' ms-visible='addr_search.trim()'>×</i>" +
                "   <div class='list' ms-if='list.length'>" +
                "       <div class='view'>" +
                "           <a " +
                "               ms-repeat='list' " +
                "               ms-data-gid='el.gid' " +
                "               ms-on-mouseenter='item_mouse(1,el)' " +
                "               ms-on-mouseleave='item_mouse(0,el)'" +
                "               ms-click='item_click($event,el)'" +
                "               ms-class-2='light:el.gid==mouseover_gid'" +
                "               ms-class-3='choosed:el.gid==choosed_gid'" +
                "           >" +
                "               <i></i>" +
                "               <b>{{el.name}}</b>" +
                "               <b class='suer' ms-click='item_choose(el)'>选择</b>" +
                "           </a>" +
                "       </div>" +
                "       <ul class='page'></ul>" +
                "</div>" +
                "</div>"
            ;

            m.ser_el = $(tpl).appendTo("#mapel");
            m.ser_el.tx = m.ser_el.find("input");

            m.ser_vm = avalon.define({
                $id:"map_location",
                list:[],

                addr_search:"",

                //条目被单击
                item_click:function(e,el){
                    if($(e.target).is(".sure")) return;
                    m.ec.emit("item_mouse_click",el);
                },

                //鼠标划入或者划出条目
                item_mouse:function(is_enter,el){
                    if(is_enter){
                        m.ec.emit("item_mouse_enter",el);
                    }else{
                        m.ec.emit("item_mouse_leave",el);
                    }
                },


                //当前鼠标停放在地图上poi的gid
                mouseover_gid:-1,


                //当前已经选择的gid
                choosed_gid:-1,

                //单击条目的选择按钮
                item_choose:function(el){
                    m.ec.emit("item_mouse_choose",el);
                    m.input.val(el.name);
                    m.setGeom(el.geometry)
                    m.ec.emit("draw_geom",el);
                    m.ser_vm.choosed_gid = el.gid;
                },

                //单击搜索框内的清除
                do_clear:function(e){
                    m.ser_vm.list = [];
                    m.ec.emit("on_search_result_clear");
                    if(e) m.ser_vm.addr_search = "";
                },

                //进行搜索
                do_search:function(e){
                    if(e && e.keyCode!=13){
                        return;
                    }
                    var key = m.ser_vm.addr_search.trim();

                    if(!key) {
                        u.tips(m.ser_el.tx,"请先输入地址！",{
                            dire:3
                        })

                        m.ser_vm.do_clear();
                        return;
                    }

                    m.ec.emit("search_poi",key);
                }


            });

            m.ser_el.tx.keydown(function(e){
                if(e.keyCode==13){
                    m.ser_el.find("a").click();
                }
            });

            avalon.scan(m.el[0]);
        },
        /**
         * 当poi被单击
         * @param el
         */
        poi_click:function(el){
            var m = this;
            m.ser_vm.item_choose(el);
        },

        /**
         * 鼠标滑入或者滑出
         */
        poi_mouse:function(el,is_enter){
            var m = this;
            if(is_enter) {
                m.ser_vm.mouseover_gid = el.gid;
            }else{
                m.ser_vm.mouseover_gid = -1;
            }
        },

        /**
         * 获取或者设置当前已经选中的poi的id
         */
        choose_poi_id:function(gid){
            var m = this;
            if(gid === undefined) {
                return m.ser_vm.choosed_gid;
            }
            m.ser_vm.choosed_gid = gid;

        }
    }).init();




    return {
        mapel:mapel
    };

});