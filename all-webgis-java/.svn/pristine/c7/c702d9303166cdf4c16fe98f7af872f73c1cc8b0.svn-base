$(function () {
    use(["_/omap/omap", "ctool"], function (omap, cl) {
        omap.init({mapid: "mapel"});


        use(window.mode || "__/js/record_add", function (ex) {

            //绘制事件处理
            ex.mapel.ec.on("startDraw", function (event) {
                var type = event;
                omap.startDraw(type, function (iscancel, geom) {
                    if (!iscancel) {
                        omap.clearDraw(true);
                        ex.mapel.setGeom(geom);
                        poi_mgr.do_check();
                        ex.mapel.choose_poi_id(-1);
                    }
                });
            });


            //结束绘制事件处理
            ex.mapel.ec.on("endDraw", function () {
                omap.endDraw();
                ex.mapel.release_btns();
            });

            //清除事件处理
            ex.mapel.ec.on("clear", function () {
                omap.endDraw();
                omap.clearDraw(true);
                omap.pop(undefined);
                ex.mapel.forbid_btns(false);
                ex.mapel.choose_poi_id(-1);



                $.each(poi_mgr.current_el_list,function(key,poi){
                    poi.dom.removeClass("checked");
                });


            });

            //绘制到地图事件处理
            ex.mapel.ec.on("draw_geom", function (data) {
                var geom = data.geometry;
                omap.clearDraw(true);
                omap.pop(undefined);
                omap.drawByGeom(geom, true);
            })


            //定位事件处理
            ex.mapel.ec.on("locaion", function (item) {
                omap.centerAtByCoord(
                    omap.getCoordinate(item.geometry)
                )
            });

            //鼠标滑过条目
            ex.mapel.ec.on("item_mouse_enter", function (el) {
                var poi = poi_mgr.get(el.gid);
                poi.dom.addClass("active");
                var overlay_cont = poi.dom.parent();
                overlay_cont.parent().append(overlay_cont);
                //poi.dom.icon.addClass("shake animated");

            })

            //鼠标移出条目
            ex.mapel.ec.on("item_mouse_leave", function (el) {
                var poi = poi_mgr.get(el.gid);
                poi.dom.removeClass("active");
                //poi.dom.icon.removeClass("shake animated");
                //omap.map.removeOverlay(poi.overlay);
                //omap.map.addOverlay(poi.overlay);
                poi.totop();
            })


            //条目被点击
            ex.mapel.ec.on("item_mouse_click", function (el) {
                var ac = arguments.callee;
                var geom = el.geometry;
                if (!ac.find) {
                    ac.find = function (geom) {
                        omap.centerAtByCoord(
                            omap.getCoordinate(geom)
                        )
                    }
                }
                ac.find.killDelayCall();
                ac.find.delayCall(330, [geom]);
                var p = poi_mgr.get(el.gid);
                p.totop();

            })


            //条目被选择
            ex.mapel.ec.on("item_mouse_choose", function (el) {
                var p = poi_mgr.get(el.gid);
                poi_mgr.do_check(p);
                p.totop();
            })

            //当执行一次搜索，并获取到结果
            ex.mapel.ec.on("on_search_result", function (results) {
                poi_mgr.realse_recoder();
                $.each(results, function (k, el) {
                    var full_el = poi_mgr.get(el.gid);
                    if (!full_el) {
                        full_el = poi_mgr.set(el.gid, el);
                        el.coord = omap.getCoordinate(el.geometry);
                        var $dom = $(
                            "<div class='search_result_overlay'><i></i><b>" + el.name + "</b></div>"
                        );
                        el.dom = $dom;
                        $dom.on("click",function(){
                            ex.mapel.poi_click(el);
                        });

                        $dom.on("mouseenter",function(){
                            ex.mapel.poi_mouse(el,true);
                        });

                        $dom.on("mouseleave",function(){
                            ex.mapel.poi_mouse(el,false);
                        });

                        el.dom.icon = $dom.find(">i");
                        el.overlay = new ol.Overlay({
                            position: el.coord,
                            positioning: 'bottom-left',
                            offset:[-6,4],
                            element: $dom[0],
                            stopEvent: false
                        });
                        el.cont = $dom.parent();

                        el.totop = function(){
                            this.cont.addClass("z900").siblings().removeClass("z900");
                        }
                    }
                    omap.map.addOverlay(full_el.overlay);
                    poi_mgr.recoder(full_el);
                });
            })


            /**
             * 兴趣点管理
             */
            var poi_mgr = ({
                dic_id_poi: {},
                current_el_list: [],
                init: function () {
                    var m = this;
                    return m;
                },
                recoder: function (el) {
                    var m = this;
                    m.current_el_list.push(el);
                },
                realse_recoder: function () {
                    var m = this;
                    $.each(m.current_el_list, function (key, ele) {
                        ele.dom.removeClass("checked");
                        omap.map.removeOverlay(ele.overlay);
                    });
                    m.current_el_list.length = 0;
                },
                get: function (gid) {
                    var m = this;
                    return m.dic_id_poi[gid];

                },
                set: function (gid, el) {
                    var m = this;
                    m.dic_id_poi[gid] = el;
                    return el;
                },
                each:function(callback){
                    callback = callback || $.noop;
                    $.each(m.dic_id_poi,function(_id,poi){
                        callback(poi);
                    });
                },

                __last_checked_el:undefined,
                do_check:function(el){
                    var m = this;

                    if(m.__last_checked_el) {
                        m.__last_checked_el.dom.removeClass("checked");
                    }

                    if(el){
                        el.dom.addClass("checked");
                        m.__last_checked_el = el;
                    }
                },

                /**
                 * 居顶
                 */
                toTop:function(el){
                    var m = this;
                    el.cont.addClass("z900").siblings().removeClass("z900");
                }
            }).init();


            //搜索结果被清除
            ex.mapel.ec.on("on_search_result_clear", function () {
                poi_mgr.realse_recoder();
            });


            //绘制已保存的geom
            ex.mapel.draw_default_geom();


            //var d = ({"type":"FeatureCollection","features":[{"type":"Feature","id":"16063","properties":{"id":16063,"geom":"POINT(108.93321990966797 34.31451698217333)","addrname":"红庙坡"},"geometry":{"type":"Point","coordinates":[108.93321990966797,34.31451698217333]}},{"type":"Feature","id":"16064","properties":{"id":16064,"geom":"POINT(108.91502380371092 34.31111408145408)","addrname":"西北城角"},"geometry":{"type":"Point","coordinates":[108.91502380371092,34.31111408145408]}}]});
            //omap.drawByData(d);

            //var geom = "POLYGON((108.91725540161134 34.29608295348062,109.0085792541504 34.280481768929675,108.91382217407225 34.24472995671498,108.85786056518555 34.27622639773304,108.91725540161134 34.29608295348062))";
            //var ft = omap.getFtByGeom(geom);


            //omap.drawByGeom(geom);


            //omap.drawByData({"type":"FeatureCollection","features":[ft]});

            //ol.feature和从后台加载到的feature不是同一种东西，绘制时会出错


        });
    })
});




